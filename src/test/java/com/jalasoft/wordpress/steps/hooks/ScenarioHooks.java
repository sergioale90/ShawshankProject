package com.jalasoft.wordpress.steps.hooks;

import framework.selenium.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.LoggerManager;

import java.io.File;
import java.util.logging.Level;

import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY;
import static org.openqa.selenium.edge.EdgeDriverService.EDGE_DRIVER_SILENT_OUTPUT_PROPERTY;
import static org.openqa.selenium.firefox.FirefoxDriver.SystemProperty.BROWSER_LOGFILE;

public class ScenarioHooks {
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final DriverManager driverManager = DriverManager.getInstance();
    private static final String firefoxLogFilePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator + "firefox.log";
    private static boolean isAUIScenario = false;

    public void disableOtherJavaLoggers() {
        System.setProperty(CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        System.setProperty(EDGE_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        System.setProperty(BROWSER_LOGFILE, firefoxLogFilePath);
        java.util.logging.Logger.getLogger("").setLevel(Level.OFF);
    }

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        log.info("Scenario: --> " + scenario.getName());
        disableOtherJavaLoggers();
        isAUIScenario = scenario.getSourceTagNames().contains("@UI");
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        log.info("Scenario: --> " + scenario.getStatus() + " : " + scenario.getName());
        if (scenario.isFailed() && scenario.getSourceTagNames().contains("@UI")) {
            TakesScreenshot screenshotManager = (TakesScreenshot) driverManager.getWebDriver();
            byte[] screenshot = screenshotManager.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png", scenario.getId());
        }
    }

    @AfterAll
    public static void afterAll() {
        if (isAUIScenario) {
            driverManager.quitWebDriver();
        }
    }
}
