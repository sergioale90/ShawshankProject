package ui.sections;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

public class PublishPanel extends BasePageObject {
    @FindBy(xpath = "//button[contains(@class, 'editor-post-publish')]")
    WebElement publishButton;

    public PublishPanel() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        publishButton = wait.until(ExpectedConditions.visibilityOf(publishButton));
    }

    public void clickPublishButton() {
        publishButton.click();
    }
}
