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
import ui.PageTransporter;
import ui.admin.pages.AdminCommentPage;
import ui.admin.pages.HomeAdminPage;
import ui.controller.UICommentsController;
import ui.sections.PublishedPostPage;

public class CommentsSteps {
    private final UICommentsController controller;
    private AdminCommentPage adminCommentPage;
    private HomeAdminPage homeAdminPage;
    private final PageTransporter pageTransporter;
    private PublishedPostPage publishedPostPage;

    public CommentsSteps(UICommentsController controller, HomeAdminPage homeAdminPage) {
        this.pageTransporter = PageTransporter.getInstance();
        this.controller = controller;
        this.homeAdminPage = homeAdminPage;
        this.publishedPostPage = new PublishedPostPage();
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

    @Then("^the user should review that his comment \"(.*?)\" was added$")
    public void verifyCommentAdded(String comment) {
        boolean commentIsAdded = publishedPostPage.isCommentDisplayed(comment);
    }
}
