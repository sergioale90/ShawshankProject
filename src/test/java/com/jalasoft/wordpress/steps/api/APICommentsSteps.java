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
import api.methods.APIPostsMethods;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class is responsible for providing all the necessary methods for
 * the API tests to be performed by the commenting feature.
 *
 * @version 1.0
 */
public class APICommentsSteps {
    private static final APIConfig API_CONFIG = APIConfig.getInstance();
    private static final APIManager API_MANAGER = APIManager.getInstance();
    private final APIController controller;
    private final String commentsEndpoint = API_CONFIG.getCommentsEndpoint();
    private final String commentsByIdEndpoint = API_CONFIG.getCommentsByIdEndpoint();
    private Map<String, Object> params;
    private static final int PER_PAGE = 2;

    public APICommentsSteps(APIController controller) {
        this.controller = controller;
    }

    @When("the user makes a request to retrieve all comments")
    public void retrieveAllComments() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();

        Response requestResponse = API_MANAGER.get(commentsEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }

    @When("the user makes a request to retrieve two comments per page")
    public void retrieveTwoCommentsPerPage() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", PER_PAGE);

        Response requestResponse = API_MANAGER.get(commentsEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }

    @And("the user should get a list with two comments")
    public void listWithTwoComments() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        Assert.assertFalse(controller.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(controller.getResponse().getContentType(), expectedContentType,
                "wrong content type returned");
        Assert.assertEquals(controller.getResponse().jsonPath().getList(expectedContentType).size(), PER_PAGE);
    }

    @When("the user makes a request to publish a new comment with the following content")
    public void publishANewComment(DataTable table) {
        Response postResponse = APIPostsMethods.createAPost();
        controller.setIdAux(postResponse.jsonPath().getString("id"));

        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = new HashMap<>(queryParamsList.get(0));
        queryParams.put("post", controller.getIdAux());

        Response requestResponse = API_MANAGER.post(commentsEndpoint, queryParams,
                controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = queryParams;
    }

    @And("a new comment should have been publish")
    public void newCommentPublish() {
        String expectedContent = (String) params.get("content");
        String actualContent = controller.getResponse().jsonPath().getString("content.raw");
        Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
    }

    @When("the user makes a request to retrieve a comment")
    public void retrieveAComment() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.get(commentsByIdEndpoint.replace("<id>", id), authHeader);

        Map<String, Object> queryParams = new HashMap<>();
        String post = controller.getResponse().jsonPath().getString("post");
        String author = controller.getResponse().jsonPath().getString("author");
        String content = controller.getResponse().jsonPath().getString("content.rendered");

        queryParams.put("id", id);
        queryParams.put("post", post);
        queryParams.put("author", author);
        queryParams.put("content", content);

        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @And("a comment should have been retrieved")
    public void commentRetrieved() {
        String expectedId = (String) params.get("id");
        String expectedPost = (String) params.get("post");
        String expectedAuthor = (String) params.get("author");
        String expectedContent = (String) params.get("content");

        String actualId = controller.getResponse().jsonPath().getString("id");
        String actualPost = controller.getResponse().jsonPath().getString("post");
        String actualAuthor = controller.getResponse().jsonPath().getString("author");
        String actualContent = controller.getResponse().jsonPath().getString("content.rendered");

        Assert.assertEquals(actualId, expectedId, "wrong id value returned");
        Assert.assertEquals(actualPost, expectedPost, "wrong post value returned");
        Assert.assertEquals(actualAuthor, expectedAuthor, "wrong author value returned");
        Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
    }

    @When("the user makes a request to update a comment with the following params")
    public void updateAComment(DataTable table) {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);
        Response requestResponse = API_MANAGER.put(commentsByIdEndpoint.replace("<id>", id), queryParams, authHeader);

        params = new HashMap<>(queryParams);
        params.put("id", id);
        controller.setResponse(requestResponse);
    }

    @And("a comment should have been updated with the proper params")
    public void commentHaveBeenUpdated() {
        String expectedId = (String) params.get("id");
        String expectedContent = (String) params.get("content");

        String actualId = controller.getResponse().jsonPath().getString("id");
        String actualContent = controller.getResponse().jsonPath().getString("content.raw");

        Assert.assertEquals(actualId, expectedId, "wrong id value returned");
        Assert.assertEquals(actualContent, expectedContent, "wrong role content returned");
    }

