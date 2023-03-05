/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */

package com.jalasoft.wordpress.steps.hooks.ui.admin;

import api.methods.APICategoriesMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
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
    @After("@CreateANewCategory or @EnterToEditCategory or @EditCategorySuccessfully or @EditCategoryUnsuccessfully or @QuickEditCategory or @SearchACategory")
    public void deleteCategoryCreated() {
        CommonMethods.logout();
        String id = controller.getId();
        Response requestResponse = APICategoriesMethods.deleteACategory(id);

        Assert.assertNotNull(requestResponse, "category with id -> " + id + " was not found");
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "category with id -> " + id + " was not deleted");
    }
    @Before("@EnterToEditCategory or @EditCategorySuccessfully or @EditCategoryUnsuccessfully or @QuickEditCategory or @SearchACategory or @DeleteCategorySuccessfully")
    public void createACategory() {
        Response requestResponse = APICategoriesMethods.createACategory();
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "category was not created");
        String id = requestResponse.jsonPath().getString("id");
        String categoryName = requestResponse.jsonPath().getString("name");
        String categorySlug = requestResponse.jsonPath().getString("slug");
        String categoryDescription = requestResponse.jsonPath().getString("description");
        controller.setId(id);
        controller.setName(categoryName);
        controller.setSlug(categorySlug);
        controller.setDescription(categoryDescription);
    }
}
