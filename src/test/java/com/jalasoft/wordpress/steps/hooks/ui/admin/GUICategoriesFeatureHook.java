package com.jalasoft.wordpress.steps.hooks.ui.admin;

import api.methods.APICategoriesMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.controller.UIController;

public class GUICategoriesFeatureHook {
    private final UIController controller;
    public GUICategoriesFeatureHook(UIController controller) {
        this.controller = controller;
    }
    @After("@CreateANewCategory or @EnterToEditCategory or @EditCategorySuccessfully or @EditCategoryUnsuccessfully or @QuickEditCategory")
    public void deleteCategoryCreated() {
        String id = controller.getId();
        Response requestResponse = APICategoriesMethods.deleteACategory(id);

        Assert.assertNotNull(requestResponse, "category with id -> " + id + " was not found");
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "category with id -> " + id + " was not deleted");
    }
    @Before("@EnterToEditCategory or @EditCategorySuccessfully or @EditCategoryUnsuccessfully or @QuickEditCategory")
    public void createACategory() {
        Response requestResponse = APICategoriesMethods.createACategory();
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "category was not created");
        String id = requestResponse.jsonPath().getString("id");
        controller.setId(id);
    }
}
