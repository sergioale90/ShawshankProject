/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.sections;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

public class EditOptionsPanel extends BasePageObject {
    @FindBy(xpath = "//span[text()='Fullscreen mode']/ancestor::button")
    private WebElement fullscreenModeButton;

    @FindBy(xpath = "//span[text()='Code editor']/ancestor::button")
    private WebElement codeEditorButton;

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
