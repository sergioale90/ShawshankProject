/**
 * Copyright (c) 2023 Jala University.
 * <p>
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.admin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;

/**
 * This class provides actions for a WordPress New Tag Page
 */

public class NewTagPage extends BaseAdminPage {
    private String nameLocator = "input#tag-name";
    private String slugLocator = "input#tag-slug";
    private String descriptionLocator = "textarea#tag-description";

    @FindBy(css = "input#submit")
    private WebElement submitButton;

    @FindBy(css = "div.edit-tag-actions input")
    private WebElement updateEditTagButton;

    @FindBy(css = "span#delete-link a")
    private WebElement deleteLink;

    private WebElement nameTextBox;
    private WebElement slugTextBox;
    private WebElement descriptionTextArea;

    public NewTagPage() {
        PageFactory.initElements(driver, this);
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
    }

    public void setNameTextBox(String name) {
        nameTextBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(nameLocator)));
        nameTextBox.click();
        nameTextBox.clear();
        nameTextBox.sendKeys(name);
    }

    public void setSlugTextBox(String slug) {
        slugTextBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(slugLocator)));
        slugTextBox.click();
        slugTextBox.clear();
        slugTextBox.sendKeys(slug);
    }

    public void setDescriptionTextArea(String description) {
        descriptionTextArea = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(descriptionLocator)));
        descriptionTextArea.click();
        descriptionTextArea.clear();
        descriptionTextArea.sendKeys(description);
    }

    public void addNewTag(String name, String slug, String description) {
        setNameTextBox(name);
        setSlugTextBox(slug);
        setDescriptionTextArea(description);
        submitButton.submit();
    }

    public boolean isTagAddedMessageDisplayed() {
        String tagMessageLocator = "//div[contains(@id, 'message')]//p[contains(text(), 'Tag added.')]";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(tagMessageLocator))).isDisplayed();
    }

    public boolean isErrorMessageDisplayedForTagCreatedWithTheSameName(String message) {
        String messageLocator = String.format("//div[@id='message']//p[contains(.,'%s')]", message);
        WebElement messageElement = driver.findElement(By.xpath(messageLocator));
        return messageElement.isDisplayed();
    }

    public void updateTag(String name, String slug, String description) {
        setNameTextBox(name);
        setDescriptionTextArea(description);
        setSlugTextBox(slug);
        submitButton.submit();
    }
}
