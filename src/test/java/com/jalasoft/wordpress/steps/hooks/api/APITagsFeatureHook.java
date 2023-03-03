package com.jalasoft.wordpress.steps.hooks.api;

import api.controller.APIController;
import api.methods.APITagsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

public class APITagsFeatureHook {
    private final APIController controller;

    public APITagsFeatureHook(APIController controller) {
        this.controller = controller;
    }

    @Before("@RetrieveATag or @UnableToGetAllTags or @UpdateATag")
    public void createATag() {
        Response requestResponse = APITagsMethods.CreateATag();
        controller.setResponse(requestResponse);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "tag was not created");
    }

    @After("@CreateATag or @RetrieveATag or @UpdateATag or @UnableToGetAllTags")
    public void deleteATagById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Response requestResponse = APITagsMethods.deleteATagById(id);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "tag with id -> " + id + " was not deleted");
    }
}