    @When("the user makes a request to delete a comment published")
    public void deleteAComment() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        String id = controller.getResponse().jsonPath().getString("id");
        String post = controller.getResponse().jsonPath().getString("post");
        String author_name = controller.getResponse().jsonPath().getString("author_name");
        String content = controller.getResponse().jsonPath().getString("content");

        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.delete(commentsByIdEndpoint.replace("<id>", id), jsonAsMap, authHeader);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("post", post);
        queryParams.put("author_name", author_name);
        queryParams.put("content", content);

        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @And("the comment should have been deleted")
    public void commentHaveBeenDeleted() {
        String expectedId = (String) params.get("id");
        String expectedPost = (String) params.get("post");
        String expectedAuthor_name = (String) params.get("author_name");
        String expectedContent = (String) params.get("content");

        String actualId = controller.getResponse().jsonPath().getString("previous.id");
        String actualPost = controller.getResponse().jsonPath().getString("previous.post");
        String actualAuthor_name = controller.getResponse().jsonPath().getString("previous.author_name");
        String actualContent = controller.getResponse().jsonPath().getString("previous.content");
        String actualDeleted = controller.getResponse().jsonPath().getString("deleted");

        Assert.assertEquals(actualId, expectedId, "wrong id value returned");
        Assert.assertEquals(actualPost, expectedPost, "wrong post value returned");
        Assert.assertEquals(actualAuthor_name, expectedAuthor_name, "wrong author_name value returned");
        Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
        Assert.assertEquals(actualDeleted, "true", "wrong deleted value returned");
    }

    @When("the user makes a request to moved to the trash a comment published")
    public void movedToTheTrashAComment() {
        String id = controller.getResponse().jsonPath().getString("id");
        String post = controller.getResponse().jsonPath().getString("post");
        String author_name = controller.getResponse().jsonPath().getString("author_name");
        String content = controller.getResponse().jsonPath().getString("content");

        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.delete(commentsByIdEndpoint.replace("<id>", id), authHeader);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("post", post);
        queryParams.put("author_name", author_name);
        queryParams.put("content", content);

        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @And("the comment should be moved to the trash")
    public void commentBeMovedToTheTrash() {
        String expectedId = (String) params.get("id");
        String expectedPost = (String) params.get("post");
        String expectedAuthor_name = (String) params.get("author_name");
        String expectedContent = (String) params.get("content");

        String actualId = controller.getResponse().jsonPath().getString("id");
        String actualPost = controller.getResponse().jsonPath().getString("post");
        String actualAuthor_name = controller.getResponse().jsonPath().getString("author_name");
        String actualContent = controller.getResponse().jsonPath().getString("content");
        String actualStatus = controller.getResponse().jsonPath().getString("status");

        Assert.assertEquals(actualId, expectedId, "wrong id value returned");
        Assert.assertEquals(actualPost, expectedPost, "wrong post value returned");
        Assert.assertEquals(actualAuthor_name, expectedAuthor_name, "wrong author_name value returned");
        Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
        Assert.assertEquals(actualStatus, "trash", "wrong deleted value returned");
    }

    @When("the user makes a request to delete a comment nonexistent")
    public void deleteACommentNonexistent(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        String id = queryParams.get("id").toString();
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.delete(commentsByIdEndpoint.replace("<id>", id), authHeader);
        controller.setResponse(requestResponse);
    }

    @And("the comment shouldn't have been created and the response has a error {string}")
    public void commentDoNotHaveBeenCreated(String message) {
        String actualMessage = controller.getResponse().jsonPath().getString("message");
        Assert.assertEquals(actualMessage, message, "The message error is not the correctly");
    }

    @When("the user makes a request to update a comment nonexistent")
    public void updateACommentNonexistent(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        String id = queryParams.get("id").toString();
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.delete(commentsByIdEndpoint.replace("<id>", id), authHeader);
        controller.setResponse(requestResponse);
    }

    @And("the comment shouldn't have been update and the response has a error {string}")
    public void commentDoNotHaveBeenUpdate(String message) {
        String actualMessage = controller.getResponse().jsonPath().getString("message");
        Assert.assertEquals(actualMessage, message, "The message error is not the correctly");
    }

    @When("the user makes a request to retrieve all comments of a post")
    public void retrieveAllCommentsOfAPost() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("post", controller.getIdAux());

        Response requestResponse = API_MANAGER.get(commentsEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }

    @And("the user should get a list of comments with all comments of a post")
    public void getAListOfCommentsOfAPost() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        Assert.assertFalse(controller.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(controller.getResponse().getContentType(), expectedContentType,
                "wrong content type returned");

        controller.getResponse().jsonPath().getString("message");
        List<Object> items = controller.getResponse().jsonPath().getList("post");

        for (Object item : items) {
            Assert.assertEquals(item.toString(), controller.getIdAux());
        }
    }
}
