/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.hooks.ui.admin;

import api.controller.APIController;
import api.methods.APIPostsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.controller.UIController;
import ui.methods.CommonMethods;
import utils.LoggerManager;

import java.util.List;

public class GUIPostsFeatureHook {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private final UIController controller;
    private final APIController apiController;
    private static final int ORDER = 100;

    public GUIPostsFeatureHook(UIController controller, APIController apiController) {
        this.controller = controller;
        this.apiController = apiController;
    }

    @Before("@OpenPublishPost or @EditPublishPost or @DeletePublishPost")
    public void createAPost() {
        Response requestResponse = APIPostsMethods.createAPost();
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post was not created");

        String id = requestResponse.jsonPath().getString("id");
        String title = requestResponse.jsonPath().getString("title.raw");
        String content = requestResponse.jsonPath().getString("content.raw");
        controller.setId(id);
        controller.setTitle(title);
        controller.setContent(content);
    }

    @Before("@OpenDraftPost or @EditDraftPost or @DeleteDraftPost")
    public void createADraftPost() {
        Response requestResponse = APIPostsMethods.createADraftPost();
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post was not created");

        String id = requestResponse.jsonPath().getString("id");
        String title = requestResponse.jsonPath().getString("title.raw");
        String content = requestResponse.jsonPath().getString("content.raw");
        controller.setId(id);
        controller.setTitle(title);
        controller.setContent(content);
    }

    @After("@LoginAdmin")
    public void afterLoginAdmin() {
        CommonMethods.logout();
    }

    @After("@CreatePublishPost or @CreateDraftPost")
    public void afterPosts() {
        CommonMethods.logout();
        String title = controller.getTitle();
        Response requestResponse = APIPostsMethods.deleteAPostByTitle(title);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post with title -> " + title + " was not deleted");
    }

    @After("@OpenPublishPost or @OpenDraftPost or @EditPublishPost or @EditDraftPost or @EditOwnPublishPost or @EditOwnDraftPost or @DeletePublishPost or @DeleteDraftPost")
    public void afterCreateAPost() {
        CommonMethods.logout();
        String id = controller.getId();
        Response requestResponse = APIPostsMethods.deleteAPostById(id);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post with id -> " + id + " was not deleted");
    }

    @After(value = "@PostsUI", order = ORDER)
    public void afterPostsAll() {
        CommonMethods.logout();
        List<Integer> objects = APIPostsMethods.getAllPosts().jsonPath().getList("id");
        for (Integer id : objects) {
            APIPostsMethods.deleteAPostById(id.toString());
        }
    }
}