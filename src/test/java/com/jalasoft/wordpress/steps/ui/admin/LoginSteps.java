package com.jalasoft.wordpress.steps.ui.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import ui.PageTransporter;
import ui.admin.pages.HomeAdminPage;
import ui.admin.pages.LoginAdminPage;
import ui.methods.CommonMethods;
import utils.StringManager;

public class LoginSteps {

    private final PageTransporter pageTransporter;
    private LoginAdminPage loginAdminPage;
    private HomeAdminPage homeAdminPage;
    private String username;

    public LoginSteps() {
        this.pageTransporter = PageTransporter.getInstance();
    }

    @Given("^the user goes to Admin Login page$")
    public void navigateToAdminLoginPage() {
        loginAdminPage = pageTransporter.navigateToAdminLoginPage();
    }

    @Given("^the user login to Admin page as a user with \"(.*?)\" role$")
    public void loginToAdmin(String userRole) {
        homeAdminPage = loginAdminPage.loginWithUser(userRole);
    }

    @Given("^the user login to Admin page using invalid credentials$")
    public void loginToAdminWithInvalidCredentials() {
        username = StringManager.generateAlphanumericString(8);
        String password = StringManager.generateAlphanumericString(8);
        loginAdminPage.loginWithInvalidCredentials(username, password);
    }

    @Given("^the user login to Admin page using invalid email$")
    public void loginToAdminWithInvalidEmail() {
        String email = StringManager.generateAlphanumericString(8) + "@" + StringManager.generateAlphanumericString(4) + ".com";
        String password = StringManager.generateAlphanumericString(8);
        loginAdminPage.loginWithInvalidEmailAddress(email, password);
    }

    @Given("^the user login to Admin page with no credentials$")
    public void loginToAdminPageWithNoCredentials() {
        String username = "";
        String password = "";
        loginAdminPage.loginWithInvalidCredentials(username, password);
    }

    @Given("^the user login to Admin page with no username$")
    public void loginToAdminPageWithNoUsername() {
        String username = "";
        String password = StringManager.generateAlphanumericString(8);
        loginAdminPage.loginWithInvalidCredentials(username, password);
    }

    @Given("^the user login to Admin page with no password$")
    public void loginToAdminPageWithNoPassword() {
        String username = StringManager.generateAlphanumericString(8);
        String password = "";
        loginAdminPage.loginWithInvalidCredentials(username, password);
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

    @Then("^the user should login to Admin page successfully$")
    public void verifyLoginToAdminPage() {
        boolean isMyAccountButtonDisplayed = homeAdminPage.topBarMenu.isMyAccountButtonDisplayed();

        Assert.assertTrue(isMyAccountButtonDisplayed, "My account button was not displayed");
    }

    @Then("^the user should not be able to login to Admin page$")
    public void verifyUnsuccessfulLogin() {
        boolean isOnLoginAdminPage = pageTransporter.isOnLoginAdminPage();
        boolean isLoginSubmitButtonDisplayed = loginAdminPage.isLoginSubmitButtonDisplayed();

        Assert.assertTrue(isOnLoginAdminPage, "user with invalid credentials is not on the Admin Login page");
        Assert.assertTrue(isLoginSubmitButtonDisplayed, "login submit button was not displayed");
    }

    @Then("^an error message that indicates that the username is not registered should be displayed$")
    public void verifyUsernameNotRegisteredErrorMessage() {
        String expectedErrorMessage = String.format("Error: The username %s is not registered on this site. If you are unsure of your username, try your email address instead.", username);
        String actualErrorMessage = loginAdminPage.getErrorMessage();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "displayed error message was not correct");
    }

    @Then("^an error message that indicates that the email address is unknown should be displayed$")
    public void verifyUnknownEmailErrorMessage() {
        String expectedErrorMessage = "Unknown email address. Check again or try your username.";
        String actualErrorMessage = loginAdminPage.getErrorMessage();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "displayed error message was not correct");
    }

    @Then("^error messages that indicates that the username and password fields are empty should be displayed$")
    public void verifyEmptyFieldsErrorMessages() {
        String expectedErrorMessage = "Error: The username field is empty.\nError: The password field is empty.";
        String actualErrorMessage = loginAdminPage.getErrorMessage();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "displayed error message was not correct");
    }

    @Then("^an error message that indicates that the username field is empty should be displayed$")
    public void verifyUsernameEmptyFieldErrorMessage() {
        String expectedErrorMessage = "Error: The username field is empty.";
        String actualErrorMessage = loginAdminPage.getErrorMessage();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "displayed error message was not correct");
    }

    @Then("^an error message that indicates that the password field is empty should be displayed$")
    public void verifyPasswordEmptyFieldErrorMessage() {
        String expectedErrorMessage = "Error: The password field is empty.";
        String actualErrorMessage = loginAdminPage.getErrorMessage();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "displayed error message was not correct");
    }
}
