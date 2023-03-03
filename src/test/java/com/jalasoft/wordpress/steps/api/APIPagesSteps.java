/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.api;

import api.APIConfig;
import api.APIManager;
import api.controller.APIController;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIPagesSteps {

    private static final APIConfig API_CONFIG = APIConfig.getInstance();

    private static final APIManager API_MANAGER = APIManager.getInstance();

    private final APIController controller;

    private final String pagesEndpoint = API_CONFIG.getPagesEndpoint();

    private final String pagesByIdEndpoint = API_CONFIG.getPagesByIdEndpoint();

    private Map<String, Object> params;

    private static final int PER_PAGE = 100;

    public APIPagesSteps(APIController controller) {
        this.controller = controller;
    }

    @Given("^the user tries to retrieve all pages list$")
    public void getAllPages() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", PER_PAGE);

        Response requestResponse = API_MANAGER.get(pagesEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }

    @Given("^the user creates a page with the following values$")
    public void createAPublishPage(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        Response requestResponse = API_MANAGER.post(pagesEndpoint, queryParams, controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = queryParams;
    }

    @Given("^the user tries to retrieve a page$")
    public void getPagetById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.get(pagesByIdEndpoint.replace("<id>", id), authHeader);

        Map<String, Object> queryParams = new HashMap<>();
        String title = controller.getResponse().jsonPath().getString("title.raw");
        String status = controller.getResponse().jsonPath().getString("status");
        String content = controller.getResponse().jsonPath().getString("content.raw");
        String excerpt = controller.getResponse().jsonPath().getString("excerpt.raw");

        queryParams.put("id", id);
        queryParams.put("title", title);
        queryParams.put("status", status);
        queryParams.put("content", content);
        queryParams.put("excerpt", excerpt);
        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @Then("^the user should get a proper amount of pages$")
    public void verifyPagesAmount() {
        int expectedAmountOfPages = Integer.parseInt(controller.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfPages = controller.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(actualAmountOfPages, expectedAmountOfPages, "wrong amount of pages returned");
    }

    @Then("^the user reviews that the page have been created with the proper values$")
    public void verifyCreatedPage() {
        String expectedStatus = "";
        String expectedTitle = (String) params.get("title");
        if (params.containsKey("status")) {
            expectedStatus = (String) params.get("status");
        } else {
            expectedStatus = "draft";
        }
        String expectedContent = (String) params.get("content");
        String expectedExcerpt = (String) params.get("excerpt");

        String actualTitle = controller.getResponse().jsonPath().getString("title.raw");
        String actualStatus = controller.getResponse().jsonPath().getString("status");
        String actualContent = controller.getResponse().jsonPath().getString("content.raw");
        String actualExcerpt = controller.getResponse().jsonPath().getString("excerpt.raw");

        Assert.assertEquals(actualTitle, expectedTitle, "wrong title value returned");
        Assert.assertEquals(actualStatus, expectedStatus, "wrong status value returned");
        Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
        Assert.assertEquals(actualExcerpt, expectedExcerpt, "wrong excerpt value returned");
    }
    @Then("^the user should see the response returned and have a body with the following values$")
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

    @Then("^the user reviews that the page should have been retrieved with the proper values$")
    public void verifyRetrievedPage() {
        String expectedId = (String) params.get("id");
        String expectedTitle = (String) params.get("title");
        String expectedStatus = (String) params.get("status");
        String expectedContent = (String) params.get("content");
        String expectedExcerpt = (String) params.get("excerpt");

        String actualId = controller.getResponse().jsonPath().getString("id");
        String actualTitle = controller.getResponse().jsonPath().getString("title.rendered");
        String actualStatus = controller.getResponse().jsonPath().getString("status");
        String actualContent = controller.getResponse().jsonPath().getString("content.rendered")
                .replaceAll("<[^>]*>", "").strip();
        String actualExcerpt = controller.getResponse().jsonPath().getString("excerpt.rendered")
                .replaceAll("<[^>]*>", "").strip();

        Assert.assertEquals(actualId, expectedId, "wrong id value returned");
        Assert.assertEquals(actualTitle, expectedTitle, "wrong title value returned");
        Assert.assertEquals(actualStatus, expectedStatus, "wrong status returned");
        Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
        Assert.assertEquals(actualExcerpt, expectedExcerpt, "wrong excerpt value returned");
    }

    @Then("^the user should get an error message$")
    public void verifyUserShouldNotCreatedAPage() {
        String expectedErrorMessage = "Sorry, you are not allowed to create posts as this user.";
        String actualErrorMessage = controller.getResponse().jsonPath().getString("message");
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "wrong message returned to the user");
    }
}
