/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package framework;

import utils.LoggerManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CredentialsManager {
    private Properties properties;
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private static final String ENV_FILE_PATH = System.getProperty("user.dir") + File.separator + "environments.properties";
    private static CredentialsManager instance;
    private String envId;

    protected CredentialsManager() {
        initialize();
    }

    private void initialize() {
        LOG.info("Reading credentials");
        String wpEnvironmentId = System.getProperty("envId");
        if ((wpEnvironmentId == null) || (wpEnvironmentId.isEmpty())) {
            envId = "local";
        } else {
            envId = wpEnvironmentId.toLowerCase();
        }
        LOG.info("WordPress Environment Id --> " + envId);

        properties = new Properties();
        Properties envProperties = new Properties();
        try {
            envProperties.load(new FileInputStream(ENV_FILE_PATH));
        } catch (IOException e) {
            LOG.error("unable to load properties file");
        }
        properties.putAll(envProperties);
    }

    public static CredentialsManager getInstance() {
        if (instance == null || instance.properties == null) {
            instance = new CredentialsManager();
        }
        return instance;
    }

    private String getEnvironmentSetting(String setting) {
        return (String) getInstance().properties.get(setting);
    }

    public String getEnvId() {
        return envId;
    }

    public String getBaseURL() {
        return getEnvironmentSetting(getEnvId() + ".baseURL");
    }

    public String getUsername(String userRole) {
        return getEnvironmentSetting(getEnvId() + "." + userRole + ".username");
    }

    public String getPassword(String userRole) {
        return getEnvironmentSetting(getEnvId() + "." + userRole + ".password");
    }

    public String getEmail(String userRole) {
        return getEnvironmentSetting(getEnvId() + "." + userRole + ".email");
    }

    public String getAdminLoginURL() {
        return getBaseURL() + getEnvironmentSetting(getEnvId() + ".admin.loginURL");
    }

    public String getAdminURL() {
        return getBaseURL() + getEnvironmentSetting(getEnvId() + ".adminURL");
    }

    public String getAdminNewPostURL() {
        return getAdminURL() + getEnvironmentSetting(getEnvId() + ".admin.newPostURL");
    }
}
