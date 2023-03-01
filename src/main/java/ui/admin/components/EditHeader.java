package ui.admin.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;
import ui.sections.EditOptionsPanel;
import ui.sections.PublishPanel;

public class EditHeader extends BasePageObject {
    @FindBy(xpath = "//button[contains(@class, 'editor-post-publish')]")
    WebElement publishButton;

    @FindBy(xpath = "//button[contains(@class, 'components-button editor-post-save-draft is-tertiary')]")
    WebElement saveDraftButton;

    @FindBy(css = "button[class='components-button components-dropdown-menu__toggle has-icon']")
    WebElement optionsButton;

//    @FindBy(css = "button.editor-post-save-draft")
//    WebElement saveDraftButton;

    @FindBy(css = "button.editor-post-publish-button")
    WebElement updateButton;

    @FindBy(css = "button.editor-post-switch-to-draft")
    WebElement switchToDraftButton;

    public EditHeader() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        publishButton = wait.until(ExpectedConditions.visibilityOf(publishButton));
        optionsButton = wait.until(ExpectedConditions.elementToBeClickable(optionsButton));
    }

    public PublishPanel clickPublishButton() {
        publishButton.click();
        return new PublishPanel();
    }

    public void clickSaveDraftButton() {
        saveDraftButton.click();
    }

    public void clickUpdateButton() {
        updateButton.click();
    }

    public WordPressAlert switchDraftButton() {
        switchToDraftButton.click();
        WebElement alertDialog = driver.findElement(By.cssSelector("div.components-confirm-dialog"));
        return new WordPressAlert(alertDialog);
    }

//    public EditHeader clickSaveDraftButton() {
//        saveDraftButton.click();
//        return new EditHeader();
//    }

    public EditOptionsPanel clickOptionsButton() {
        optionsButton.click();
        return new EditOptionsPanel();
    }
}
