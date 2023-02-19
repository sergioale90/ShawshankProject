package ui.admin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;
import utils.LoggerManager;

public class HomeAdminPage extends BaseAdminPage {
    private static final LoggerManager log = LoggerManager.getInstance();

    @FindBy(id = "contextual-help-link")
    WebElement helpButton;

    public HomeAdminPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        helpButton = wait.until(ExpectedConditions.visibilityOf(helpButton));
    }
}
