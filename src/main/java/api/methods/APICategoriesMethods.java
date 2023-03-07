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
 * Categories feature with a provided user role and Id
 *
 * @version 1.0
 */

public class APICategoriesMethods {
    public static final LoggerManager LOG = LoggerManager.getInstance();
    public static final APIManager API_MANAGER = APIManager.getInstance();
    private static final APIConfig API_CONFIG = APIConfig.getInstance();
    private static final int STRING_LENGHT = 4;
    private static final int PER_PAGE = 100;

    public static Response createACategory() {
        String categoriesEndpoint = API_CONFIG.getCategoriesEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", StringManager.generateAlphanumericString(STRING_LENGHT));
        queryParams.put("slug", StringManager.generateAlphanumericString(STRING_LENGHT));
        queryParams.put("description", StringManager.generateAlphanumericString(STRING_LENGHT));

        return API_MANAGER.post(categoriesEndpoint, queryParams, authHeader);
    }

    public static Response deleteACategory(String id) {
        String categoriesEndpointById = API_CONFIG.getCategoriesEndpointById().replace("<id>", id);
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("force", true);

        return API_MANAGER.delete(categoriesEndpointById, queryParams, authHeader);
    }
    public static Response getAllCategoriesCreated() {
        String categoriesEndpoint = API_CONFIG.getCategoriesEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", PER_PAGE);

        return API_MANAGER.get(categoriesEndpoint, queryParams, authHeader);
    }
    public static String getTheIdByName(String name) {
        List<Object> objects = getAllCategoriesCreated().jsonPath().getList("$");
        int index = 0;
        String categoryId = null;
        for (Object object : objects) {
            if (object.toString().contains(name)) {
                categoryId = getAllCategoriesCreated().jsonPath().getList("id", String.class).get(index);
                break;
            }
            index++;
        }
        return categoryId;
    }
}
