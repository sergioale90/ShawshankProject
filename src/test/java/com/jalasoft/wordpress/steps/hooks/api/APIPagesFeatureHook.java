package com.jalasoft.wordpress.steps.hooks.api;

import api.controller.APIController;
import api.methods.APIPagesMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

public class APIPagesFeatureHook {
    private final APIController controller;

    public APIPagesFeatureHook(APIController controller) {
        this.controller = controller;
    }

    @Before("@RetrieveAPage or @UpdateAPage or @DeleteAPage")
    public void createAPage() {
        Response requestResponse = APIPagesMethods.createAPage();
        controller.setResponse(requestResponse);

        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page was not created");
    }

    @After("@CreateAPagePublish or @CreateAPageDraft or @RetrieveAPage or @UpdateAPage or @DeleteAPage")
    public void deleteAPageById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Response requestResponse = APIPagesMethods.deleteAPageById(id);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "page with id -> " + id + " was not deleted");
    }
}
