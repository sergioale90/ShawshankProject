/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package api;

import utils.LoggerManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class APIConfig {
    private Properties properties;
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private static final String API_FILE_PATH = System.getProperty("user.dir") + File.separator + "api.properties";
    private static APIConfig instance;

    protected APIConfig() {
        initialize();
    }

    private void initialize() {
        LOG.info("Reading API service config");
        properties = new Properties();
        Properties apiProperties = new Properties();
        try {
            apiProperties.load(new FileInputStream(API_FILE_PATH));
        } catch (IOException e) {
            LOG.error("unable to load properties file");
        }
        properties.putAll(apiProperties);
    }

    public static APIConfig getInstance() {
        if (instance == null || instance.properties == null) {
            instance = new APIConfig();
        }
        return instance;
    }

    private String getAPISetting(String setting) {
        return (String) properties.get(setting);
    }

    public String getBasePath() {
        return getAPISetting("api.basePath");
    }

    public int getAPIServicePort() {
        return Integer.parseInt(getAPISetting("api.service.port"));
    }

    public String getTokenEndpoint() {
        return getAPISetting("api.endpoint.token");
    }

    public String getPostsEndpoint() {
        return getAPISetting("api.endpoint.posts");
    }

    public String getPostsByIdEndpoint() {
        return getAPISetting("api.endpoint.postsById");
    }

    public String getPagesEndpoint() {
        return getAPISetting("api.endpoint.pages");
    }

    public String getPagesByIdEndpoint() {
        return getAPISetting("api.endpoint.pagesById");
    }

    public String getCategoriesEndpoint() {
        return getAPISetting("api.endpoint.categories");
    }

    public String getCategoriesEndpointById() {
        return getAPISetting("api.endpoint.categoriesById");
    }

    public String getUsersEndpoint() {
        return getAPISetting("api.endpoint.users");
    }

    public String getUsersByIdEndpoint() {
        return getAPISetting("api.endpoint.usersById");
    }

    public String getTagsEndpoint() {
        return getAPISetting("api.endpoint.tags");
    }

    public String getTagsEndpointById() {
        return getAPISetting("api.endpoint.tagsById");
    }
    public String getCommentsEndpoint() {
        return getAPISetting("api.endpoint.comments");
    }

    public String getCommentsByIdEndpoint() {
        return getAPISetting("api.endpoint.commentsById");
    }


}
