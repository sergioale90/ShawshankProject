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
    public static final APIManager apiManager = APIManager.getInstance();
    private static final APIConfig apiConfig = APIConfig.getInstance();

    public static Response getAllTags() {
        String tagsEndpoint = apiConfig.getTagsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", 100);

        return apiManager.get(tagsEndpoint, queryParams, authHeader);
    }

    public static Response CreateATag() {
        String tagsEndpoint = apiConfig.getTagsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", StringManager.generateAlphanumericString(7));
        queryParams.put("slug", StringManager.generateAlphanumericString(5).toLowerCase());
        queryParams.put("description", StringManager.generateAlphanumericString(25));

        return apiManager.post(tagsEndpoint, queryParams, authHeader);
    }

    public static Response deleteATagById(String id) {
        String tagByIdEndpoint = apiConfig.getTagsEndpointById().replace("<id>", id);
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("force", true);

        return apiManager.delete(tagByIdEndpoint, queryParams, authHeader);
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
