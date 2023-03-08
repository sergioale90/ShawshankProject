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
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for providing the required methods for the classes that consume the API requests for the
 * Users feature with a provided user role and Id
 *
 * @version 1.0
 */

public class APIUsersMethods {

    public static final LoggerManager LOG = LoggerManager.getInstance();

    public static final APIManager API_MANAGER = APIManager.getInstance();

    private static final APIConfig API_CONFIG = APIConfig.getInstance();

    private static final int PER_PAGE = 100;

    public static Response createAUser() {
        String usersEndpoint = API_CONFIG.getUsersEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", "James");
        jsonAsMap.put("password", "James123");
        jsonAsMap.put("email", "james@gsl.com");
        jsonAsMap.put("roles", "administrator");

        return API_MANAGER.post(usersEndpoint, jsonAsMap, authHeader);
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

    public static Response deleteAUsersById(String id) {
        String usersByIdEndpoint = API_CONFIG.getUsersByIdEndpoint().replace("<id>", id);
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("reassign", 1);
        jsonAsMap.put("force", true);

        return API_MANAGER.delete(usersByIdEndpoint, jsonAsMap, authHeader);
    }

    public static Response getAllUsers() {
        String usersEndpoint = API_CONFIG.getUsersEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("per_page", PER_PAGE);

        return API_MANAGER.get(usersEndpoint, jsonAsMap, authHeader);
    }

}
