package com.jalasoft.wordpress.steps.admin;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.admin.components.Wrap;
import ui.admin.pages.HomeAdminPage;
import ui.admin.pages.NewPagesPage;
import ui.admin.pages.PagesPage;
import ui.controller.UIController;

import java.util.List;
import java.util.Map;

public class PagesSteps {
    private final UIController controller;
    private final HomeAdminPage homeAdminPage;
    private PagesPage pagesPage;
    private NewPagesPage newPagesPage;
    private Wrap wrapPage;
    private Response draftPages;

    public PagesSteps(UIController controller, HomeAdminPage homeAdminPage) {
        this.controller = controller;
        this.homeAdminPage = homeAdminPage;
        this.wrapPage = new Wrap();
    }

    @Given("^the user goes to Pages using the left side menu bar$")
    public void gotToPagesPageUsingMenu() {
        pagesPage = homeAdminPage.leftSideBarMenu.goToNewPagePage();
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
        boolean isPageTitleLinkPresent = pagesPage.isPageTitleLinkPresent(title);

        Assert.assertTrue(isPageMovedToTrashMessageDisplayed, "page moved to trash message was not displayed");
        Assert.assertTrue(isPageTitleLinkPresent, "page title link was present");
    }
    @Given("^the user deletes the Page using the Delete Permanently link on the Page page table$")
    public void goToTrashSectionUsingTrashLink() {
        wrapPage.getLinkTrash();
        String title = controller.getTitle();
        pagesPage = pagesPage.deletePagePermanentlyUsingLink(title);
    }
    @Given("^the user reviews that the Page should have been delete permanently$")
    public void deletePagePermanently() {
        String title = controller.getTitle();
        // pagesPage = pagesPage.movePageToTrashUsingLink(title);
        boolean isPagePermanentlyDeleteMessageDisplayed = pagesPage.isPagePermanentlyDeleteMessageDisplayed();
       //  boolean isPageTitleLinkPresent = pagesPage.isPageTitleLinkPresentInTrashSection(title);

        Assert.assertTrue(isPagePermanentlyDeleteMessageDisplayed, "page deleted permanently message was not displayed");
        // Assert.assertTrue(isPageTitleLinkPresent, "page title link was present");
    }
}
