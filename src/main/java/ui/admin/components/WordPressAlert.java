package ui.admin.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

public class WordPressAlert extends BasePageObject {
    private final WebElement buttonOk;
    private final WebElement buttonCancel;
    private final WebElement message;
    private WebElement dialog;

    public WordPressAlert(WebElement dialog) {
        message = dialog.findElement(By.cssSelector("span.components-text"));
        buttonOk = dialog.findElement(By.cssSelector("button.is-primary"));
        buttonCancel = dialog.findElement(By.cssSelector("button.is-tertiary"));
        this.dialog = dialog;
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() throws WebDriverException {
        dialog = wait.until(ExpectedConditions.visibilityOf(dialog));
    }

    public boolean isAlertVisible() {return dialog.isDisplayed();}

    public String getMessage() {
        return message.getText();
    }

    public void clickOkButton() {
        buttonOk.click();
    }

    public void clickCancelButton() {
        buttonCancel.click();
    }

}
