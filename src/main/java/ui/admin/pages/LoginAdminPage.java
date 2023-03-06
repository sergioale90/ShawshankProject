/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.admin.pages;

import framework.CredentialsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;
import utils.LoggerManager;

public class LoginAdminPage extends BasePageObject {
    private static final LoggerManager LOG = LoggerManager.getInstance();

    @FindBy(id = "user_login")
    private WebElement userNameTextBox;

    @FindBy(id = "user_pass")
    private WebElement passwordTextBox;

    @FindBy(id = "wp-submit")
    private WebElement loginSubmitButton;

    public LoginAdminPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        userNameTextBox = wait.until(ExpectedConditions.elementToBeClickable(userNameTextBox));
        passwordTextBox = wait.until(ExpectedConditions.elementToBeClickable(passwordTextBox));
        loginSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(loginSubmitButton));
    }

    public void setUserNameTextBox(String userName) {
        userNameTextBox = wait.until(ExpectedConditions.elementToBeClickable(userNameTextBox));
        userNameTextBox.click();
        userNameTextBox.clear();
        userNameTextBox.sendKeys(userName);
    }

    public void setPasswordTextBox(String password) {
        passwordTextBox = wait.until(ExpectedConditions.elementToBeClickable(passwordTextBox));
        passwordTextBox.click();
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
    }

    public void clickLoginButton() {
        loginSubmitButton.submit();
    }

    public boolean isLoginSubmitButtonDisplayed() {
        return loginSubmitButton.isDisplayed();
    }

    public String getErrorMessage() {
        String errorMessageLocator = "login_error";
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(errorMessageLocator))).getText().strip();
    }

    public HomeAdminPage loginWithUser(String userRole) {
        setUserNameTextBox(CredentialsManager.getInstance().getUsername(userRole));
        setPasswordTextBox(CredentialsManager.getInstance().getPassword(userRole));
        clickLoginButton();
        return new HomeAdminPage();
    }

    public void loginWithInvalidCredentials(String username, String password) {
        setUserNameTextBox(username);
        setPasswordTextBox(password);
        clickLoginButton();
    }

    public void loginWithInvalidEmailAddress(String email, String password) {
        setUserNameTextBox(email);
        setPasswordTextBox(password);
        clickLoginButton();
    }
}
