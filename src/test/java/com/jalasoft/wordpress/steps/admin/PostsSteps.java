package com.jalasoft.wordpress.steps.admin;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import ui.PageTransporter;
import ui.admin.pages.HomeAdminPage;
import ui.admin.pages.NewPostPage;
import ui.admin.pages.PostsPage;
import ui.controller.UIController;

import java.util.List;
import java.util.Map;

public class PostsSteps {
    private final PageTransporter pageTransporter;
    private final UIController controller;
    private final HomeAdminPage homeAdminPage;
    private PostsPage postsPage;
    private NewPostPage newPostPage;

    public PostsSteps(UIController controller, HomeAdminPage homeAdminPage) {
        this.pageTransporter = PageTransporter.getInstance();
        this.controller = controller;
        this.homeAdminPage = homeAdminPage;
    }

    @Given("^the user goes to Posts page using the left side menu bar$")
    public void gotToPostsPageUsingMenu() {
        postsPage = homeAdminPage.leftSideBarMenu.goToPostPage();
    }

    @Given("^I go to New Post page using the popup submenu button from the left side menu bar$")
    public void goToNewPostPageUsingSubmenuButton() {
        newPostPage = homeAdminPage.leftSideBarMenu.goToNewPostPageUsingPopupMenu();
    }

    @Given("^I go to New Post page$")
    public void goToNewPostPage() {
        newPostPage = pageTransporter.navigateToNewPostPage();
    }

    @Given("^the user goes to New Post page using the Add New button on Posts page$")
    public void goToNewPostPageUsingButton() {
        newPostPage = postsPage.goToNewPostPage();
    }

    @Given("^the user opens the Post using the post title link on the Post page table$")
    public void goToPostUsingLink() {
        String title = controller.getTitle();
        newPostPage = postsPage.goToPostPageUsingLink(title);
    }

    @Given("^the user moves a Post to trash using the trash link on the Post page table$")
    public void movePostToTrashUsingLink() {
        String title = controller.getTitle();
        postsPage = postsPage.movePostToTrashUsingLink(title);
    }

    @Given("^I leave the New Posts page$")
    public void leaveNewPostPage() {
        newPostPage.leftSideBarMenu.clickPostMenuButton();
    }

    @Given("^the user(?: publish a new | edit and publish the )Post with the following values$")
    public void publishPost(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);

        String title = (String) values.get("title");
        String content = (String) values.get("content");

        controller.setTitle(title);
        controller.setContent(content);
        newPostPage.publishPost(title, content);
    }

    @And("the user creates a new Draft Post with the following values")
    public void createDraftPost(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);

        String title = (String) values.get("title");
        String content = (String) values.get("content");

        controller.setTitle(title);
        controller.setContent(content);
        newPostPage.draftPost(title, content);
    }

    @Given("^I create a new Post with the following values without saving the changes$")
    public void createAPost(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);

        String title = (String) values.get("title");
        String content = (String) values.get("content");

        newPostPage.createPost(title, content);
    }

    @Then("^the Post should have been published successfully$")
    public void verifyIfPostWasPublished() {
        boolean isPublishedMessageDisplayed = newPostPage.isPublishedMessageDisplayed();
        String actualTitle = newPostPage.getTitleText();
        String actualContent = newPostPage.getContentText();

        String expectedTitle = controller.getTitle();
        String expectedContent = controller.getContent();

        Assert.assertTrue(isPublishedMessageDisplayed, "post published message was not displayed");
        Assert.assertEquals(actualTitle, expectedTitle, "wrong title found");
        Assert.assertEquals(actualContent, expectedContent, "wrong content found");
    }

    @Then("the Post should have been saved as a Draft successfully")
    public void thePostShouldHaveBeenSavedAsADraftSuccessfully() {
        boolean isPublishedMessageDisplayed = newPostPage.isSavedAsDraftMessageDisplayed();
        Assert.assertTrue(isPublishedMessageDisplayed, "post published message was not displayed");
    }

    @Then("^the Post should have been moved to trash successfully$")
    public void verifyPostWasMovedToTrash() {
        String title = controller.getTitle();
        boolean isPostMovedToTrashMessageDisplayed = postsPage.isPostMovedToTrashMessageDisplayed();
        boolean isPostTitleLinkPresent = postsPage.isPostTitleLinkNotPresent(title);

        Assert.assertTrue(isPostMovedToTrashMessageDisplayed, "post moved to trash message was not displayed");
        Assert.assertTrue(isPostTitleLinkPresent, "post title link was present");
    }

    @Then("^the Post should have the correct info$")
    public void verifyPostInfo() {
        String actualTitle = newPostPage.getTitleText();
        String actualContent = newPostPage.getContentText();

        String expectedTitle = controller.getTitle();
        String expectedContent = controller.getContent();

        Assert.assertEquals(actualTitle, expectedTitle, "wrong title found");
        Assert.assertEquals(actualContent, expectedContent, "wrong content found");
    }

    @Then("^an alert should be displayed on the New Posts page$")
    public void verifyAnAlertIsDisplayedNewPostPage() {
        boolean alertIsPresent = newPostPage.alertIsPresent();
        newPostPage.acceptAlert();

        Assert.assertTrue(alertIsPresent, "alert message was not displayed");
    }
}
