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
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.internal.http.Status;
import org.testng.Assert;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class APISteps {
    private final APIController controller;

    public APISteps(APIController controller) {
        this.controller = controller;
    }

    @Then("^the user should get a \"(.*?)\" response$")
    public void verifyStatusLine(String expectedStatusLine) {
        String actualStatusLine = controller.getResponse().getStatusLine();
        Assert.assertEquals(actualStatusLine, expectedStatusLine, "wrong status line returned");
    }

    @Then("^the user should get a valid response and have a body$")
    public void verifyValidResponseAndBody() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        Assert.assertTrue(Status.SUCCESS.matches(controller.getResponse()
                .getStatusCode()), "invalid status code returned");
        Assert.assertFalse(controller.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(controller.getResponse()
                .getContentType(), expectedContentType, "wrong content type returned");
    }

    @Then("^the user should see the response invalid and have a body$")
    public void verifyInvalidResponseAndBody() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        String actualContentType = controller.getResponse().getContentType();

        Assert.assertTrue(Status.FAILURE.matches(controller.getResponse()
                .getStatusCode()), "invalid status code returned");
        Assert.assertFalse(controller.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(actualContentType, expectedContentType, "wrong content type returned");
    }

    @Then("the user should get a invalid response and have a body")
    public void theUserShouldGetAInvalidResponseAndHaveABody() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        Assert.assertTrue(Status.FAILURE.matches(controller.getResponse()
                .getStatusCode()), "invalid status code returned");
        Assert.assertFalse(controller.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(controller.getResponse()
                .getContentType(), expectedContentType, "wrong content type returned");
    }

    @Then("^the user should get a valid response format and have a body$")
    public void verifyResponseAndBodyNeutral() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        String actualContentType = controller.getResponse().getContentType();
        Assert.assertFalse(controller.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(actualContentType, expectedContentType, "wrong content type returned");
    }

    @Then("^an error response should be returned and have a body with the following values$")
    public void verifyErrorResponseAndBody(DataTable table) {
        List<Map<String, Object>> paramsList = table.asMaps(String.class, Object.class);
        Map<String, Object> params = paramsList.get(0);

        String expectedStatus = (String) params.get("status");
        String expectedError = (String) params.get("error");
        String expectedCode = (String) params.get("code");
        String expectedErrorDescription = (String) params.get("error_description");

        String actualStatus = controller.getResponse().jsonPath().getString("status");
        String actualError = controller.getResponse().jsonPath().getString("error");
        String actualCode = controller.getResponse().jsonPath().getString("code");
        String actualErrorDescription = controller.getResponse().jsonPath().getString("error_description");

        Assert.assertEquals(actualStatus, expectedStatus, "wrong status value returned");
        Assert.assertEquals(actualError, expectedError, "wrong error value returned");
        Assert.assertEquals(actualCode, expectedCode, "wrong code value returned");
        Assert.assertEquals(actualErrorDescription, expectedErrorDescription, "wrong error description value returned");
    }

    @Then("^the response should be returned and have a body with the following values$")
    public void verifyResponseAndBody(DataTable table) {
        List<Map<String, Object>> paramsList = table.asMaps(String.class, Object.class);
        Map<String, Object> params = paramsList.get(0);

        String expectedCode = (String) params.get("code");
        String expectedMessage = (String) params.get("message");
        String expectedData = (String) params.get("data");

        String actualCode = controller.getResponse().jsonPath().getString("code");
        String actualMessage = controller.getResponse().jsonPath().getString("message");
        String actualData = controller.getResponse().jsonPath().getString("data");

        Assert.assertEquals(actualCode, expectedCode, "wrong code value returned");
        Assert.assertEquals(actualMessage, expectedMessage, "wrong message value returned");
        Assert.assertEquals(actualData, expectedData, "wrong data value returned");
    }
}
