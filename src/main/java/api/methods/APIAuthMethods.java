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
import framework.CredentialsManager;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import utils.LoggerManager;

import java.util.HashMap;
import java.util.Map;

public class APIAuthMethods {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private static final APIManager API_MANAGER = APIManager.getInstance();
    private static final CredentialsManager CREDENTIALS_MANAGER = CredentialsManager.getInstance();
    private static final APIConfig API_CONFIG = APIConfig.getInstance();

    public static Header getAuthHeader(String userRole) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", CREDENTIALS_MANAGER.getUsername(userRole));
        jsonAsMap.put("password", CREDENTIALS_MANAGER.getPassword(userRole));
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
}
