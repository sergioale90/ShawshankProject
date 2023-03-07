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
import io.restassured.http.Header;
import io.restassured.response.Response;
import utils.LoggerManager;
import utils.StringManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for providing the required methods for the classes that consume the API requests for the
 * Posts feature with a provided user role and Id
 *
 * @version 1.0
 */

public class APIPostsMethods {
    public static final LoggerManager LOG = LoggerManager.getInstance();
    public static final APIManager API_MANAGER = APIManager.getInstance();
    private static final APIConfig API_CONFIG = APIConfig.getInstance();
    private static final int PER_PAGE = 100;
    private static final int STRING_LENGHT = 4;

    public static Response createAPost() {
        String postsEndpoint = API_CONFIG.getPostsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        String string = StringManager.generateAlphanumericString(STRING_LENGHT);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("content", "Content " + string);
        jsonAsMap.put("title", "Title " + string);
        jsonAsMap.put("excerpt", "Excerpt " + string);
        jsonAsMap.put("status", "publish");

        return API_MANAGER.post(postsEndpoint, jsonAsMap, authHeader);
    }

    public static Response createADraftPost() {
        String postsEndpoint = API_CONFIG.getPostsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        String string = StringManager.generateAlphanumericString(STRING_LENGHT);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("content", "Draft Content " + string);
        jsonAsMap.put("title", "Draft Title " + string);
        jsonAsMap.put("excerpt", "Draft Excerpt " + string);
        jsonAsMap.put("status", "draft");

        return API_MANAGER.post(postsEndpoint, jsonAsMap, authHeader);
    }

    public static Response deleteAPostById(String id) {
        String postByIdEndpoint = API_CONFIG.getPostsByIdEndpoint().replace("<id>", id);
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        return API_MANAGER.delete(postByIdEndpoint, jsonAsMap, authHeader);
    }

    public static Response getAllPosts() {
        String postsEndpoint = API_CONFIG.getPostsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("per_page", PER_PAGE);
        jsonAsMap.put("status", "any");

        return API_MANAGER.get(postsEndpoint, jsonAsMap, authHeader);
    }

    public static Response deleteAPostByTitle(String title) {
        Response response = null;
        List<Object> objects = getAllPosts().jsonPath().getList("$");
        int index = 0;
        for (Object object : objects) {
            if (object.toString().contains(title)) {
                String id = getAllPosts().jsonPath().getList("id", String.class).get(index);
                response = deleteAPostById(id);
                break;
            }
            index++;
        }
        return response;
    }
}
