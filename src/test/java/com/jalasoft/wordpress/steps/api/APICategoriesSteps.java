package com.jalasoft.wordpress.steps.api;

import api.APIConfig;
import api.APIManager;
import api.controller.APIController;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Header;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.StringManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APICategoriesSteps {
    private static final APIConfig apiConfig = APIConfig.getInstance();
    private static final APIManager apiManager = APIManager.getInstance();
    private final APIController controller;
    private final String categoriesEndpoint = apiConfig.getCategoriesEndpoint();
    private final String categoriesEndpointById = apiConfig.getCategoriesEndpointById();
    public Map<String, Object> params;
    String errorMessage;
    public APICategoriesSteps(APIController controller) {
        this.controller = controller;
    }


    @Given("^the user makes a request to retrieve all categories$")
    public void getAllCategories() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", 100);

        Response requestResponse = apiManager.get(categoriesEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }
    @Given("^the user makes a request to create a category$")
    public void createANewCategory() {
        Map<String, Object> queryRequest = new HashMap<>();
        queryRequest.put("name", StringManager.generateAlphanumericString(7));
        queryRequest.put("slug", StringManager.generateAlphanumericString(5));
        queryRequest.put("description", StringManager.generateAlphanumericString(25));

        Response requestResponse = apiManager.post(categoriesEndpoint, queryRequest, controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = queryRequest;
    }
    @Given("^the user makes a request to delete a existent category$")
    public void deleteACategory() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryRequest = new HashMap<>();
        queryRequest.put("force", true);
        Response requestResponse = apiManager.delete(categoriesEndpointById.replace("<id>", id), queryRequest, authHeader);
        controller.setIdAux(id);
        controller.setResponse(requestResponse);
    }
    @Given ("^the user makes a request to update a existent category$")
    public void updateACategory() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryRequest = new HashMap<>();
        queryRequest.put("name", StringManager.generateAlphanumericString(7));
        queryRequest.put("slug", StringManager.generateAlphanumericString(5));
        queryRequest.put("description", StringManager.generateAlphanumericString(15));
        Response requestResponse = apiManager.put(categoriesEndpointById.replace("<id>", id), queryRequest, authHeader);
        params = queryRequest;
        controller.setIdAux(id);
        controller.setResponse(requestResponse);

    }
    @Given("^the user makes a request to retrieve a category")
    public void retrieveACategory() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = apiManager.get(categoriesEndpointById.replace("<id>", id), authHeader);
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
        int expectedAmountOfCategories = Integer.parseInt(controller.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfCategories = controller.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(actualAmountOfCategories, expectedAmountOfCategories, "wrong amount of categories returned");
    }
    @Then("^category should have been created with the proper name$")
    public void verifyIfCategoryWasCreatedProperly() {
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
    @Then("^category shouldn't have been created and the response has a error \"(.*?)\"$")
    public void verifyIfCategoryNotCreated(String message) {
        String actualMessage = controller.getResponse().jsonPath().getString("message");
        Assert.assertEquals(actualMessage, message, "Error message in response is not equal to expected");
    }
    @Then("^category should have been deleted$")
    public void verifyIfCategoryWasDeletedSuccessfully() {
        Boolean expectedStatus = true;
        Boolean actualStatus = controller.getResponse().jsonPath().getBoolean("deleted");

        Assert.assertEquals(actualStatus, expectedStatus, "wrong status value returned");
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
