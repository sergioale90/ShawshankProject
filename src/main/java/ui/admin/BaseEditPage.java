package ui.admin;

import framework.selenium.UIMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.components.EditHeader;
import ui.sections.EditOptionsPanel;

public abstract class BaseEditPage extends BaseAdminPage {
    public String titleLocator = "h1[aria-label='Add title']";
    public String contentLocator = "textarea[placeholder='Start writing with text or HTML']";
    public String screenOverlay = "div[class='components-modal__screen-overlay']";
    public EditHeader editHeader;

    public BaseEditPage() {
        hideScreenOverlay();
        editHeader = new EditHeader();
        disableVisualEditorAndFullScreenMode();
    }

    public void setTitleTextBox(String title) {
        WebElement titleTextBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(titleLocator)));
        titleTextBox.click();
        titleTextBox.clear();
        titleTextBox.sendKeys(title);
    }

    public void setContentTextArea(String content) {
        WebElement contentTextArea = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(contentLocator)));
        contentTextArea.click();
        contentTextArea.clear();
        contentTextArea.sendKeys(content);
    }

    public String getTitleText() {
        WebElement titleTextBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(titleLocator)));
        return titleTextBox.getText();
    }

    public String getContentText() {
        WebElement contentTextArea = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(contentLocator)));
        return contentTextArea.getText();
    }

    public void disableVisualEditorAndFullScreenMode() {

        if ((!topBarMenu.isMyAccountButtonDisplayed()) && (!UIMethods.isWebElementPresentJs(contentLocator))) {
            try {
                EditOptionsPanel panel = editHeader.clickOptionsButton();
                panel.clickFullScreenModeButton();
                panel.clickCodeEditorButton();
                UIMethods.clickWebElementJs(titleLocator);
            } catch (ElementClickInterceptedException e) {
                hideScreenOverlay();
                EditOptionsPanel panel = editHeader.clickOptionsButton();
                panel.clickFullScreenModeButton();
                panel.clickCodeEditorButton();
                UIMethods.clickWebElementJs(titleLocator);
            }
        }

        if ((!topBarMenu.isMyAccountButtonDisplayed()) && (UIMethods.isWebElementPresentJs(contentLocator))) {
            try {
                EditOptionsPanel panel = editHeader.clickOptionsButton();
                panel.clickFullScreenModeButton();
                UIMethods.clickWebElementJs(titleLocator);
            } catch (ElementClickInterceptedException e) {
                hideScreenOverlay();
                EditOptionsPanel panel = editHeader.clickOptionsButton();
                panel.clickFullScreenModeButton();
                UIMethods.clickWebElementJs(titleLocator);
            }
        }

        if ((topBarMenu.isMyAccountButtonDisplayed()) && (!UIMethods.isWebElementPresentJs(contentLocator))) {
            try {
                EditOptionsPanel panel = editHeader.clickOptionsButton();
                panel.clickCodeEditorButton();
                UIMethods.clickWebElementJs(titleLocator);
            }catch (ElementClickInterceptedException e) {
                hideScreenOverlay();
                EditOptionsPanel panel = editHeader.clickOptionsButton();
                panel.clickCodeEditorButton();
                UIMethods.clickWebElementJs(titleLocator);
            }
        }
    }

    public void hideScreenOverlay() {
        if (UIMethods.isWebElementPresentJs(screenOverlay)) {
            UIMethods.hideWebElementJs(screenOverlay);
        }
    }
}
