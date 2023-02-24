package com.jalasoft.wordpress.steps.api;

import api.controller.APIController;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.internal.http.Status;
import org.testng.Assert;

import java.nio.charset.StandardCharsets;

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
        Assert.assertTrue(Status.SUCCESS.matches(controller.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(controller.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(controller.getResponse().getContentType(), expectedContentType, "wrong content type returned");
    }

    @Then("^the user should not get a response$")
    public void verifyUserShouldNotCreatedAPage() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        Assert.assertTrue(Status.FAILURE.matches(controller.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(controller.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(controller.getResponse().getContentType(), expectedContentType, "wrong content type returned");
    }
}
