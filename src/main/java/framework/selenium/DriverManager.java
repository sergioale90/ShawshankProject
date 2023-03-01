package framework.selenium;

import org.openqa.selenium.*;
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

import static constants.DomainAppConstants.*;

public class DriverManager {
    private static final LoggerManager log = LoggerManager.getInstance();
    public static final DriverConfig driverConfig = DriverConfig.getInstance();
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
        log.info("Initializing Selenium WebDriver Manager");
        switch (driverConfig.getBrowser()) {
            case CHROME -> {
                DriverService.Builder<ChromeDriverService, ChromeDriverService.Builder> builder = new ChromeDriverService.Builder().withSilent(true);
                ChromeDriverService service = builder.build();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

                chromeOptions.addArguments("--password-store=basic");
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", prefs);

                if (driverConfig.getHeadlessMode()) {
                    chromeOptions.addArguments("--headless");
                }
                driver = new ChromeDriver(service, chromeOptions);
            }
            case EDGE -> {
                DriverService.Builder<EdgeDriverService, EdgeDriverService.Builder> builder = new EdgeDriverService.Builder().withSilent(true);
                EdgeDriverService service = builder.build();

                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                edgeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

                edgeOptions.addArguments("--password-store=basic");
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                edgeOptions.setExperimentalOption("prefs", prefs);

                if (driverConfig.getHeadlessMode()) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(service, edgeOptions);
            }
            case FIREFOX -> {
                String firefoxLogFilePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator + "firefox.log";
                DriverService.Builder<GeckoDriverService, GeckoDriverService.Builder> builder = new GeckoDriverService.Builder().withLogFile(new File(firefoxLogFilePath));
                GeckoDriverService service = builder.build();

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                firefoxOptions.setLogLevel(FirefoxDriverLogLevel.FATAL);
                if (driverConfig.getHeadlessMode()) {
                    firefoxOptions.addArguments("--headless");
                }

                driver = new FirefoxDriver(service, firefoxOptions);
            }
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(driverConfig.getImplicitWaitTime());
        wait = new FluentWait<>(driver)
                .withTimeout(driverConfig.getTimeout())
                .pollingEvery(driverConfig.getPollingTime())
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
            log.info("Closing WebDriver");
            driver.quit();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        driver = null;
    }
}
