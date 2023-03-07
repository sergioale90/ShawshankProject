/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.hooks.ui.admin;

import api.methods.APIUsersMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.controller.UIUserController;
import utils.LoggerManager;

public class GUIUsersFeatureHook {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private final UIUserController controller;

    public GUIUsersFeatureHook(UIUserController controller) {
        this.controller = controller;
    }

    @Before("@FindAUser or @UpdateAUserUI or @UpdateARoleUI or @DeleteAUserUI or @DeleteAUserWithBulkActions ")
    public void createAUser() {
        Response requestResponse = APIUsersMethods.createAUser();
        controller.setId(requestResponse.jsonPath().getString("id"));
        controller.setUsername(requestResponse.jsonPath().getString("username"));
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "user was not created");
    }

    @After("@FindAUser or @CreateAUserUI or @CreateAUserWithRoleUI or @UpdateAUserUI or @UpdateARoleUI ")
    public void deleteAPostById() {
        Response requestResponse = APIUsersMethods.deleteAUsersById(controller.getId());
        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()),
                "post with id -> " + controller.getId() + " was not deleted");
    }
}
