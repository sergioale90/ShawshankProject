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
import utils.StringManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APITagsMethods {
    public static final APIManager API_MANAGER = APIManager.getInstance();
    private static final APIConfig API_CONFIG = APIConfig.getInstance();
    private static final int PER_PAGE = 100;
    private static final int NAME_STRING_LENGHT = 7;
    private static final int SLUG_STRING_LENGHT = 5;
    private static final int DESCRIPTION_STRING_LENGHT = 15;

    public static Response getAllTags() {
        String tagsEndpoint = API_CONFIG.getTagsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", PER_PAGE);

        return API_MANAGER.get(tagsEndpoint, queryParams, authHeader);
    }
    public static Response createATag() {
        String tagsEndpoint = API_CONFIG.getTagsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", StringManager.generateAlphanumericString(NAME_STRING_LENGHT));
        queryParams.put("slug", StringManager.generateAlphanumericString(SLUG_STRING_LENGHT).toLowerCase());
        queryParams.put("description", StringManager.generateAlphanumericString(DESCRIPTION_STRING_LENGHT));

        return API_MANAGER.post(tagsEndpoint, queryParams, authHeader);
    }

    public static Response deleteATagById(String id) {
        String tagByIdEndpoint = API_CONFIG.getTagsEndpointById().replace("<id>", id);
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("force", true);

        return API_MANAGER.delete(tagByIdEndpoint, queryParams, authHeader);
    }

    public static Response deleteATagByTitle(String title) {
        Response response = null;
        List<Object> objects = getAllTags().jsonPath().getList("$");

        int index = 0;
        for (Object object : objects) {
            if (object.toString().contains(title)) {
                String id = getAllTags().jsonPath().getList("id", String.class).get(index);
                response = deleteATagById(id);
                break;
            }
            index++;
        }
        return response;
    }
}
