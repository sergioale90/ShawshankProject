package com.jalasoft.wordpress.steps.api;

import api.APIConfig;
import api.APIManager;
import api.controller.APIController;
import api.methods.APIAuthMethods;
import api.methods.APIPostsMethods;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIPostsSteps {
    private static final APIConfig apiConfig = APIConfig.getInstance();
    private static final APIManager apiManager = APIManager.getInstance();
    private final APIController controller;
    private final String postsEndpoint = apiConfig.getPostsEndpoint();

    private final String postByIdEndpoint = apiConfig.getPostsByIdEndpoint();
    private Map<String, Object> params;

    public APIPostsSteps(APIController controller) {
        this.controller = controller;
    }

    @Given("^(?:I make|the user makes) a request to retrieve all posts$")
    public void getAllPosts() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", 100);

        Response requestResponse = apiManager.get(postsEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }

    @Given("^the user makes a request to create a post with the following params$")
    public void createAPost(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("status", "publish");
        queryParams.putAll(queryParamsList.get(0));

        Response requestResponse = apiManager.post(postsEndpoint, queryParams, controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = queryParams;
    }

    @Given("^the user makes a request to create a draft post with the following params$")
    public void createADraftPost(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("status", "draft");
        queryParams.putAll(queryParamsList.get(0));

        Response requestResponse = apiManager.post(postsEndpoint, queryParams, controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = queryParams;
    }

    @Given("^the user makes a request to retrieve a post$")
    public void getPostById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = apiManager.get(postByIdEndpoint.replace("<id>", id), authHeader);

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
        Response requestResponse = apiManager.put(postByIdEndpoint.replace("<id>", id), queryParams, authHeader);

        params = new HashMap<>(queryParams);
        params.put("id", id);
        controller.setResponse(requestResponse);
    }

    @Given("^the user makes a request to delete a post by trash$")
    public void deletePostByIdTrash() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = apiManager.delete(postByIdEndpoint.replace("<id>", id), authHeader);
        Map<String, Object> queryParams = new HashMap<>();

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

    @Given("^the user makes a request to delete a post permanently$")
    public void deletePostByIdPermanently() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        Map<String, Object> jsonAsMap = new HashMap<>();

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

        Response requestResponse = apiManager.delete(postByIdEndpoint.replace("<id>", id), jsonAsMap,  authHeader);
        params = queryParams;
        controller.setResponse(requestResponse);
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
        String expectedId = (String) params.get("id");
        String expectedContent = (String) params.get("content");
        String expectedTitle = (String) params.get("title");
        String expectedExcerpt = (String) params.get("excerpt");

        String actualId = controller.getResponse().jsonPath().getString("id");
        String actualContent = controller.getResponse().jsonPath().getString("content.rendered").replaceAll("<[^>]*>", "").strip();
        String actualTitle = controller.getResponse().jsonPath().getString("title.rendered");
        String actualExcerpt = controller.getResponse().jsonPath().getString("excerpt.rendered").replaceAll("<[^>]*>", "").strip();

        Assert.assertEquals(actualId, expectedId, "wrong id value returned");
        Assert.assertEquals(actualContent, expectedContent, "wrong content value returned");
        Assert.assertEquals(actualTitle, expectedTitle, "wrong title value returned");
        Assert.assertEquals(actualExcerpt, expectedExcerpt, "wrong excerpt value returned");
    }

    @Then("^post should have been updated with the proper params$")
    public void verifyUpdatedPost() {
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

    @Then("^post should have been trashed$")
    public void verifyTrashedPost() {
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

    @Then("^post should have been deleted permanently$")
    public void verifyTrueDeletedPost() {
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
