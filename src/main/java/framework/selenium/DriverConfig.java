/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package framework.selenium;

import utils.LoggerManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * This class is responsible for initializing and providing the required methods for setting up the web driver in a specific browser
 * and perform the testing in UI o headless mode, it also retrieves the information stored in the webdriver properties file
 *
 * @version 1.0
 */

public class DriverConfig {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private static final String WEB_DRIVER_FILE_PATH = System.getProperty("user.dir") + File.separator + "webdriver.properties";
    private static DriverConfig instance;
    private String browser;
    private Properties properties;

    protected DriverConfig() {
        initialize();
    }

    public static DriverConfig getInstance() {
        if (instance == null || instance.properties == null) {
            instance = new DriverConfig();
        }
        return instance;
    }

    private void initialize() {
        LOG.info("Reading WebDriver config");
        String selectedBrowser = System.getProperty("browser");
        if ((selectedBrowser == null) || (selectedBrowser.isEmpty())) {
            browser = "chrome";
        } else {
            browser = selectedBrowser.toLowerCase();
        }
        LOG.info("Selected browser is --> " + browser);

        properties = new Properties();
        Properties webDriverProperties = new Properties();
        try {
            webDriverProperties.load(new FileInputStream(WEB_DRIVER_FILE_PATH));
        } catch (IOException e) {
            LOG.error("unable to load properties file");
        }
        properties.putAll(webDriverProperties);
    }

    public String getBrowser() {
        return browser;
    }

    private String getWebDriverSetting(String setting) {
        return (String) properties.get(setting);
    }

    public Duration getImplicitWaitTime() {
        long seconds = Long.parseLong(getWebDriverSetting("webdriver.implicit.wait.time"));
        return Duration.ofMillis(seconds);
    }

    public Duration getTimeout() {
        long seconds = Long.parseLong(getWebDriverSetting("webdriver.timeout"));
        return Duration.ofMillis(seconds);
    }

    public Duration getPollingTime() {
        long seconds = Long.parseLong(getWebDriverSetting("webdriver.polling.time"));
        return Duration.ofMillis(seconds);
    }

    public boolean getHeadlessMode() {
        return Boolean.parseBoolean(getWebDriverSetting("webdriver.headless.mode"));
    }
}
