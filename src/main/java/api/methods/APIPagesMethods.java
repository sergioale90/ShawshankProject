package api.methods;

import api.APIConfig;
import api.APIManager;
import io.restassured.http.Header;
import io.restassured.response.Response;
import utils.LoggerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static api.methods.APIPostsMethods.deleteAPostById;

public class APIPagesMethods {
    public static final LoggerManager log = LoggerManager.getInstance();
    public static final APIManager apiManager = APIManager.getInstance();
    private static final APIConfig apiConfig = APIConfig.getInstance();

    public static Response createAPage() {
        String pagesEndpoint = apiConfig.getPagesEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("title", "Test WAPI Draft Page");
        queryParams.put("content", "Test WAPI Draft Page Hook Content");
        queryParams.put("excerpt", "Test WAPI Draft Page Hook Excerpt");

        return apiManager.post(pagesEndpoint, queryParams, authHeader);
    }

    public static Response createAPagePublished() {
        String pagesEndpoint = apiConfig.getPagesEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("title", "Test WAPI Publish Page Hook Title");
        queryParams.put("status", "publish");
        queryParams.put("content", "Test WAPI Publish Page Hook Content");
        queryParams.put("excerpt", "Test WAPI Publish Page Hook Excerpt");

        return apiManager.post(pagesEndpoint, queryParams, authHeader);
    }

    public static Response getAllPages() {
        String pagesEndpoint = apiConfig.getPagesEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", 100);

        return apiManager.get(pagesEndpoint, queryParams, authHeader);
    }

    public static Response getAllDraftPages() {
        String pagesEndpoint = apiConfig.getPagesEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("per_page", 100);
        queryParams.put("status", "draft");

        return apiManager.get(pagesEndpoint, queryParams, authHeader);
    }

    public static Response deleteAPageById(String id) {
        String pageByIdEndpoint = apiConfig.getPagesByIdEndpoint().replace("<id>", id);
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("force", true);

        return apiManager.delete(pageByIdEndpoint, queryParams, authHeader);
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
