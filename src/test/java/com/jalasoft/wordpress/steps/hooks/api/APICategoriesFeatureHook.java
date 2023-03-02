package com.jalasoft.wordpress.steps.hooks.api;

import api.controller.APIController;
import api.methods.APICategoriesMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

public class APICategoriesFeatureHook {
    private final APIController controller;

    public APICategoriesFeatureHook(APIController controller) {
        this.controller = controller;
    }

    @Before("@DeleteACategory or @UpdateACategory or @RetrieveACategory or @NotUpdateACategory or @NotDeleteACategory")
    public void createACategory() {
        Response requestResponse = APICategoriesMethods.CreateACategory();
        controller.setResponse(requestResponse);

        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "category was not created");
    }

    @After("@CreateACategory or @UpdateACategory or @RetrieveACategory")
    public void deleteACategory() {
        String id = controller.getResponse().jsonPath().getString("id");
        Response requestResponse = APICategoriesMethods.deleteACategory(id);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "category with id -> " + id + " was not deleted");
    }
    @After("@NotUpdateACategory or @NotDeleteACategory")
    public void deleteACategoryInvalidResponse() {
        String id = controller.getIdAux();
        Response requestResponse = APICategoriesMethods.deleteACategory(id);
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "category with id -> " + id + " was not deleted");
    }
}