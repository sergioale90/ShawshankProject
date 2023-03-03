package com.jalasoft.wordpress.steps.api;

import api.APIConfig;
import api.APIManager;
import api.controller.APIController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.StringManager;

import java.util.HashMap;
import java.util.Map;

public class APITagsSteps {
    private static final APIConfig apiConfig = APIConfig.getInstance();
    private static final APIManager apiManager = APIManager.getInstance();
    private final APIController controller;
    private final String tagsEndpoint = apiConfig.getTagsEndpoint();
    private final String tagsByIdEndpoint = apiConfig.getTagsEndpointById();
    private Map<String, Object> params;

    public APITagsSteps(APIController controller) {
        this.controller = controller;
    }

    @Given("^the user tries to retrieve all tags list$")
    public void getAllTags() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", 100);

        Response requestResponse = apiManager.get(tagsEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }

    @Given("^the user creates a tag with the following values$")
    public void createTag() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryRequest = new HashMap<>();
        queryRequest.put("name", StringManager.generateAlphanumericString(7));
        queryRequest.put("slug", StringManager.generateAlphanumericString(5).toLowerCase());
        queryRequest.put("description", StringManager.generateAlphanumericString(25));

        Response requestResponse = apiManager.post(tagsEndpoint, queryRequest, authHeader);
        controller.setResponse(requestResponse);
        params = queryRequest;
    }

    @Then("^the user should get a proper amount of tags$")
    public void verifyTagsAmount() {
        int expectedAmountOfTags = Integer.parseInt(controller.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfTags = controller.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(actualAmountOfTags, expectedAmountOfTags, "wrong amount of pages returned");
    }

    @Then("^the user reviews that the tag have been created with the proper values$")
    public void verifyCreatedTag() {
        String expectedName = (String) params.get("name");
        String expectedSlug = (String) params.get("slug");
        String expectedDescription = (String) params.get("description");

        String actualName = controller.getResponse().jsonPath().getString("name");
        String actualSlug = controller.getResponse().jsonPath().getString("slug");
        String actualDescription = controller.getResponse().jsonPath().getString("description");

        Assert.assertEquals(actualName, expectedName, "wrong name value returned");
        Assert.assertEquals(actualSlug, expectedSlug, "wrong slug value returned");
        Assert.assertEquals(actualDescription, expectedDescription, "wrong description value returned");
    }

}
