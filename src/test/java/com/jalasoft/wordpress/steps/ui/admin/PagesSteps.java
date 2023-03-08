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
import ui.admin.components.EditHeader;
import ui.admin.components.WordPressAlert;
import ui.admin.components.Wrap;
import ui.admin.pages.HomeAdminPage;
import ui.admin.pages.NewPagesPage;
import ui.admin.pages.PagesPage;
import ui.controller.UIController;

import java.util.List;
import java.util.Map;

/**
 * This class has the methods of the steps for Pages scenarios.
 */

public class PagesSteps {
    private final UIController controller;
    private final HomeAdminPage homeAdminPage;
    private PagesPage pagesPage;
    private NewPagesPage newPagesPage;
    private Wrap wrapPage;
    private EditHeader editHeader;
    private WordPressAlert wordPressAlert;

    public PagesSteps(UIController controller, HomeAdminPage homeAdminPage) {
        this.controller = controller;
        this.homeAdminPage = homeAdminPage;
        this.wrapPage = new Wrap();
        this.editHeader = new EditHeader();
    }

    @Given("^the user goes to Pages using the left side menu bar$")
    public void gotToPagesPageUsingMenu() {
        pagesPage = homeAdminPage.getLeftSideBarMenu().goToNewPagePage();
    }

    @Then("^the user switches to draft a page published$")
    public void switchToDraftAPagePublished() {
        String title = controller.getTitle();
        newPagesPage = pagesPage.goToPagePageUsingLink(title);
        wordPressAlert = editHeader.switchDraftButton();
    }

    @Then("^the user searches an invalid title page \"(.*?)\"$")
    public void searchInValidaTitlePage(String title) {
        wrapPage.setTitleTextBox(title);
        wrapPage.getButtonSearchSubmit();
    }

    @Then("^the user searches a valid title page$")
    public void searchValidaTitlePage() {
        String title = controller.getTitle();
        wrapPage.setTitleTextBox(title);
        wrapPage.getButtonSearchSubmit();
    }

    @Given("^the user goes to New Page page using the Add New button on Pages page$")
    public void goToNewPagePageUsingButton() {
        newPagesPage = pagesPage.goToNewPostPage();
    }

    @Given("^the user opens the Page using the page title link on the Page page table$")
    public void goToPageUsingLink() {
        String title = controller.getTitle();
        newPagesPage = pagesPage.goToPagePageUsingLink(title);
    }

    @Given("^the user(?: publishes a new | edits and publishes the )Page with the following values$")
    public void publishPage(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);

        String title = (String) values.get("title");
        String content = (String) values.get("content");

