package api.methods;

import api.APIConfig;
import api.APIManager;
import io.restassured.http.Header;
import io.restassured.response.Response;
import utils.LoggerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIPostsMethods {
    public static final LoggerManager log = LoggerManager.getInstance();
    public static final APIManager apiManager = APIManager.getInstance();
    private static final APIConfig apiConfig = APIConfig.getInstance();

    public static Response createAPost() {
        String postsEndpoint = apiConfig.getPostsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("content", "Test WAPI Post Content");
        jsonAsMap.put("title", "Test WAPI Title");
        jsonAsMap.put("excerpt", "Test WAPI Excerpt");
        jsonAsMap.put("status", "publish");

        return apiManager.post(postsEndpoint, jsonAsMap, authHeader);
    }

    public static Response createADraftPost() {
        String postsEndpoint = apiConfig.getPostsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("content", "Test WAPI Post Content");
        jsonAsMap.put("title", "Test WAPI Title");
        jsonAsMap.put("excerpt", "Test WAPI Excerpt");
        jsonAsMap.put("status", "draft");

        return apiManager.post(postsEndpoint, jsonAsMap, authHeader);
    }

    public static Response deleteAPostById(String id) {
        String postByIdEndpoint = apiConfig.getPostsByIdEndpoint().replace("<id>", id);
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        return apiManager.delete(postByIdEndpoint, jsonAsMap, authHeader);
    }

    public static Response getAllPosts() {
        String postsEndpoint = apiConfig.getPostsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("per_page", 100);

        return apiManager.get(postsEndpoint, jsonAsMap, authHeader);
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
