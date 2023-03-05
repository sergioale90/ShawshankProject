/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package api.methods;

import api.APIConfig;
import api.APIManager;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import utils.LoggerManager;
import utils.StringManager;

import java.util.HashMap;
import java.util.Map;

public class APICommentsMethods {

    public static final LoggerManager LOG = LoggerManager.getInstance();

    public static final APIManager API_MANAGER = APIManager.getInstance();

    private static final APIConfig API_CONFIG = APIConfig.getInstance();

    private static final int PER_PAGE = 100;

    private static final int STRING_LENGHT = 15;

    public static Response publishAComment(String id) {
        String commentEndpoint = API_CONFIG.getCommentsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("post", id);
        jsonAsMap.put("content", StringManager.generateAlphanumericString(STRING_LENGHT));

        return API_MANAGER.post(commentEndpoint, jsonAsMap, authHeader);
    }

    public static Header loginAUser() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", "James");
        jsonAsMap.put("password", "James123");
        String tokenEndpoint = API_CONFIG.getTokenEndpoint();
        Response response = API_MANAGER.post(tokenEndpoint, ContentType.JSON, jsonAsMap);

        if (Status.SUCCESS.matches(response.getStatusCode())) {
            LOG.info("Authentication Token retrieved");
            String tokenType = response.jsonPath().getString("token_type");
            String token = response.jsonPath().getString("jwt_token");
            String authorization = tokenType + " " + token;
            return new Header("Authorization", authorization);
        } else {
            LOG.error("Failed to retrieve Authentication Token");
            return null;
        }
    }

    public static Response deleteACommentById(String id) {

        String commentsByIdEndpoint = API_CONFIG.getCommentsByIdEndpoint().replace("<id>", id.replaceAll("\\[|\\]", ""));
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        return API_MANAGER.delete(commentsByIdEndpoint, jsonAsMap, authHeader);
    }

    public static Response createADraftPost() {
        String postsEndpoint = API_CONFIG.getPostsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("content", "Draft Test WAPI Post Content");
        jsonAsMap.put("title", "Draft Test WAPI Title");
        jsonAsMap.put("excerpt", "Draft Test WAPI Excerpt");
        jsonAsMap.put("status", "draft");

        return API_MANAGER.post(postsEndpoint, jsonAsMap, authHeader);
    }

    public static Response getAllPosts() {
        String postsEndpoint = API_CONFIG.getPostsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("per_page", PER_PAGE);

        return API_MANAGER.get(postsEndpoint, jsonAsMap, authHeader);
    }

}
