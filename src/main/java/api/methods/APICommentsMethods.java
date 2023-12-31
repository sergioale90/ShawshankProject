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

/**
 * This class is responsible for providing the required methods for the classes that consume the API requests for the
 * Comments feature with a provided user role and Id
 *
 * @version 1.0
 */

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

    public static Response deleteACommentById(String id) {
        String commentsByIdEndpoint = API_CONFIG.getCommentsByIdEndpoint().replace("<id>", id.replaceAll("\\[|\\]", ""));
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        return API_MANAGER.delete(commentsByIdEndpoint, jsonAsMap, authHeader);
    }

    public static Response createComment(String postId) {
        Header authHeader = APIAuthMethods.getAuthHeader("author");
        String commentsEndpoint = API_CONFIG.getCommentsEndpoint();

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("post", postId);
        jsonAsMap.put("content", "Message from api");

        return API_MANAGER.post(commentsEndpoint, jsonAsMap, authHeader);
    }
}
