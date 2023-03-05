/**
 * Copyright (c) 2023 Jala University.
 *
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
import ui.admin.pages.HomeAdminPage;
import ui.admin.pages.NewTagPage;
import ui.admin.pages.TagsPage;
import ui.controller.UIController;

import java.util.List;
import java.util.Map;

public class TagsSteps {
    private final UIController controller;
    private final HomeAdminPage homeAdminPage;
    private final PageTransporter pageTransporter;
    private TagsPage tagsPage;
    private NewTagPage newTagPage;

    public TagsSteps(UIController controller, HomeAdminPage homeAdminPage) {
        this.pageTransporter = PageTransporter.getInstance();
        this.controller = controller;
        this.homeAdminPage = homeAdminPage;
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

    @Then("^the user should review that the tag was created successfully$")
    public void verifyIfTagWasCreated() {
        boolean isTagAddedMessageDisplayed = newTagPage.isTagMessageDisplayed();

        String actualName = newTagPage.getNameText();
        String actualSlug = newTagPage.getSlugText();
        String actualDescription = newTagPage.getDescriptionText();


        String expectedName = controller.getName();
        String expectedSlug = controller.getSlug();
        String expectedDescription = controller.getDescription();

        Assert.assertTrue(isTagAddedMessageDisplayed, "tag added message was not displayed");
        Assert.assertEquals(actualName, expectedName, "wrong name found");
        Assert.assertEquals(actualSlug, expectedSlug, "wrong slug found");
        Assert.assertEquals(actualDescription, expectedDescription, "wrong description found");
    }

}
