/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.api;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import api.APIConfig;
import api.APIManager;
import api.controller.APIController;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
//import io.restassured.internal.http.Status;
import io.restassured.response.Response;
//import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;

//import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIUsersSteps {

    private static final APIConfig API_CONFIG = APIConfig.getInstance();

    private static final APIManager API_MANAGER = APIManager.getInstance();

    private final APIController controller;

    private final String usersEndpoint = API_CONFIG.getUsersEndpoint();

    private final String usersByIdEndpoint = API_CONFIG.getUsersByIdEndpoint();

    private Map<String, Object> params;

    private static final int PER_PAGE = 2;

    public APIUsersSteps(APIController controller) {
        this.controller = controller;
    }

    @When("the user makes a request to retrieve all users")
    public void theUserMakesARequestToRetrieveAllUsers() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();

        Response requestResponse = API_MANAGER.get(usersEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }

    @When("the user makes a request to retrieve all users with {string}")
    public void theUserMakesARequestToRetrieveAllUsersWith(String role) {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("roles", role);

        Response requestResponse = API_MANAGER.get(usersEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);

    }

    @And("the user should get a list of users with the specified role")
    public void theUserShouldGetAListOfUsersWithTheSpecifiedRole() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        Assert.assertFalse(controller.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(controller.getResponse().getContentType(), expectedContentType,
                "wrong content type returned");
    }

    @When("the user makes a request to retrieve two users per page")
    public void theUserMakesARequestToRetrieveTwoUsersPerPage() {
        Header authHeader = controller.getHeader("Authorization");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", PER_PAGE);

        Response requestResponse = API_MANAGER.get(usersEndpoint, queryParams, authHeader);
        controller.setResponse(requestResponse);
    }

    @And("the user should get a list of users with two users")
    public void theUserShouldGetAListOfUsersWithTwoUsers() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        Assert.assertFalse(controller.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(controller.getResponse().getContentType(), expectedContentType,
                "wrong content type returned");
        Assert.assertEquals(controller.getResponse().jsonPath().getList(expectedContentType).size(), PER_PAGE);
    }

    @When("the user makes a request to create a new user with the following params")
    public void theUserMakesARequestToCreateANewUserWithTheFollowingParams(DataTable table) {

        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        Response requestResponse = API_MANAGER.post(usersEndpoint, queryParams, controller.getHeader("Authorization"));
        controller.setResponse(requestResponse);
        params = queryParams;
    }

    @And("a new user should have been created with the proper params")
    public void aNewUserShouldHaveBeenCreatedWithTheProperParams() {
        String expectedContent = (String) params.get("username");
        String expectedTitle = (String) params.get("email");
        String expectedExcerpt = (String) params.get("roles");

        String actualContent = controller.getResponse().jsonPath().getString("username");
        String actualTitle = controller.getResponse().jsonPath().getString("email");
        String actualExcerpt = controller.getResponse().jsonPath().getString("roles").replaceAll("\\[|\\]", "");

        Assert.assertEquals(actualContent, expectedContent, "wrong username value returned");
        Assert.assertEquals(actualTitle, expectedTitle, "wrong email value returned");
        Assert.assertEquals(actualExcerpt, expectedExcerpt, "wrong roles value returned");
    }

    @When("the user makes a request to retrieve a user")
    public void theUserMakesARequestToRetrieveAUser() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.get(usersByIdEndpoint.replace("<id>", id), authHeader);

        System.out.println(id);

        Map<String, Object> queryParams = new HashMap<>();
        String name = controller.getResponse().jsonPath().getString("name");
        String slug = controller.getResponse().jsonPath().getString("slug");

        queryParams.put("id", id);
        queryParams.put("name", name);
        queryParams.put("slug", slug);

        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @And("a user should have been retrieved with the proper params")
    public void aUserShouldHaveBeenRetrievedWithTheProperParams() {
        String expectedId = (String) params.get("id");
        String expectedName = (String) params.get("name");
        String expectedSlug = (String) params.get("slug");

        String actualId = controller.getResponse().jsonPath().getString("id");
        String actualName = controller.getResponse().jsonPath().getString("name");
        String actualSlug = controller.getResponse().jsonPath().getString("slug");

        Assert.assertEquals(actualId, expectedId, "wrong id value returned");
        Assert.assertEquals(actualName, expectedName, "wrong name value returned");
        Assert.assertEquals(actualSlug, expectedSlug, "wrong slug value returned");
    }

    @When("the user makes a request to update a user with the following params")
    public void theUserMakesARequestToUpdateAUserWithTheFollowingParams(DataTable table) {

        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);
        Response requestResponse = API_MANAGER.put(usersByIdEndpoint.replace("<id>", id), queryParams, authHeader);

        params = new HashMap<>(queryParams);
        params.put("id", id);
        controller.setResponse(requestResponse);
    }

    @And("a user should have been updated with the proper params")
    public void aUserShouldHaveBeenUpdatedWithTheProperParams() {
        String expectedId = (String) params.get("id");
        String expectedEmail = (String) params.get("email");
        String expectedRole = (String) params.get("roles");

        String actualId = controller.getResponse().jsonPath().getString("id");
        String actualEmail = controller.getResponse().jsonPath().getString("email");
        String actualRole = controller.getResponse().jsonPath().getString("roles").replaceAll("\\[|\\]", "");

        Assert.assertEquals(actualId, expectedId, "wrong id value returned");
        Assert.assertEquals(actualEmail, expectedEmail, "wrong email value returned");
        Assert.assertEquals(actualRole, expectedRole, "wrong role value returned");
    }

    @When("the user makes a request to delete a user")
    public void theUserMakesARequestToDeleteAUser() {

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("reassign", 1);
        jsonAsMap.put("force", true);

        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.delete(usersByIdEndpoint.replace("<id>", id), jsonAsMap, authHeader);
        Map<String, Object> queryParams = new HashMap<>();

        String username = controller.getResponse().jsonPath().getString("username");
        String email = controller.getResponse().jsonPath().getString("email");
        String actualRole = controller.getResponse().jsonPath().getString("roles").replaceAll("\\[|\\]", "");

        queryParams.put("id", id);
        queryParams.put("username", username);
        queryParams.put("email", email);
        queryParams.put("actualRole", actualRole);

        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @And("the user should have been deleted")
    public void theUserShouldHaveBeenDeleted() {
        String expectedId = (String) params.get("id");
        String expectedUsername = (String) params.get("username");
        String expectedEmail = (String) params.get("email");
        String expectedRoles = (String) params.get("actualRole");

        String actualId = controller.getResponse().jsonPath().getString("previous.id");
        String actualUsername = controller.getResponse().jsonPath().getString("previous.username");
        String actualEmail = controller.getResponse().jsonPath().getString("previous.email");
        String actualRoles = controller.getResponse().jsonPath().getString("previous.roles").replaceAll("\\[|\\]", "");
        String actualDeleted = controller.getResponse().jsonPath().getString("deleted");

        Assert.assertEquals(actualId, expectedId, "wrong id value returned");
        Assert.assertEquals(actualUsername, expectedUsername, "wrong username value returned");
        Assert.assertEquals(actualEmail, expectedEmail, "wrong email value returned");
        Assert.assertEquals(actualRoles, expectedRoles, "wrong roles value returned");
        Assert.assertEquals(actualDeleted, "true", "wrong deleted value returned");
    }

    @When("the user makes a request to retrieve the current user")
    public void theUserMakesARequestToRetrieveTheCurrentUser() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.get(usersByIdEndpoint.replace("<id>", "me"), authHeader);

        // System.out.println(id);

        Map<String, Object> queryParams = new HashMap<>();
        String name = controller.getResponse().jsonPath().getString("name");
        String slug = controller.getResponse().jsonPath().getString("slug");

        queryParams.put("id", id);
        queryParams.put("name", name);
        queryParams.put("slug", slug);

        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @When("the user makes a request to update a current user with the following params")
    public void theUserMakesARequestToUpdateACurrentUserWithTheFollowingParams(DataTable table) {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);
        Response requestResponse = API_MANAGER.put(usersByIdEndpoint.replace("<id>", "me"), queryParams, authHeader);

        params = new HashMap<>(queryParams);
        params.put("id", id);
        controller.setResponse(requestResponse);
    }

    @When("the user makes a request to delete the current user")
    public void theUserMakesARequestToDeleteTheCurrentUser() {

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("reassign", 1);
        jsonAsMap.put("force", true);

        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = API_MANAGER.delete(usersByIdEndpoint.replace("<id>", "me"), jsonAsMap, authHeader);
        Map<String, Object> queryParams = new HashMap<>();

        String username = controller.getResponse().jsonPath().getString("username");
        String email = controller.getResponse().jsonPath().getString("email");
        String actualRole = controller.getResponse().jsonPath().getString("roles").replaceAll("\\[|\\]", "");

        queryParams.put("id", id);
        queryParams.put("username", username);
        queryParams.put("email", email);
        queryParams.put("actualRole", actualRole);

        params = queryParams;
        controller.setResponse(requestResponse);
    }

    @And("the body should comply with the schema")
    public void theBodyShouldComplyWithTheSchema() {
//        controller.getResponse().then()
//                .assertThat()
//                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/schemas/schema_create_users.json")));
    }
}
