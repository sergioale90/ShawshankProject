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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIPagesMethods {
    public static final LoggerManager LOG = LoggerManager.getInstance();
    public static final APIManager API_MANAGER = APIManager.getInstance();
    private static final APIConfig API_CONFIG = APIConfig.getInstance();
    private static final int PER_PAGE = 100;

    public static Response createADraftPage() {
        String pagesEndpoint = API_CONFIG.getPagesEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("title", "Test WAPI Draft Page");
        queryParams.put("content", "Test WAPI Draft Page Hook Content");
        queryParams.put("excerpt", "Test WAPI Draft Page Hook Excerpt");

        return API_MANAGER.post(pagesEndpoint, queryParams, authHeader);
    }

    public static Response createAPagePublished() {
        String pagesEndpoint = API_CONFIG.getPagesEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("title", "Test WAPI Publish Page Hook Title");
        queryParams.put("status", "publish");
        queryParams.put("content", "Test WAPI Publish Page Hook Content");
        queryParams.put("excerpt", "Test WAPI Publish Page Hook Excerpt");

        return API_MANAGER.post(pagesEndpoint, queryParams, authHeader);
    }

    public static Response getAllPages() {
        String pagesEndpoint = API_CONFIG.getPagesEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", PER_PAGE);
        queryParams.put("status", "any");

        return API_MANAGER.get(pagesEndpoint, queryParams, authHeader);
    }

    public static Response getAllDraftPages() {
        String pagesEndpoint = API_CONFIG.getPagesEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", PER_PAGE);
        queryParams.put("status", "draft");

        return API_MANAGER.get(pagesEndpoint, queryParams, authHeader);
    }

    public static Response deleteAPageById(String id) {
        String pageByIdEndpoint = API_CONFIG.getPagesByIdEndpoint().replace("<id>", id);
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("force", true);

        return API_MANAGER.delete(pageByIdEndpoint, queryParams, authHeader);
    }

    public static Response deleteAPageByTitle(String title) {
        Response response = null;
        List<Object> objects = getAllPages().jsonPath().getList("$");

        int index = 0;
        for (Object object : objects) {
            if (object.toString().contains(title)) {
                String id = getAllPages().jsonPath().getList("id", String.class).get(index);
                response = deleteAPageById(id);
                break;
            }
            index++;
        }
        return response;
    }

    public static Response deleteADraftPageByTitle(String title) {
        Response response = null;
        List<Object> objects = getAllDraftPages().jsonPath().getList("$");
        int index = 0;
        for (Object object : objects) {
            if (object.toString().contains(title)) {
                String id = getAllDraftPages().jsonPath().getList("id", String.class).get(index);
                response = deleteAPageById(id);
                break;
            }
            index++;
        }
        return response;
    }
}