        controller.setTitle(title);
        controller.setContent(content);
        newPagesPage.publishPage(title, content);
    }

    @Given("^the user save as draft a new Page with the following values$")
    public void draftPage(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);

        String title = (String) values.get("title");
        String content = (String) values.get("content");

        controller.setTitle(title);
        controller.setContent(content);
        newPagesPage.draftPage(title, content);
    }

    @Given("^the user updates the Page with the following values$")
    public void updatePublishPage(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);

        String title = (String) values.get("title");
        String content = (String) values.get("content");

        controller.setTitle(title);
        controller.setContent(content);
        newPagesPage.updatePagePublished(title, content);
    }

    @Given("^the user moves a Page to trash using the trash link on the Page page table$")
    public void movePageToTrashUsingLink() {
        String title = controller.getTitle();
        pagesPage = pagesPage.movePageToTrashUsingLink(title);
    }

    @Given("^the user restores the Page using the Restore link on the Page page table$")
    public void restorePage() {
        wrapPage.getLinkTrash();
        String title = controller.getTitle();
        pagesPage = pagesPage.restorePageUsingLink(title);
    }

    @Then("^the user reviews that the Page should have been published successfully$")
    public void verifyIfPageWasPublished() {
        boolean isPublishedMessageDisplayed = newPagesPage.isPublishedMessageDisplayed();
        String actualTitle = newPagesPage.getTitleText();
        String actualContent = newPagesPage.getContentText();

        String expectedTitle = controller.getTitle();
        String expectedContent = controller.getContent();

        Assert.assertTrue(isPublishedMessageDisplayed, "page published message was not displayed");
        Assert.assertEquals(actualTitle, expectedTitle, "wrong title found");
        Assert.assertEquals(actualContent, expectedContent, "wrong content found");
    }

    @Then("^the user reviews that the Page should have been updated successfully$")
    public void verifyIfPagePublishedWasUpdate() {
        boolean isUpdatedMessageDisplayed = newPagesPage.isUpdateMessageDisplayed();
        String actualTitle = newPagesPage.getTitleText();
        String actualContent = newPagesPage.getContentText();

        String expectedTitle = controller.getTitle();
        String expectedContent = controller.getContent();

        Assert.assertTrue(isUpdatedMessageDisplayed, "page updated message was not displayed");
        Assert.assertEquals(actualTitle, expectedTitle, "wrong title found");
        Assert.assertEquals(actualContent, expectedContent, "wrong content found");
    }

    @Then("^the user reviews that the Page should have been saved successfully$")
    public void verifyIfPageWasSavedAsDraft() {
        boolean isDraftMessageDisplayed = newPagesPage.isSaveDraftMessageDisplayed();
        String actualTitle = newPagesPage.getTitleText();
        String actualContent = newPagesPage.getContentText();

        String expectedTitle = controller.getTitle();
        String expectedContent = controller.getContent();

        Assert.assertTrue(isDraftMessageDisplayed, "draft message was not displayed");
        Assert.assertEquals(actualTitle, expectedTitle, "wrong title found");
        Assert.assertEquals(actualContent, expectedContent, "wrong content found");
    }

    @Then("^the user reviews that the Page should have been moved to trash successfully$")
    public void verifyPageWasMovedToTrash() {
        String title = controller.getTitle();
        boolean isPageMovedToTrashMessageDisplayed = pagesPage.isPageMovedToTrashMessageDisplayed();
        boolean isPageTitleLinkPresent = pagesPage.isPageTitleLinkNotPresent(title);

        Assert.assertTrue(isPageMovedToTrashMessageDisplayed, "page moved to trash message was not displayed");
        Assert.assertTrue(isPageTitleLinkPresent, "page title link was present");
    }

    @Given("^the user deletes the Page using the Delete Permanently link on the Page page table$")
    public void deletePagePermanently() {
        wrapPage.getLinkTrash();
        String title = controller.getTitle();
        pagesPage = pagesPage.deletePagePermanentlyUsingLink(title);
    }

    @Then("^the user reviews that the Page should have been delete permanently$")
    public void verifyPageWasDeletedPermanently() {
        String title = controller.getTitle();
        boolean isPagePermanentlyDeleteMessageDisplayed = pagesPage.isPagePermanentlyDeleteMessageDisplayed();
        boolean isPageTitleLinkNotPresent = pagesPage.isPageTitleLinkNotPresentInTrashSection(title);

        Assert.assertTrue(isPagePermanentlyDeleteMessageDisplayed, "page deleted permanently message was not displayed");
        Assert.assertTrue(isPageTitleLinkNotPresent, "page title link was present");
    }

    @Then("^the user reviews that the Page should have been restored$")
    public void verifyPageWasRestored() {
        String title = controller.getTitle();
        boolean isPageRestoreMessageDisplayed = pagesPage.isPageRestoredMessageDisplayed();
        boolean isPageTitleLinkNotPresent = pagesPage.isPageTitleLinkNotPresentInTrashSection(title);

        Assert.assertTrue(isPageRestoreMessageDisplayed, "page restore message was not displayed");
        Assert.assertTrue(isPageTitleLinkNotPresent, "page title link was present");
    }

    @Then("^the user should see the title page found$")
    public void userVerifyTitlePageFound() {
        String title = controller.getTitle();
        boolean isPageTitleLinkPresent = pagesPage.isPageTitleLinkNotPresent(title);
        Assert.assertFalse(isPageTitleLinkPresent, "page title link was not present");
    }

    @Then("^the user should see a \"(.*?)\" message$")
    public void userVerifyTitlePageNotFound(String errorMessage) {
        String title = controller.getTitle();
        boolean isPageTitleLinkPresent = pagesPage.isInvalidPageTitleLinkNotPresent(title);
        Assert.assertFalse(isPageTitleLinkPresent, "page title link was present");
    }

    @Then("^the user presses OK option of the message displayed to continue with the process")
    public void userPressesOkOptionButtonOfTheAlert() {
        Assert.assertTrue(wordPressAlert.isAlertVisible(), "alert is not visible");
        Assert.assertEquals(wordPressAlert.getMessage(), "Are you sure you want to unpublish this post?");
        wordPressAlert.clickOkButton();
        Assert.assertTrue(newPagesPage.isPageRevertedToDraftMessageDisplayed(), "message is not displayed");
    }

    @Then("^the user should review that the page was moved to draft status")
    public void userVerifyThatPageWasMovedToDraftSatus() {
        pagesPage = homeAdminPage.getLeftSideBarMenu().goToNewPagePage();
        wrapPage.getLinkDraft();
        String title = controller.getTitle();
        boolean isPageTitleLinkPresent = pagesPage.isPageTitleLinkNotPresent(title);
        Assert.assertFalse(isPageTitleLinkPresent, "page title link was not present");
    }
}