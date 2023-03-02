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

public class APITagsSteps {
    private static final APIConfig apiConfig = APIConfig.getInstance();
    private static final APIManager apiManager = APIManager.getInstance();
    private final APIController controller;
    private final String pagesEndpoint = apiConfig.getPagesEndpoint();
    private final String pagesByIdEndpoint = apiConfig.getPagesByIdEndpoint();
    private Map<String, Object> params;

    public APITagsSteps(APIController controller) {
        this.controller = controller;
    }

    @Given("^the user tries to retrieve all tags list$")
    public void getAllTags() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", 100);

        Response requestResponse = apiManager.get(pagesEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }

    @Given("^the user creates a tag with the following values$")
    public void createTag(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        Response requestResponse = apiManager.post(pagesEndpoint, queryParams, controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = queryParams;
    }

    @Then("^the user should get a proper amount of tags$")
    public void verifyTagsAmount() {
        int expectedAmountOfTags = Integer.parseInt(controller.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfTags = controller.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(actualAmountOfTags, expectedAmountOfTags, "wrong amount of pages returned");
    }

    @Then("^the user reviews that the tag have been created with the proper values$")
    public void verifyCreatedTag() {
    }

}
