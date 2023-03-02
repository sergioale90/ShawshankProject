package com.jalasoft.wordpress.steps.hooks.ui.admin;

import api.methods.APIPagesMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.controller.UIController;
import ui.methods.CommonMethods;
import utils.LoggerManager;

public class GUIPagesFeatureHook {
    private static final LoggerManager log = LoggerManager.getInstance();
    private final UIController controller;

    public GUIPagesFeatureHook(UIController controller) {
        this.controller = controller;
    }

    @Before("@EditPublishPage or @DeleteDraftPage or @DeleteDraftPagePermanently or @RestoreDraftPage or @FindValidPage or @FindNoValidPage")
    public void createPage() {
        Response requestResponse = APIPagesMethods.CreateADraftPage();
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page was not created");

        String title = requestResponse.jsonPath().getString("title.raw");
        String id = requestResponse.jsonPath().getString("id");
        controller.setTitle(title);
        controller.setId(id);
    }

    @Before("@UpdatePublishPage or @PagePublishSwitchDraft")
    public void createPagePublished() {
        Response requestResponse = APIPagesMethods.createAPagePublished();
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page was not created");

        String id = requestResponse.jsonPath().getString("id");
        String title = requestResponse.jsonPath().getString("title.raw");
        controller.setId(id);
        controller.setTitle(title);
    }

    @After("@CreatePublishPage or @UpdatePublishPage or @PagePublishSwitchDraft or @FindNoValidPage")
    public void afterPages() {
        CommonMethods.logout();
        String title = controller.getTitle();
        System.out.println(title);
        Response requestResponse = APIPagesMethods.deleteAPageByTitle(title);

        Assert.assertNotNull(requestResponse, "page with title -> " + title + " was not found");
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page with title -> " + title + " was not deleted");
    }

    @After("@CreateDraftPage or @RestoreDraftPage or @FindValidPage")
    public void afterDeleteDraftPages() {
        CommonMethods.logout();
        String title = controller.getTitle();
        Response requestResponse = APIPagesMethods.deleteADraftPageByTitle(title);
        Assert.assertNotNull(requestResponse, "page with title -> " + title + " was not found");
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page with title -> " + title + " was not deleted");
    }

    @After("@EditPublishPage or @DeleteDraftPage")
    public void afterCreateAPage() {
        CommonMethods.logout();
        String id = controller.getId();
        Response requestResponse = APIPagesMethods.deleteAPageById(id);

        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page with id -> " + id + " was not deleted");
    }
}
