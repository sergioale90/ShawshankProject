package com.jalasoft.wordpress.steps.hooks.api;

import api.controller.APIController;
import api.methods.APIPostsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

public class APIPostsFeatureHook {
    private final APIController controller;

    public APIPostsFeatureHook(APIController controller) {
        this.controller = controller;
    }

    @Before("@RetrieveAPost or @UpdateAPost or @DeleteAPost")
    public void createAPost() {
        Response requestResponse = APIPostsMethods.createAPost();
        controller.setResponse(requestResponse);

        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post was not created");
    }

    @After("@CreateAPost or @RetrieveAPost or @UpdateAPost or @DeleteAPost")
    public void deleteAPostById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Response requestResponse = APIPostsMethods.deleteAPostById(id);

        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "post with id -> " + id + " was not deleted");
    }
}
