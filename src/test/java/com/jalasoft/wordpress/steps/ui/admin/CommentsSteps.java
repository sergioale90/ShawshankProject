/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.ui.admin;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import ui.PageTransporter;
import ui.admin.pages.CommentsPage;
import ui.admin.pages.EditCommentPage;
import ui.admin.pages.HomeAdminPage;
import ui.controller.UICommentsController;
import ui.sections.PublishedPostPage;

import java.util.Map;

/**
 * This class is responsible for providing all the necessary methods for
 * the UI tests to be performed by the commenting feature.
 *
 * @version 1.0
 */
public class CommentsSteps {
    private final UICommentsController controller;
    private final PageTransporter pageTransporter;
    private HomeAdminPage homeAdminPage;
    private CommentsPage commentsPage;

    private EditCommentPage editCommentPage;
    private PublishedPostPage publishedPostPage;

    public CommentsSteps(UICommentsController controller, HomeAdminPage homeAdminPage) {
        this.pageTransporter = PageTransporter.getInstance();
        this.controller = controller;
        this.homeAdminPage = homeAdminPage;
        this.editCommentPage = new EditCommentPage();
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

    @When("the user moves a comment to reply using the reply link on the Comments table")
    public void replyAComment() {
        String commentId = controller.getIdComment();
        controller.setContent(commentsPage.moveCommentUserToReply(commentId));
    }

    @Then("the user should see a new comment created in the comments table")
    public void verifyCommentReply() {
        boolean isCommentAdded = commentsPage.isReplyDisplayed(controller.getContent());
        Assert.assertTrue(isCommentAdded, "wrong the reply comment was not added");
    }

    @And("the user moves a comment to edit using the quick edit link on the Comments table")
    public void movesACommentToEdit() {
        String commentId = controller.getIdComment();
        controller.setContent(commentsPage.moveCommentUserToQuickEdit(commentId));
    }

    @Then("the user should see that the comment was updated successfully")
    public void commentWasUpdatedSuccessfully() {
        boolean isCommentUpdated = commentsPage.isReplyDisplayed(controller.getContent());
        Assert.assertTrue(isCommentUpdated, "wrong the comment was not updated");
    }

    @And("the user goes to a edit from the edit link on the Comments table")
    public void goesToAEditFrom() {
        String commentId = controller.getIdComment();
        commentsPage.moveCommentUserToEdit(commentId);
    }

    @And("the user replace the old comment with a new one")
    public void replaceTheOldComment() {
        controller.setContent(commentsPage.editCommentInPage());
    }

    @And("the user searches for {string} with the Search Comments option")
    public void searchCommentsOption(String comment) {
        commentsPage.searchComment(comment);
        controller.setContent(comment);
    }

    @Then("the user should see the result of his comment searched")
    public void resultOfHisCommentSearched() {
        commentsPage.isReplyDisplayed(controller.getContent());
    }

    @And("the user replace the old author and email with a new one")
    public void replaceTheAuthorAndEmail() {
        Map<String, String> values = editCommentPage.fillEditComment();
        controller.setName(values.get("name"));
        controller.setEmail(values.get("email"));
    }

    @Then("the user should see that the author and email was updated successfully")
    public void seeAuthorAndEmailUpdatedSuccessfully() {
        Assert.assertTrue(commentsPage.verifyEmailAuthor(controller.getName(), controller.getEmail()));
    }

    @And("the user moves the comment to trash using the trash link")
    public void movesTheCommentToTrashUsing() {
        String commentId = controller.getIdComment();
        editCommentPage.moveCommentToTrash();
    }
}
