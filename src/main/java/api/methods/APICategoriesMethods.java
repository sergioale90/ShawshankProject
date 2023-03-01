package api.methods;

import api.APIConfig;
import api.APIManager;
import io.restassured.http.Header;
import io.restassured.response.Response;
import utils.LoggerManager;
import utils.StringManager;

import java.util.HashMap;
import java.util.Map;

public class APICategoriesMethods {
    public static final LoggerManager log = LoggerManager.getInstance();
    public static final APIManager apiManager = APIManager.getInstance();
    private static final APIConfig apiConfig = APIConfig.getInstance();
    public static Response CreateACategory() {
        String categoriesEndpoint = apiConfig.getCategoriesEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("name", StringManager.generateAlphanumericString(6));
        queryParams.put("slug", StringManager.generateAlphanumericString(5));
        queryParams.put("description", StringManager.generateAlphanumericString(20));

        return apiManager.post(categoriesEndpoint, queryParams, authHeader);
    }

    public static Response deleteACategory(String id) {
        String categoriesEndpointById = apiConfig.getCategoriesEndpointById().replace("<id>", id);
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("force", true);

        return apiManager.delete(categoriesEndpointById, queryParams, authHeader);
    }
}
