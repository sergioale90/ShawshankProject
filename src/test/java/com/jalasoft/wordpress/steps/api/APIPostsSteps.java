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
import io.restassured.internal.http.Status;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.controller.UIController;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class APIPostsSteps {
    private static final APIConfig API_CONFIG = APIConfig.getInstance();
    private static final APIManager API_MANAGER = APIManager.getInstance();
    private final APIController controller;
    private final UIController uiController;
    private final String postsEndpoint = API_CONFIG.getPostsEndpoint();
    private final String postByIdEndpoint = API_CONFIG.getPostsByIdEndpoint();
    private Map<String, Object> params;
    private static final int PER_PAGE = 100;

    public APIPostsSteps(APIController controller, UIController uiController) {
        this.controller = controller;
        this.uiController = uiController;
    }

    @Given("^(?:I make|the user makes) a request to retrieve all posts$")
    public void getAllPosts() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", PER_PAGE);

        Response requestResponse = API_MANAGER.get(postsEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }

    @Given("^the user makes a request to create a post with the following params$")
    public void createAPost(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("status", "publish");
        queryParams.putAll(queryParamsList.get(0));
        Response requestResponse = API_MANAGER.post(postsEndpoint, queryParams, controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = queryParams;
        String id = controller.getResponse().jsonPath().getString("id");
        uiController.setId(id);
        uiController.setTitle((String) params.get("title"));
        uiController.setContent((String) params.get("content"));
    }

    @Given("^the user makes a request to create a draft post with the following params$")
    public void createADraftPost(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("status", "draft");
        queryParams.putAll(queryParamsList.get(0));
        Response requestResponse = API_MANAGER.post(postsEndpoint, queryParams, controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = queryParams;
        String id = controller.getResponse().jsonPath().getString("id");
        uiController.setId(id);
        uiController.setTitle((String) params.get("title"));
        uiController.setContent((String) params.get("content"));
    }

    @Given("^the user makes a request to retrieve a post$")
    public void getPostById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.get(postByIdEndpoint.replace("<id>", id), authHeader);
        controller.setIdAux(id);
        Map<String, Object> queryParams = new HashMap<>();
        String content = controller.getResponse().jsonPath().getString("content.raw");
        String title = controller.getResponse().jsonPath().getString("title.raw");
        String excerpt = controller.getResponse().jsonPath().getString("excerpt.raw");

        queryParams.put("id", id);
        queryParams.put("content", content);
        queryParams.put("title", title);
        queryParams.put("excerpt", excerpt);
        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @Given("^the user makes a request to update a post with the following params$")
    public void updatePostById(DataTable table) {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);
        Response requestResponse = API_MANAGER.put(postByIdEndpoint.replace("<id>", id), queryParams, authHeader);
        controller.setIdAux(id);
        params = new HashMap<>(queryParams);
        params.put("id", id);
        controller.setResponse(requestResponse);
    }

    @Given("^the user makes a request to delete a (?:published|draft) post by trash$")
    public void deletePostByIdTrash() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.delete(postByIdEndpoint.replace("<id>", id), authHeader);
        Map<String, Object> queryParams = new HashMap<>();
        controller.setIdAux(id);
        String content = controller.getResponse().jsonPath().getString("content.raw");
        String title = controller.getResponse().jsonPath().getString("title.raw");
        String excerpt = controller.getResponse().jsonPath().getString("excerpt.raw");

        queryParams.put("id", id);
        queryParams.put("content", content);
        queryParams.put("title", title);
        queryParams.put("excerpt", excerpt);
        queryParams.put("status", "trash");

        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @Given("^the user makes a request to delete a (?:published|draft) post permanently$")
    public void deletePostByIdPermanently() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        Map<String, Object> jsonAsMap = new HashMap<>();
        controller.setIdAux(id);
        String content = controller.getResponse().jsonPath().getString("content.raw");
        String title = controller.getResponse().jsonPath().getString("title.raw");
        String excerpt = controller.getResponse().jsonPath().getString("excerpt.raw");
        String status = controller.getResponse().jsonPath().getString("status");

        queryParams.put("id", id);
        queryParams.put("content", content);
        queryParams.put("title", title);
        queryParams.put("excerpt", excerpt);
        queryParams.put("status", status);
        queryParams.put("deleted", true);

        jsonAsMap.put("force", true);

        Response requestResponse = API_MANAGER.delete(postByIdEndpoint.replace("<id>", id), jsonAsMap,  authHeader);

        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @Given("the user makes a request to create a post using a json file")
    public void createAPostUsingAJsonFile() {
        String requestBodyFilePath = "src|test|resources|api|json|posts|NewPost.json".replace("|", File.separator);
        File requestBodyFile = new File(requestBodyFilePath);
        Object requestBody = JsonPath.from(requestBodyFile).get();
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("content", JsonPath.from(requestBodyFile).getString("content"));
        queryParams.put("title", JsonPath.from(requestBodyFile).getString("title"));
        queryParams.put("excerpt", JsonPath.from(requestBodyFile).getString("excerpt"));

        Response requestResponse = API_MANAGER.post(postsEndpoint, controller.getHeader("Authorization"), ContentType.JSON, requestBody);
        controller.setResponse(requestResponse);
        params = queryParams;
    }

    @Then("^response should have a proper amount of posts$")
    public void verifyPostsAmount() {
        int expectedAmountOfPosts = Integer.parseInt(controller.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfPosts = controller.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(actualAmountOfPosts, expectedAmountOfPosts, "wrong amount of posts returned");
    }

    @Then("^post should have been created with the proper params$")
    public void verifyCreatedPost() {
        String expectedContent = (String) params.get("content");
        String expectedTitle = (String) params.get("title");
        String expectedExcerpt = (String) params.get("excerpt");

        String actualContent = controller.getResponse().jsonPath().getString("content.raw");
        String actualTitle = controller.getResponse().jsonPath().getString("title.raw");
        String actualExcerpt = controller.getResponse().jsonPath().getString("excerpt.raw");

        Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
        Assert.assertEquals(actualTitle, expectedTitle, "wrong title value returned");
        Assert.assertEquals(actualExcerpt, expectedExcerpt, "wrong excerpt value returned");
    }

    @Then("^post should have been retrieved with the proper params$")
    public void verifyRetrievedPost() {
        if (Status.SUCCESS.matches(controller.getResponse().getStatusCode())) {
            String expectedId = (String) params.get("id");
            String expectedContent = (String) params.get("content");
            String expectedTitle = (String) params.get("title");
            String expectedExcerpt = (String) params.get("excerpt");

            String actualId = controller.getResponse().jsonPath().getString("id");
            String actualContent = controller.getResponse().jsonPath().getString("content.rendered")
                    .replaceAll("<[^>]*>", "").strip();
            String actualTitle = controller.getResponse().jsonPath().getString("title.rendered");
            String actualExcerpt = controller.getResponse().jsonPath().getString("excerpt.rendered")
                    .replaceAll("<[^>]*>", "").strip();

            Assert.assertEquals(actualId, expectedId, "wrong id value returned");
            Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
            Assert.assertEquals(actualTitle, expectedTitle, "wrong title value returned");
            Assert.assertEquals(actualExcerpt, expectedExcerpt, "wrong excerpt value returned");
        }
    }

    @Then("^post should have been updated with the proper params$")
    public void verifyUpdatedPost() {
        if (Status.SUCCESS.matches(controller.getResponse().getStatusCode())) {
            String expectedId = (String) params.get("id");
            String expectedContent = (String) params.get("content");
            String expectedTitle = (String) params.get("title");
            String expectedExcerpt = (String) params.get("excerpt");

            String actualId = controller.getResponse().jsonPath().getString("id");
            String actualContent = controller.getResponse().jsonPath().getString("content.raw");
            String actualTitle = controller.getResponse().jsonPath().getString("title.raw");
            String actualExcerpt = controller.getResponse().jsonPath().getString("excerpt.raw");

            Assert.assertEquals(actualId, expectedId, "wrong id value returned");
            Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
            Assert.assertEquals(actualTitle, expectedTitle, "wrong title value returned");
            Assert.assertEquals(actualExcerpt, expectedExcerpt, "wrong excerpt value returned");
        }
    }

    @Then("^(?:the published|the draft) post should have been trashed$")
    public void verifyTrashedPost() {
        if (Status.SUCCESS.matches(controller.getResponse().getStatusCode())) {
            String expectedId = (String) params.get("id");
            String expectedContent = (String) params.get("content");
            String expectedTitle = (String) params.get("title");
            String expectedExcerpt = (String) params.get("excerpt");
            String expectedStatus = (String) params.get("status");

            String actualId = controller.getResponse().jsonPath().getString("id");
            String actualContent = controller.getResponse().jsonPath().getString("content.raw");
            String actualTitle = controller.getResponse().jsonPath().getString("title.raw");
            String actualExcerpt = controller.getResponse().jsonPath().getString("excerpt.raw");
            String actualStatus = controller.getResponse().jsonPath().getString("status");

            Assert.assertEquals(actualId, expectedId, "wrong id value returned");
            Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
            Assert.assertEquals(actualTitle, expectedTitle, "wrong title value returned");
            Assert.assertEquals(actualExcerpt, expectedExcerpt, "wrong excerpt value returned");
            Assert.assertEquals(actualStatus, expectedStatus, "wrong status value returned");
        }
    }

    @Then("^(?:the published|the draft) post should have been deleted permanently$")
    public void verifyTrueDeletedPost() {
        if (Status.SUCCESS.matches(controller.getResponse().getStatusCode())) {
            String expectedId = (String) params.get("id");
            String expectedContent = (String) params.get("content");
            String expectedTitle = (String) params.get("title");
            String expectedExcerpt = (String) params.get("excerpt");
            String expectedStatus = (String) params.get("status");

            String actualId = controller.getResponse().jsonPath().getString("previous.id");
            String actualContent = controller.getResponse().jsonPath().getString("previous.content.raw");
            String actualTitle = controller.getResponse().jsonPath().getString("previous.title.raw");
            String actualExcerpt = controller.getResponse().jsonPath().getString("previous.excerpt.raw");
            String actualStatus = controller.getResponse().jsonPath().getString("previous.status");

            Assert.assertEquals(actualId, expectedId, "wrong id value returned");
            Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
            Assert.assertEquals(actualTitle, expectedTitle, "wrong title value returned");
            Assert.assertEquals(actualExcerpt, expectedExcerpt, "wrong excerpt value returned");
            Assert.assertEquals(actualStatus, expectedStatus, "wrong status value returned");
        }
    }

    @Then("^new post request response should have a valid schema for \"(.*?)\"$")
    public void verifyNewPostResponseSchema(String userRole) {
        String schemaFilePath = (API_CONFIG.getSchemaPathByUserRole(userRole)).replace("|", File.separator);
        File schemaFile = new File(schemaFilePath);

        String actualResponseBody = controller.getResponse().getBody().asString();
        JsonSchemaValidator expectedJsonSchema = JsonSchemaValidator.matchesJsonSchema(schemaFile);

        assertThat(actualResponseBody, expectedJsonSchema);
    }
}
