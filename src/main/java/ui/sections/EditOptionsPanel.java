package ui.sections;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

public class EditOptionsPanel extends BasePageObject {
    @FindBy(xpath = "//span[text()='Fullscreen mode']/ancestor::button")
    WebElement fullscreenModeButton;

    @FindBy(xpath = "//span[text()='Code editor']/ancestor::button")
    WebElement codeEditorButton;

    public EditOptionsPanel() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        fullscreenModeButton = wait.until(ExpectedConditions.elementToBeClickable(fullscreenModeButton));
        codeEditorButton = wait.until(ExpectedConditions.elementToBeClickable(codeEditorButton));
    }

    public void clickFullScreenModeButton() {
        fullscreenModeButton.click();
    }

    public void clickCodeEditorButton() {
        codeEditorButton.click();
    }
}
