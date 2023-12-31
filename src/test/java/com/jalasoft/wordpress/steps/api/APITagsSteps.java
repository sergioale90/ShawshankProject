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
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.StringManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This class has the steps methods for tag scenarios.
 */

public class APITagsSteps {
    private static final APIConfig API_CONFIG = APIConfig.getInstance();
    private static final APIManager API_MANAGER = APIManager.getInstance();

    private final APIController controller;
    private final String tagsEndpoint = API_CONFIG.getTagsEndpoint();
    private final String tagsByIdEndpoint = API_CONFIG.getTagsEndpointById();
    private Map<String, Object> params;
    private static final int PER_PAGE = 100;
    private static final int NAME_STRING_LENGHT = 7;
    private static final int SLUG_STRING_LENGHT = 5;
    private static final int DESCRIPTION_STRING_LENGHT = 15;

    public APITagsSteps(APIController controller) {
        this.controller = controller;
    }

    @Given("^the user tries to retrieve all tags list$")
    public void getAllTags() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", PER_PAGE);

        Response requestResponse = API_MANAGER.get(tagsEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }

    @Given("^the user makes a request to create a tag$")
    public void createATagStringGenerator() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryRequest = new HashMap<>();
        queryRequest.put("name", StringManager.generateAlphanumericString(NAME_STRING_LENGHT));
        queryRequest.put("slug", StringManager.generateAlphanumericString(SLUG_STRING_LENGHT).toLowerCase());
        queryRequest.put("description", StringManager.generateAlphanumericString(DESCRIPTION_STRING_LENGHT));

        Response requestResponse = API_MANAGER.post(tagsEndpoint, queryRequest, authHeader);
        controller.setResponse(requestResponse);
        params = queryRequest;
    }

    @Given("^the user creates a tag with the following params$")
    public void createATag(DataTable table) {
        Header authHeader = controller.getHeader("Authorization");
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        Response requestResponse = API_MANAGER.post(tagsEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
        params = queryParams;
    }

    @Then("^the new tag request response should have a valid schema$")
    public void verifyNewPostResponseSchema() {
        String schemaFilePath = "src|test|resources|api|json|schemas|NewTagSchema.json".replace("|", File.separator);
        File schemaFile = new File(schemaFilePath);

        String actualResponseBody = controller.getResponse().getBody().asString();
        JsonSchemaValidator expectedJsonSchema = JsonSchemaValidator.matchesJsonSchema(schemaFile);

        assertThat(actualResponseBody, expectedJsonSchema);
    }

    @Given("^the user tries to retrieve a tag$")
    public void getATagById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.get(tagsByIdEndpoint.replace("<id>", id), authHeader);

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

        Response requestResponse = API_MANAGER.put(tagsByIdEndpoint.replace("<id>", id), authHeader, ContentType.JSON, requestBody);
        controller.setResponse(requestResponse);

        params = new HashMap<>(queryParams);
        params.put("id", id);
    }

    @Given("^the user makes a request to delete a tag$")
    public void deleteATagById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParamDelete = new HashMap<>();
        queryParamDelete.put("force", true);

        Response requestResponse = API_MANAGER.delete(tagsByIdEndpoint.replace("<id>", id), queryParamDelete, authHeader);

        Map<String, Object> queryParams = new HashMap<>();
        String name = controller.getResponse().jsonPath().getString("name");
        String slug = controller.getResponse().jsonPath().getString("slug");
        String description = controller.getResponse().jsonPath().getString("description");

        queryParams.put("id", id);
        queryParams.put("name", name);
        queryParams.put("slug", slug);
        queryParams.put("description", description);
        queryParams.put("deleted", true);

        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @Given("^the user makes a request to delete a tag without force param$")
    public void deleteATagByIdWithoutForceParam() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");

        Response requestResponse = API_MANAGER.delete(tagsByIdEndpoint.replace("<id>", id), authHeader);
        controller.setResponse(requestResponse);
    }

    @Given("^the user makes a request to create a tag with the same name that other already created$")
    public void createATagWithTheSameName() {
        Header authHeader = controller.getHeader("Authorization");
        String nameTagCreated = controller.getResponse().jsonPath().getString("name");

        Map<String, Object> queryRequest = new HashMap<>();
        queryRequest.put("name", nameTagCreated);
        queryRequest.put("slug", StringManager.generateAlphanumericString(SLUG_STRING_LENGHT).toLowerCase());
        queryRequest.put("description", StringManager.generateAlphanumericString(DESCRIPTION_STRING_LENGHT));

        Response requestResponse = API_MANAGER.post(tagsEndpoint, queryRequest, authHeader);
        controller.setResponse(requestResponse);
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

    @Then("^the user reviews that the tag should have been created with the proper values$")
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

    @Then("^the user reviews that the tag should have been deleted$")
    public void verifyDeletedTag() {
        String expectedId = (String) params.get("id");
        String expectedName = (String) params.get("name");
        String expectedSlug = (String) params.get("slug");
        String expectedDescription = (String) params.get("description");
        boolean expectedStatus = (boolean) params.get("deleted");

        String actualId = controller.getResponse().jsonPath().getString("previous.id");
        String actualName = controller.getResponse().jsonPath().getString("previous.name");
        String actualSlug = controller.getResponse().jsonPath().getString("previous.slug");
        String actualDescription = controller.getResponse().jsonPath().getString("previous.description");
        boolean actualStatus = Boolean.parseBoolean(controller.getResponse().jsonPath().getString("deleted"));

        Assert.assertEquals(actualId, expectedId, "wrong id value returned");
        Assert.assertEquals(actualName, expectedName, "wrong name value returned");
        Assert.assertEquals(actualSlug, expectedSlug, "wrong slug value returned");
        Assert.assertEquals(actualDescription, expectedDescription, "wrong description value returned");
        Assert.assertEquals(actualStatus, expectedStatus, "wrong status value returned");
    }

    @Then("^the user reviews that the response should be returned and have a body with the following values$")
    public void verifyResponseAndBody(DataTable table) {
        List<Map<String, Object>> paramsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = paramsList.get(0);

        String expectedCode = (String) queryParams.get("code");
        String expectedMessage = (String) queryParams.get("message");
        String expectedData = (String) queryParams.get("data");

        String actualCode = controller.getResponse().jsonPath().getString("code");
        String actualMessage = controller.getResponse().jsonPath().getString("message");
        String actualData = controller.getResponse().jsonPath().getString("data");

        Assert.assertEquals(actualCode, expectedCode, "wrong code value returned");
        Assert.assertEquals(actualMessage, expectedMessage, "wrong message value returned");
        Assert.assertEquals(actualData, expectedData, "wrong data value returned");
    }
}
