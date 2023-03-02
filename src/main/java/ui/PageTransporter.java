package ui;

import framework.CredentialsManager;
import framework.selenium.DriverManager;
import org.openqa.selenium.WebDriver;
import ui.admin.pages.LoginAdminPage;
import ui.admin.pages.NewPostPage;
import utils.LoggerManager;

public class PageTransporter {
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    private WebDriver driver;
    private String loginAdminURL;
    private String adminURL;
    private String adminAddNewPostURL;
    private static PageTransporter instance;

    protected PageTransporter() {
        initialize();
    }

    public static PageTransporter getInstance() {
        if (instance == null) {
            instance = new PageTransporter();
        }
        return instance;
    }

    private void initialize() {
        log.info("Initializing Page Transporter");
        this.loginAdminURL = credentialsManager.getAdminLoginURL();
        this.adminURL = credentialsManager.getAdminURL();
        this.adminAddNewPostURL = credentialsManager.getAdminNewPostURL();
        this.driver = DriverManager.getInstance().getWebDriver();
    }

    private void goToURL(String url) {
        driver.navigate().to(url);
    }

    public boolean isOnLoginAdminPage() {
        return driver.getCurrentUrl().contains(loginAdminURL);
    }

    public boolean isOnAdminPage() {
        return driver.getCurrentUrl().contains(adminURL);
    }

    public boolean isOnAdminNewPostPage() {
        return driver.getCurrentUrl().contains(adminAddNewPostURL);
    }

    public LoginAdminPage navigateToAdminLoginPage() {
        if (!isOnLoginAdminPage()) {
            goToURL(loginAdminURL);
        }
        return new LoginAdminPage();
    }

    public NewPostPage navigateToNewPostPage() {
        if (!isOnAdminNewPostPage()) {
            goToURL(adminAddNewPostURL);
        }
        return new NewPostPage();
    }
}
