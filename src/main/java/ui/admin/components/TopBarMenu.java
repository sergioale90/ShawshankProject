package ui.admin.components;

import framework.selenium.UIMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;
import ui.admin.pages.LoginAdminPage;

public class TopBarMenu extends BasePageObject {
    @FindBy(id = "wp-admin-bar-my-account")
    WebElement myAccountButton;

    @FindBy(id = "wp-admin-bar-logout")
    WebElement logoutButton;

    public TopBarMenu() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        //myAccountButton = wait.until(ExpectedConditions.elementToBeClickable(myAccountButton));
    }

    public void clickLogoutButton() {
        logoutButton = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();
    }

    public boolean isMyAccountButtonDisplayed() {
        return myAccountButton.isDisplayed();
    }

    public LoginAdminPage logout() {
        UIMethods.moveToWebElement(myAccountButton);
        wait.until(ExpectedConditions.attributeContains(myAccountButton, "class", "hover"));
        clickLogoutButton();
        return new LoginAdminPage();
    }
}
