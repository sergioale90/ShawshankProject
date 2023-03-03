/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui;

import framework.CredentialsManager;
import framework.selenium.DriverManager;
import org.openqa.selenium.WebDriver;
import ui.admin.pages.LoginAdminPage;
import ui.admin.pages.NewPostPage;
import utils.LoggerManager;

public class PageTransporter {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private static final CredentialsManager CREDENTIALS_MANAGER = CredentialsManager.getInstance();

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
        LOG.info("Initializing Page Transporter");
        this.loginAdminURL = CREDENTIALS_MANAGER.getAdminLoginURL();
        this.adminURL = CREDENTIALS_MANAGER.getAdminURL();
        this.adminAddNewPostURL = CREDENTIALS_MANAGER.getAdminNewPostURL();
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
