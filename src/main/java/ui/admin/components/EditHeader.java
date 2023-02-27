package ui.admin.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;
import ui.sections.EditOptionsPanel;
import ui.sections.PublishPanel;

public class EditHeader extends BasePageObject {
    @FindBy(xpath = "//button[contains(@class, 'editor-post-publish-panel')]")
    WebElement publishButton;

    @FindBy(css = "button[class='components-button components-dropdown-menu__toggle has-icon']")
    WebElement optionsButton;

    @FindBy(css = "button.editor-post-saved-state")
    WebElement saveDraftButton;


    public EditHeader() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        publishButton = wait.until(ExpectedConditions.elementToBeClickable(publishButton));
        optionsButton = wait.until(ExpectedConditions.elementToBeClickable(optionsButton));
        saveDraftButton = wait.until(ExpectedConditions.elementToBeClickable(saveDraftButton));
    }

    public PublishPanel clickPublishButton() {
        publishButton.click();
        return new PublishPanel();
    }

    public PublishPanel clickSaveDraftButton() {
        saveDraftButton.click();
        return new PublishPanel();
    }
    public EditOptionsPanel clickOptionsButton() {
        optionsButton.click();
        return new EditOptionsPanel();
    }

}
