/**
 * Copyright (c) 2023 Jala University.
 * <p>
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.ui.admin;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import ui.PageTransporter;
import ui.admin.pages.*;
import ui.controller.UIController;

import java.util.List;
import java.util.Map;

public class TagsSteps {
    private final UIController controller;
    private final PageTransporter pageTransporter;
    private TagsPage tagsPage;
    private NewTagPage newTagPage;
    private EditTagPage editTagPage;
    private ViewTagPage viewTagPage;

    public TagsSteps(UIController controller, HomeAdminPage homeAdminPage) {
        this.pageTransporter = PageTransporter.getInstance();
        this.controller = controller;
        this.tagsPage = new TagsPage();
        this.viewTagPage = new ViewTagPage();
    }

    @Given("^the user goes to Tags page$")
    public void goToNewTagsPage() {
        newTagPage = pageTransporter.navigateToNewTagPage();
    }

    @Given("^the user creates a new tag with the following values$")
    public void createTag(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);

        String name = (String) values.get("name");
        String slug = (String) values.get("slug");
        String description = (String) values.get("description");

        controller.setName(name);
        controller.setSlug(slug);
        controller.setDescription(description);
        newTagPage.addNewTag(name, slug, description);
    }

    @Given("^the user opens the tag using the tag name link on the Tag page table$")
    public void goToTagUsingLink() {
        String id = controller.getId();
        editTagPage = tagsPage.editTagByIdOnTable(id);
    }

    @Then("^the user edits the tag with the following values$")
    public void tagEdited(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);

        String name = (String) values.get("name");
        String slug = (String) values.get("slug");
        String description = (String) values.get("description");
        controller.setName(name);
        controller.setSlug(slug);
        controller.setDescription(description);
        editTagPage.updateTag(name, slug, description);
        boolean isTagUpdatedMessageDisplayed = editTagPage.isTagUpdatedMessageDisplayed();
        Assert.assertTrue(isTagUpdatedMessageDisplayed, "updating message is missing");

        newTagPage = pageTransporter.navigateToNewTagPage();
    }

    @Then("^the user opens the tag using the View link on the Tag page table$")
    public void viewTag() {
        String id = controller.getId();
        String name = controller.getName();
        tagsPage = tagsPage.viewTagUsingLink(id, name);
    }

    @Then("^the user deletes the tag using the Delete link$")
    public void deleteTagFromEditPage() {
        editTagPage.deleteTagLink();
        boolean alertIsPresent = newTagPage.alertIsPresent();
        newTagPage.acceptAlert();
        Assert.assertTrue(alertIsPresent, "alert message was not displayed");

    }

    @Then("^the user deletes the tag using the Delete link on the Tag page table$")
    public void deleteTagFromTagTable() {
        String id = controller.getId();
        String name = controller.getName();
        tagsPage.deleteTagUsingLink(id, name);
        boolean alertIsPresent = newTagPage.alertIsPresent();
        newTagPage.acceptAlert();
        Assert.assertTrue(alertIsPresent, "alert message was not displayed");
    }

    @Then("^the user searches a valid name tag$")
    public void searchValidTag() {
        String name = controller.getName();
        tagsPage.setTextboxSearch(name);
        tagsPage.getSearchTagsButton();
    }

    @Then("^the user should review the name tag found$")
    public void verifyTagSearchedIsPresent() {
        String name = controller.getName();
        String id = controller.getId();
        boolean isNameTagLinkPresent = tagsPage.isNameTagLinkPresent(id);
        Assert.assertTrue(isNameTagLinkPresent, "wrong the tag is not present");
    }

    @Then("^the user should review that the tag has been updated successfully$")
    public void verifyTagWasEdited() {
        String actualName = controller.getName();
        String actualSlug = controller.getSlug();
        String actualDescription = controller.getDescription();

        Map<String, String> valueTag = tagsPage.verifyTheTagCreated(controller.getName());
        String expectedName = valueTag.get("name");
        String expectedSlug = valueTag.get("slug");
        String expectedDescription = valueTag.get("description");

        Assert.assertEquals(actualName, expectedName, "wrong name value returned");
        Assert.assertEquals(actualSlug, expectedSlug, "wrong slug value returned");
        Assert.assertEquals(actualDescription, expectedDescription, "wrong description value returned");
        Assert.assertEquals(actualDescription, expectedDescription, "wrong description value returned");
    }

    @Then("^the user should review that the tag was created successfully$")
    public void verifyIfTagWasCreated() {
        boolean isTagAddedMessageDisplayed = newTagPage.isTagAddedMessageDisplayed();

        String actualName = controller.getName();
        String actualSlug = controller.getSlug();
        String actualDescription = controller.getDescription();

        Map<String, String> valueTag = tagsPage.verifyTheTagCreated(controller.getName());
        String expectedName = valueTag.get("name");
        String expectedSlug = valueTag.get("slug");
        String expectedDescription = valueTag.get("description");

        Assert.assertTrue(isTagAddedMessageDisplayed, "creation message is missin");
        Assert.assertEquals(actualName, expectedName, "wrong name value returned");
        Assert.assertEquals(actualSlug, expectedSlug, "wrong slug value returned");
        Assert.assertEquals(actualDescription, expectedDescription, "wrong description value returned");
        Assert.assertEquals(actualDescription, expectedDescription, "wrong description value returned");
    }

    @Then("^the user should review that the Tag has the correct info$")
    public void verifyTagHasCorrectInformation() {
        String actualName = controller.getName();
        boolean tagNameIsDisplayedInViewPage = viewTagPage.isTagNameDisplayed(actualName);
        Assert.assertTrue(tagNameIsDisplayedInViewPage, "wrong name value is not displayed");
    }

    @Then("^the user should review that the Tag has been deleted$")
    public void pressesOkButtonToDeleteTag() {
        String tagName = controller.getName();

        Assert.assertTrue(tagsPage.isNameTagLinkNotPresent(tagName), "wrong the tag was no deleted");
    }

}
