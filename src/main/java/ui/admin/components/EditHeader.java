/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.admin.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.BasePageObject;
import ui.sections.EditOptionsPanel;
import ui.sections.PublishPanel;

public class EditHeader extends BasePageObject {

    @FindBy(xpath = "//button[contains(@class, 'editor-post-publish')]")
    private WebElement publishButton;

    @FindBy(css = "button.editor-post-save-draft")
    private WebElement saveDraftButton;

    @FindBy(css = "button.editor-post-publish-button")
    private WebElement updateButton;

    @FindBy(css = "button.editor-post-switch-to-draft")
    private WebElement switchToDraftButton;

    @FindBy(css = "button[class='components-button components-dropdown-menu__toggle has-icon']")
    private WebElement optionsButton;

    public EditHeader() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
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

    public EditOptionsPanel clickOptionsButton() {
        optionsButton.click();
        return new EditOptionsPanel();
    }
}
