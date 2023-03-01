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
    private static final LoggerManager log = LoggerManager.getInstance();

    @FindBy(id ="user_login")
    WebElement userNameTextBox;

    @FindBy(id = "user_pass")
    WebElement passwordTextBox;

    @FindBy(id = "wp-submit")
    WebElement loginSubmitButton;

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
        userNameTextBox.click();
        userNameTextBox.clear();
        userNameTextBox.sendKeys(userName);
    }

    public void setPasswordTextBox(String password) {
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
