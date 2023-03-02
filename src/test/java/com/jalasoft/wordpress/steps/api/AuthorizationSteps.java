/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.api;

import api.controller.APIController;
import api.methods.APIAuthMethods;
import io.cucumber.java.en.Given;
import io.restassured.http.Header;
import org.testng.Assert;

public class AuthorizationSteps {
    private final APIController controller;

    public AuthorizationSteps(APIController controller) {
        this.controller = controller;
    }

    @Given("^(?:I am|the user is) authenticated with \"(.*?)\" role$")
    public void getToken(String userRole) {
        Header authHeader = APIAuthMethods.getAuthHeader(userRole);
        controller.addHeader(authHeader);
        Assert.assertNotNull(authHeader, "Unable to retrieve authorization header for user with role --> " + userRole);
    }
}
