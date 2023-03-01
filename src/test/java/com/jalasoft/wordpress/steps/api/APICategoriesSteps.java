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
    public APICategoriesSteps(APIController controller) {
        this.controller = controller;
    }
    public Map<String, Object> params;

    @Given("^the user makes a request to retrieve all categories$")
    public void getAllCategories() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", 100);

        Response requestResponse = apiManager.get(categoriesEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }
    @Given("^the user makes a request to create a category only with a name$")
    public void createANewCategory() {
        Map<String, Object> categoryName = new HashMap<>();
        categoryName.put("name", StringManager.generateAlphanumericString(5));

        Response requestResponse = apiManager.post(categoriesEndpoint, categoryName, controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = categoryName;
    }
    @Given("^the user makes a request to delete a existent category$")
    public void deleteACategory() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryRequest = new HashMap<>();
        queryRequest.put("force", true);
        Response requestResponse = apiManager.delete(categoriesEndpointById.replace("<id>", id), queryRequest, authHeader);
        Map<String, Object> queryParams = new HashMap<>();


        queryParams.put("id", id);
        queryParams.put("deleted", true);
        queryParams.put("force", true);

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
        String actualCategoryName = controller.getResponse().jsonPath().getString("name");
        Assert.assertEquals(actualCategoryName, expectedCategoryName, "The category was not created properly");
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
}
