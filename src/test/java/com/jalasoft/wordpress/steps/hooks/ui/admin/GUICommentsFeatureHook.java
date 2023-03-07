/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.hooks.ui.admin;

import api.methods.APICommentsMethods;
import api.methods.APIPostsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.controller.UICommentsController;
import ui.methods.CommonMethods;
import utils.LoggerManager;

public class GUICommentsFeatureHook {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private final UICommentsController controller;
    private static final int ORDER = 100;

    public GUICommentsFeatureHook(UICommentsController controller) {
        this.controller = controller;
    }

    @Before("@UserComments")
    public void createPost() {
        Response requestResponse = APIPostsMethods.createAPost();
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post was not created");

        String id = requestResponse.jsonPath().getString("id");
        String title = requestResponse.jsonPath().getString("title.raw");
        String link = requestResponse.jsonPath().getString("link");

        controller.setPostId(id);
        controller.setPostTitle(title);
        controller.setPostLink(link);
    }

    @Before("@Comments")
    public void createCommentToAPost() {
        createPost();
        Response requestResponse = APICommentsMethods.createComment(controller.getPostId());
        String idComment = requestResponse.jsonPath().getString("id");
        String messageUser = requestResponse.jsonPath().getString("content.rendered").replaceAll("<[^>]*>", "").strip();

        controller.setIdComment(idComment);
        controller.setCommentUser(messageUser);
    }

    @After(value = "@Comments", order = ORDER)
    public void afterTags() {
        CommonMethods.logout();
        APIPostsMethods.deleteAPostById(controller.getPostId());
    }

}
