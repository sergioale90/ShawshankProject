package com.jalasoft.wordpress.steps.hooks.ui.admin;

import api.methods.APIPostsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.controller.UIController;
import ui.methods.CommonMethods;
import utils.LoggerManager;

public class GUIPostsFeatureHook {
    private static final LoggerManager log = LoggerManager.getInstance();
    private final UIController controller;

    public GUIPostsFeatureHook(UIController controller) {
        this.controller = controller;
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
        Assert.assertNotNull(requestResponse, "post with title -> " + title + " was not found");
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post with title -> " + title + " was not deleted");
    }

    @After("@OpenPublishPost or @OpenDraftPost or @EditPublishPost or @EditDraftPost or @DeletePublishPost or @DeleteDraftPost")
    public void afterCreateAPost() {
        CommonMethods.logout();
        String id = controller.getId();
        Response requestResponse = APIPostsMethods.deleteAPostById(id);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post with id -> " + id + " was not deleted");
    }
}