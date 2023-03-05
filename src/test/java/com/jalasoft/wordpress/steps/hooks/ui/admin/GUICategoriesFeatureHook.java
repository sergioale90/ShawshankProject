package com.jalasoft.wordpress.steps.hooks.ui.admin;

import api.methods.APICategoriesMethods;
import api.methods.APIPagesMethods;
import io.cucumber.java.After;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.controller.UIController;
import ui.methods.CommonMethods;

public class GUICategoriesFeatureHook {
    private final UIController controller;
    public GUICategoriesFeatureHook(UIController controller) {
        this.controller = controller;
    }
    @After("@CreateANewCategory")
    public void deleteCategoryCreated() {
        String id = controller.getId();
        Response requestResponse = APICategoriesMethods.deleteACategory(id);

        Assert.assertNotNull(requestResponse, "category with id -> " + id + " was not found");
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "category with id -> " + id + " was not deleted");
    }
}
