package framework;

import utils.LoggerManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CredentialsManager {
    private Properties properties;
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final String envFilePath = System.getProperty("user.dir") + File.separator + "environments.properties";
    private static CredentialsManager instance;
    private String envId;

    protected CredentialsManager() {
        initialize();
    }

    private void initialize() {
        log.info("Reading credentials");
        String wpEnvironmentId = System.getProperty("envId");
        if ((wpEnvironmentId == null) || (wpEnvironmentId.isEmpty())) {
            envId = "local";
        } else {
            envId = wpEnvironmentId.toLowerCase();
        }
        log.info("WordPress Environment Id --> " + envId);

        properties = new Properties();
        Properties envProperties = new Properties();
        try {
            envProperties.load(new FileInputStream(envFilePath));
        } catch (IOException e) {
            log.error("unable to load properties file");
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
