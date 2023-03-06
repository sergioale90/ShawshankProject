/**
 * Copyright (c) 2023 Jala University.
 *
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

public class EditTagPage extends BaseAdminPage {
    private String nameLocator = "input#name";
    private String slugLocator = "input#slug";
    private String descriptionLocator = "textarea#description";

    @FindBy(css = "div.edit-tag-actions input")
    private WebElement updateEditTagButton;

    @FindBy(css = "span#delete-link a")
    private WebElement deleteLink;


    public EditTagPage() {
        PageFactory.initElements(driver, this);
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
    }

    public void setNameTextBox(String name) {
        WebElement nameTextBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(nameLocator)));
        nameTextBox.click();
        nameTextBox.clear();
        nameTextBox.sendKeys(name);
    }

    public void setSlugTextBox(String slug) {
        WebElement slugTextBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(slugLocator)));
        slugTextBox.click();
        slugTextBox.clear();
        slugTextBox.sendKeys(slug);
    }

    public void setDescriptionTextArea(String description) {
        WebElement descriptionTextArea = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(descriptionLocator)));
        descriptionTextArea.click();
        descriptionTextArea.clear();
        descriptionTextArea.sendKeys(description);
    }

    public String getNameText() {
        WebElement nameTextBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(nameLocator)));
        return nameTextBox.getText();
    }

    public String getSlugText() {
        WebElement slugTextBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(slugLocator)));
        return slugTextBox.getText();
    }

    public String getDescriptionText() {
        WebElement descriptionTextArea = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(descriptionLocator)));
        return descriptionTextArea.getText();
    }

    public void addNewTag(String name, String slug, String description) {
        setNameTextBox(name);
        setSlugTextBox(slug);
        setDescriptionTextArea(description);
        updateEditTagButton.submit();
    }

    public boolean isTagUpdatedMessageDisplayed() {
        String tagMessageLocator = "//div[contains(@id, 'message')]//strong[contains(text(), 'Tag updated.')]";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(tagMessageLocator))).isDisplayed();
    }

    public void updateTag(String name, String slug, String description) {
        setNameTextBox(name);
        setDescriptionTextArea(description);
        setSlugTextBox(slug);
        updateEditTagButton.submit();
    }

    public void deleteTagLink() {
        deleteLink.click();
    }
}
