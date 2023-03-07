/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package framework.selenium;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import utils.LoggerManager;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static constants.DomainAppConstants.CHROME;
import static constants.DomainAppConstants.EDGE;
import static constants.DomainAppConstants.FIREFOX;

/**
 * This class is responsible for initializing a specific browser with its configurations and returning the driver
 * initialization for consumption it also retrieves the information stored in the webdriver properties file for the waiters
 *
 * @version 1.0
 */

public class DriverManager {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    public static final DriverConfig DRIVER_CONFIG = DriverConfig.getInstance();
    private static DriverManager instance;
    private WebDriver driver;
    private Wait<WebDriver> wait;

    protected DriverManager() {
        initialize();
    }

    public static DriverManager getInstance() {
        if (instance == null || instance.driver == null) {
            instance = new DriverManager();
        }
        return instance;
    }

    private void initialize() {
        LOG.info("Initializing Selenium WebDriver Manager");
        switch (DRIVER_CONFIG.getBrowser()) {
            case CHROME -> {
                DriverService.Builder<ChromeDriverService, ChromeDriverService.Builder> builder = new ChromeDriverService.Builder()
                        .withSilent(true);
                ChromeDriverService service = builder.build();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                chromeOptions.addArguments("--password-store=basic");
                chromeOptions.setAcceptInsecureCerts(true);

                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", prefs);

                if (DRIVER_CONFIG.getHeadlessMode()) {
                    chromeOptions.addArguments("--headless");
                }
                driver = new ChromeDriver(service, chromeOptions);
            }
            case EDGE -> {
                DriverService.Builder<EdgeDriverService, EdgeDriverService.Builder> builder = new EdgeDriverService.Builder()
                        .withSilent(true);
                EdgeDriverService service = builder.build();

                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                edgeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                edgeOptions.addArguments("--password-store=basic");
                edgeOptions.setAcceptInsecureCerts(true);
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                edgeOptions.setExperimentalOption("prefs", prefs);

                if (DRIVER_CONFIG.getHeadlessMode()) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(service, edgeOptions);
            }
            case FIREFOX -> {
                String firefoxLogFilePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator
                        + "firefox.log";
                DriverService.Builder<GeckoDriverService, GeckoDriverService.Builder> builder = new GeckoDriverService.Builder()
                        .withLogFile(new File(firefoxLogFilePath));
                GeckoDriverService service = builder.build();

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                firefoxOptions.setLogLevel(FirefoxDriverLogLevel.FATAL);
                firefoxOptions.setAcceptInsecureCerts(true);
                if (DRIVER_CONFIG.getHeadlessMode()) {
                    firefoxOptions.addArguments("--headless");
                }

                driver = new FirefoxDriver(service, firefoxOptions);
            }
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(DRIVER_CONFIG.getImplicitWaitTime());
        wait = new FluentWait<>(driver)
                .withTimeout(DRIVER_CONFIG.getTimeout())
                .pollingEvery(DRIVER_CONFIG.getPollingTime())
                .ignoring(NoSuchElementException.class)
                .ignoring(NotFoundException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public Wait<WebDriver> getFluentWait() {
        return wait;
    }

    public void quitWebDriver() {
        try {
            LOG.info("Closing WebDriver");
            driver.quit();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        driver = null;
    }
}
