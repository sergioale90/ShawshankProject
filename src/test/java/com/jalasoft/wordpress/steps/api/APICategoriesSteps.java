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
    public void createANewCategory(DataTable dataTable) {
        List<Map<String, Object>> queryParamsList = dataTable.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        Response requestResponse = apiManager.post(categoriesEndpoint, queryParams, controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = queryParams;
    }
    @Then("^response should have a proper amount of categories$")
    public void verifyIfResponseHasTheListOfCategories() {
        int expectedAmountOfCategories = Integer.parseInt(controller.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfCategories = controller.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(actualAmountOfCategories, expectedAmountOfCategories, "wrong amount of categories returned");
    }
    @Then("^category should have been created with the proper name$")
    public void categoryCreatedProperly() {
        String expectedCategoryName = (String) params.get("name");
        String actualCategoryName = controller.getResponse().jsonPath().getString("name");
        Assert.assertEquals(actualCategoryName, expectedCategoryName, "The category was not created properly");
    }
}
