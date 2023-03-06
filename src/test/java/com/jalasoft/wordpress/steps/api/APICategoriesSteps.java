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
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Header;
import io.restassured.internal.http.Status;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.StringManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class APICategoriesSteps {
    private static final APIConfig API_CONFIG = APIConfig.getInstance();
    private static final APIManager API_MANAGER = APIManager.getInstance();
    private final APIController controller;
    private final String categoriesEndpoint = API_CONFIG.getCategoriesEndpoint();
    private final String categoriesEndpointById = API_CONFIG.getCategoriesEndpointById();
    private Map<String, Object> params;
    private static final int PER_PAGE = 100;
    private static final int STRING_LENGHT = 5;


    public APICategoriesSteps(APIController controller) {
        this.controller = controller;
    }

    @Given("^the user makes a request to retrieve all categories$")
    public void getAllCategories() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", PER_PAGE);

        Response requestResponse = API_MANAGER.get(categoriesEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }
    @Given("^the user makes a request to create a category$")
    public void createANewCategory() {
        Map<String, Object> queryRequest = new HashMap<>();
        queryRequest.put("name", StringManager.generateAlphanumericString(STRING_LENGHT));
        queryRequest.put("slug", StringManager.generateAlphanumericString(STRING_LENGHT));
        queryRequest.put("description", StringManager.generateAlphanumericString(STRING_LENGHT));

        Response requestResponse = API_MANAGER.post(categoriesEndpoint, queryRequest, controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = queryRequest;
    }
    @Given("^the user makes a request to delete a existent category$")
    public void deleteACategory() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryRequest = new HashMap<>();
        queryRequest.put("force", true);
        Response requestResponse = API_MANAGER.delete(categoriesEndpointById.replace("<id>", id), queryRequest, authHeader);
        controller.setIdAux(id);
        controller.setResponse(requestResponse);
    }
    @Given ("^the user makes a request to update a existent category$")
    public void updateACategory() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryRequest = new HashMap<>();
        queryRequest.put("name", StringManager.generateAlphanumericString(STRING_LENGHT));
        queryRequest.put("slug", StringManager.generateAlphanumericString(STRING_LENGHT));
        queryRequest.put("description", StringManager.generateAlphanumericString(STRING_LENGHT));
        Response requestResponse = API_MANAGER.put(categoriesEndpointById.replace("<id>", id), queryRequest, authHeader);
        params = queryRequest;
        controller.setIdAux(id);
        controller.setResponse(requestResponse);
    }
    @Given("^the user makes a request to retrieve a category")
    public void retrieveACategory() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.get(categoriesEndpointById.replace("<id>", id), authHeader);
        String categoryName = controller.getResponse().jsonPath().getString("name");
        String categorySlug = controller.getResponse().jsonPath().getString("slug");
        String categoryDescription = controller.getResponse().jsonPath().getString("description");

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", categoryName);
        queryParams.put("slug", categorySlug);
        queryParams.put("description", categoryDescription);
        params = queryParams;
        controller.setResponse(requestResponse);
    }
    @Then("^response should have a proper amount of categories$")
    public void verifyIfResponseHasTheListOfCategories() {
        String schemaFilePath = "src|test|resources|api|json|schemas|ListCategoriesSchema.json".replace("|", File.separator);
        File schemaFile = new File(schemaFilePath);
        Response response = controller.getResponse();

        int expectedAmountOfCategories = Integer.parseInt(controller.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfCategories = controller.getResponse().jsonPath().getList("$").size();

        Assert.assertEquals(actualAmountOfCategories, expectedAmountOfCategories, "wrong amount of categories returned");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
    }
    @Then("^category should have been created correctly$")
    public void verifyIfCategoryWasCreatedProperly() {
        String schemaFilePath = "src|test|resources|api|json|schemas|NewCategorySchema.json".replace("|", File.separator);
        File schemaFile = new File(schemaFilePath);
        Response response = controller.getResponse();

        String expectedCategoryName = (String) params.get("name");
        String expectedCategorySlug = (String) params.get("slug");
        expectedCategorySlug = expectedCategorySlug.toLowerCase();
        String expectedCategoryDescription = (String) params.get("description");

        String actualCategoryName = controller.getResponse().jsonPath().getString("name");
        String actualCategorySlug = controller.getResponse().jsonPath().getString("slug");
        String actualCategoryDescription = controller.getResponse().jsonPath().getString("description");

        Assert.assertEquals(actualCategoryName, expectedCategoryName, "The category was not created with the properly name");
        Assert.assertEquals(actualCategorySlug, expectedCategorySlug, "The category was not created with the properly slug");
        Assert.assertEquals(actualCategoryDescription, expectedCategoryDescription, "The category was not created with the properly description");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
    }
    @Then ("^response should display the information of the category$")
    public void verifyIfCategoryIsRetrievedProperly() {
        String expectedName = (String) params.get("name");
        String expectedSlug = (String) params.get("slug");
        String expectedDescription = (String) params.get("description");

        String actualName = controller.getResponse().jsonPath().getString("name");
        String actualSlug = controller.getResponse().jsonPath().getString("slug");
        String actualDescription = controller.getResponse().jsonPath().getString("description");

        Assert.assertEquals(actualName, expectedName, "Wrong category name retrieved");
        Assert.assertEquals(actualSlug, expectedSlug, "Wrong category slug retrieved");
        Assert.assertEquals(actualDescription, expectedDescription, "Wrong category description retrieved");
    }
    @Then("^the user should not get a valid response$")
    public void theResponseIsNotValid() {
        Assert.assertFalse(Status.SUCCESS.matches(controller.getResponse().getStatusCode()));
    }
    @Then("^category shouldn't have been created and the response has a error \"(.*?)\"$")
    public void verifyIfCategoryNotCreated(String message) {
        String actualMessage = controller.getResponse().jsonPath().getString("message");
        Assert.assertEquals(actualMessage, message, "Error message in response is not equal to expected");
    }
    @Then("^category should have been deleted$")
    public void verifyIfCategoryWasDeletedSuccessfully() {
        String schemaFilePath = "src|test|resources|api|json|schemas|DeleteCategorySchema.json".replace("|", File.separator);
        File schemaFile = new File(schemaFilePath);
        Response response = controller.getResponse();

        Boolean expectedStatus = true;
        Boolean actualStatus = controller.getResponse().jsonPath().getBoolean("deleted");

        Assert.assertEquals(actualStatus, expectedStatus, "wrong status value returned");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
    }
    @Then("^category should have been updated")
    public void verifyIfCategoryWasUpdatedSuccessfully() {
        String expectedName = (String) params.get("name");
        String expectedSlug = (String) params.get("slug");
        expectedSlug = expectedSlug.toLowerCase();
        String expectedDescription = (String) params.get("description");

        String actualName = controller.getResponse().jsonPath().getString("name");
        String actualSlug = controller.getResponse().jsonPath().getString("slug");
        String actualDescription = controller.getResponse().jsonPath().getString("description");

        Assert.assertEquals(actualName, expectedName, "The category name wast not updated correctly");
        Assert.assertEquals(actualSlug, expectedSlug, "The category slug was not updated correctly");
        Assert.assertEquals(actualDescription, expectedDescription, "The category description was not updated correctly");
    }
    @Then("^category shouldn't have been updated and the response has a error \"(.*?)\"")
    public void verifyIfCategoryWasNotUpdated(String message) {
        String actualMessage = controller.getResponse().jsonPath().getString("message");
        Assert.assertEquals(actualMessage, message, "The message error is not the correctly");
    }
    @Then("^category shouldn't have been deleted and the response has a error \"(.*?)\"")
    public void verifyIfCategoryWasNotDeleted(String message) {
        String actualMessage = controller.getResponse().jsonPath().getString("message");
        Assert.assertEquals(actualMessage, message);
    }
}
