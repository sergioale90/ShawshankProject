package com.jalasoft.wordpress.steps.hooks.admin;

import api.methods.APIPagesMethods;
import api.methods.APIPostsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.controller.UIController;
import ui.methods.CommonMethods;
import utils.LoggerManager;

public class AdminFeatureHook {
    private static final LoggerManager log = LoggerManager.getInstance();
    private final UIController controller;

    public AdminFeatureHook(UIController controller) {
        this.controller = controller;
    }

    @Before("@EditPublishPost or @DeleteDraftPost")
    public void createPost() {
        Response requestResponse = APIPostsMethods.createAPost();
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post was not created");

        String title = requestResponse.jsonPath().getString("title.raw");
        String id = requestResponse.jsonPath().getString("id");
        controller.setTitle(title);
        controller.setId(id);
    }

    @Before("@EditPublishPage or @DeleteDraftPage or @DeleteDraftPagePermanently or @RestoreDraftPage")
    public void createPage() {
        Response requestResponse = APIPagesMethods.createAPage();
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page was not created");

        String title = requestResponse.jsonPath().getString("title.raw");
        String id = requestResponse.jsonPath().getString("id");
        controller.setTitle(title);
        controller.setId(id);
    }

    @Before("@UpdatePublishPage")
    public void createPagePublished() {
        Response requestResponse = APIPagesMethods.createAPagePublished();
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page was not created");

        String id = requestResponse.jsonPath().getString("id");
        String title = requestResponse.jsonPath().getString("title.raw");
        controller.setId(id);
        controller.setTitle(title);
    }


    @After("@LoginAdmin")
    public void afterLoginAdmin() {
        CommonMethods.logout();
    }

    @After("@CreatePublishPost")
    public void afterPosts() {
        CommonMethods.logout();
        String title = controller.getTitle();
        Response requestResponse = APIPostsMethods.deleteAPostByTitle(title);

        Assert.assertNotNull(requestResponse, "post with title -> " + title + " was not found");
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post with title -> " + title + " was not deleted");
    }

    @After("@CreatePublishPage or @UpdatePublishPage")
    public void afterPages() {
        CommonMethods.logout();
        String title = controller.getTitle();
        Response requestResponse = APIPagesMethods.deleteAPageByTitle(title);

        Assert.assertNotNull(requestResponse, "page with title -> " + title + " was not found");
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page with title -> " + title + " was not deleted");
    }

    @After("@CreateDraftPage or @RestoreDraftPage")
    public void afterDeleteDraftPages() {
        CommonMethods.logout();
        String title = controller.getTitle();
        Response requestResponse = APIPagesMethods.deleteADraftPageByTitle(title);
        Assert.assertNotNull(requestResponse, "page with title -> " + title + " was not found");
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page with title -> " + title + " was not deleted");
    }


    @After("@EditPublishPost or @DeleteDraftPost")
    public void afterCreateAPost() {
        CommonMethods.logout();
        String id = controller.getId();
        Response requestResponse = APIPostsMethods.deleteAPostById(id);

        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post with id -> " + id + " was not deleted");
    }

    @After("@EditPublishPage or @DeleteDraftPage")
    public void afterCreateAPage() {
        CommonMethods.logout();
        String id = controller.getId();
        Response requestResponse = APIPagesMethods.deleteAPageById(id);

        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page with id -> " + id + " was not deleted");
    }
}
