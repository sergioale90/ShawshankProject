package com.jalasoft.wordpress.steps.api;

import api.APIConfig;
import api.APIManager;
import api.controller.APIController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.StringManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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
    public void createATag() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryRequest = new HashMap<>();
        queryRequest.put("name", StringManager.generateAlphanumericString(7));
        queryRequest.put("slug", StringManager.generateAlphanumericString(5).toLowerCase());
        queryRequest.put("description", StringManager.generateAlphanumericString(25));

        Response requestResponse = apiManager.post(tagsEndpoint, queryRequest, authHeader);
        controller.setResponse(requestResponse);
        params = queryRequest;
    }

    @Given("^the user tries to retrieve a tag$")
    public void getATagById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = apiManager.get(tagsByIdEndpoint.replace("<id>", id), authHeader);

        Map<String, Object> queryParams = new HashMap<>();
        String name = controller.getResponse().jsonPath().getString("name");
        String slug = controller.getResponse().jsonPath().getString("slug");
        String description = controller.getResponse().jsonPath().getString("description");

        queryParams.put("id", id);
        queryParams.put("name", name);
        queryParams.put("slug", slug);
        queryParams.put("description", description);
        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @Given("the user updates a tag using a json file")
    public void updateATagUsingAJsonFile() {
        Header authHeader = controller.getHeader("Authorization");
        String id = controller.getResponse().jsonPath().getString("id");

        String requestBodyFilePath = "src|test|resources|api|json|tags|UpdateTag.json".replace("|", File.separator);
        File requestBodyFile = new File(requestBodyFilePath);
        Object requestBody = JsonPath.from(requestBodyFile).get();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", JsonPath.from(requestBodyFile).getString("name"));
        queryParams.put("slug", JsonPath.from(requestBodyFile).getString("slug").toLowerCase());
        queryParams.put("description", JsonPath.from(requestBodyFile).getString("description"));

        Response requestResponse = apiManager.put(tagsByIdEndpoint.replace("<id>", id), authHeader, ContentType.JSON, requestBody);
        controller.setResponse(requestResponse);

        params = new HashMap<>(queryParams);
        params.put("id", id);
    }

    @Then("^the user reviews that the tag should have been retrieved with the proper values$")
    public void verifyRetrievedTag() {
        String expectedId = (String) params.get("id");
        String expectedName = (String) params.get("name");
        String expectedSlug = (String) params.get("slug");
        String expectedDescription = (String) params.get("description");

        String actualId = controller.getResponse().jsonPath().getString("id");
        String actualName = controller.getResponse().jsonPath().getString("name");
        String actualSlug = controller.getResponse().jsonPath().getString("slug");
        String actualDescription = controller.getResponse().jsonPath().getString("description");

        Assert.assertEquals(actualId, expectedId, "wrong id value returned");
        Assert.assertEquals(actualName, expectedName, "wrong name value returned");
        Assert.assertEquals(actualSlug, expectedSlug, "wrong slug returned");
        Assert.assertEquals(actualDescription, expectedDescription, "wrong description value returned");
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

    @Then("^the user reviews that the tag should have been updated with the proper values$")
    public void verifyUpdateTag() {
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
