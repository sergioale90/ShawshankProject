/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.hooks.api;

import api.controller.APIController;
import api.methods.APIUsersMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

public class APIUsersFeatureHook {

    private final APIController controller;

    public APIUsersFeatureHook(APIController controller) {
        this.controller = controller;
    }

    @Before("@RetrieveAUser or @UpdateAUser or @DeleteAUser or @RetrieveCurrentUser or @UpdateCurrentUser or @DeleteCurrentUser")
    public void createAUser() {
        Response requestResponse = APIUsersMethods.createAUser();
        controller.setResponse(requestResponse);

        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()), "user was not created");
    }

    @After("@CreateAUser or @RetrieveAUser or @UpdateAUser or @RetrieveCurrentUser or @UpdateCurrentUser ")
    public void deleteAPostById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Response requestResponse = APIUsersMethods.deleteAUsersById(id);

        Assert.assertTrue(Status.SUCCESS.matches(requestResponse.getStatusCode()),
                "post with id -> " + id + " was not deleted");
    }
}
