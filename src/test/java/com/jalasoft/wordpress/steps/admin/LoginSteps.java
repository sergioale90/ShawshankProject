package com.jalasoft.wordpress.steps.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import ui.PageTransporter;
import ui.admin.pages.HomeAdminPage;
import ui.admin.pages.LoginAdminPage;
import ui.methods.CommonMethods;

public class LoginSteps {

    private final PageTransporter pageTransporter;
    private LoginAdminPage loginAdminPage;
    private HomeAdminPage homeAdminPage;

    public LoginSteps() {
        this.pageTransporter = PageTransporter.getInstance();
    }

    @Given("^I navigate to Admin Login page$")
    public void navigateToAdminLoginPage() {
        loginAdminPage = pageTransporter.navigateToAdminLoginPage();
    }

    @Given("^I login to Admin page as a user with \"(.*?)\" role$")
    public void loginToAdmin(String userRole) {
        homeAdminPage = loginAdminPage.loginWithUser(userRole);
    }

    @Given("^the user is logged in to Admin page with \"(.*?)\" role$")
    public void switchAdminPageUser(String userRole) {
        if (pageTransporter.isOnAdminPage()) {
            CommonMethods.logout();
            loginAdminPage = new LoginAdminPage();
            homeAdminPage = loginAdminPage.loginWithUser(userRole);
        } else if (pageTransporter.isOnLoginAdminPage()) {
            loginAdminPage = new LoginAdminPage();
            homeAdminPage = loginAdminPage.loginWithUser(userRole);
        } else {
            loginAdminPage = pageTransporter.navigateToAdminLoginPage();
            homeAdminPage = loginAdminPage.loginWithUser(userRole);
        }
    }

    @Then("^I should login to Admin page successfully$")
    public void verifyLoginToAdminPage() {
        boolean isMyAccountButtonDisplayed = homeAdminPage.topBarMenu.isMyAccountButtonDisplayed();

        Assert.assertTrue(isMyAccountButtonDisplayed, "My account was not displayed");
    }
}
