/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.ui.admin;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import ui.PageTransporter;
import ui.admin.pages.AdminCommentPage;
import ui.admin.pages.CommentsPage;
import ui.admin.pages.HomeAdminPage;
import ui.controller.UICommentsController;
import ui.sections.PublishedPostPage;

public class CommentsSteps {
    private final UICommentsController controller;
    private final PageTransporter pageTransporter;
    private AdminCommentPage adminCommentPage;
    private HomeAdminPage homeAdminPage;
    private CommentsPage commentsPage;
    private PublishedPostPage publishedPostPage;

    public CommentsSteps(UICommentsController controller, HomeAdminPage homeAdminPage) {
        this.pageTransporter = PageTransporter.getInstance();
        this.controller = controller;
        this.homeAdminPage = homeAdminPage;
        this.publishedPostPage = new PublishedPostPage();
        this.commentsPage = new CommentsPage();
    }

    @When("the user goes to Post page")
    public void userGoPostPage() {
        String link = controller.getPostLink();
        pageTransporter.goToPublishedPost(link);
    }

    @Then("^the user comments \"(.*?)\" on the post page$")
    public void userAddCommentsOnPostPage(String comment) {
        publishedPostPage.setCommentBox(comment);
    }

    @Then("^the user goes to Comments page$")
    public void userGoesToCommentPage() {
        pageTransporter.navigateToCommentsPage();
    }

    @Then("^the user moves a comment to trash using the trash link on the Comments table$")
    public void userMovesCommentToTrash() {
        String commentId = controller.getIdComment();
        commentsPage.moveCommentUserToTrash(commentId);
    }
    @Then("^the user moves a comment to approve using the approve link on the Comments table$")
    public void userMovesCommentToApprove() {
        String commentId = controller.getIdComment();
        commentsPage.moveCommentUserToApprove(commentId);
    }

    @Then("^the user moves a comment to spam using the spam link on the Comments table$")
    public void userMovesCommentToSpam() {
        String commentId = controller.getIdComment();
        commentsPage.moveCommentUserToSpam(commentId);
    }

    @Then("^the user should review that his comment \"(.*?)\" was added$")
    public void verifyCommentAdded(String comment) {
        boolean isCommentAdded = publishedPostPage.isCommentDisplayed(comment);
        Assert.assertTrue(isCommentAdded, "wrong the comment was not added");
    }

    @Then("^the user should see that the comment was moved to trash successfully$")
    public void verifyCommentMovedToTrash() {
        String trashUrl = commentsPage.goToTrash();
        Assert.assertTrue(trashUrl.contains("trash"), "current url is not for trash page");
        String comment = controller.getCommentUser();
        boolean isCommentOnTrash = commentsPage.isCommentOnTable(comment);
        Assert.assertTrue(isCommentOnTrash, "wrong the comment is not in trash");
    }

    @Then("^the user should see that the comment was moved to approve successfully$")
    public void verifyCommentMovedToApprove() {
        String approveUrl = commentsPage.goToApprove();
        Assert.assertTrue(approveUrl.contains("approved"), "current url is not for approve page");
        String comment = controller.getCommentUser();
        boolean isCommentOnApprove = commentsPage.isCommentOnTable(comment);
        Assert.assertTrue(isCommentOnApprove, "wrong the comment is not in approve");
    }

    @Then("^the user should see that the comment was moved to spam successfully$")
    public void verifyCommentMovedToSpam() {
        String approveUrl = commentsPage.goToSpam();
        Assert.assertTrue(approveUrl.contains("spam"), "current url is not for spam page");
        String comment = controller.getCommentUser();
        boolean isCommentOnSpam = commentsPage.isCommentOnTable(comment);
        Assert.assertTrue(isCommentOnSpam, "wrong the comment is not in spam");
    }

}
