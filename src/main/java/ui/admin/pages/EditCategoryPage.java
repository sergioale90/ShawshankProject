/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.admin.pages;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;
import utils.StringManager;

/**
 * This class contains all methods, attributes and locators to interact in to the Edit Categories Page.
 */

public class EditCategoryPage extends BaseAdminPage {
    private static final int STRING_LENGHT = 6;
    @FindBy(xpath = "//div[@class='wrap']//h1")
    private WebElement editCategoryTitle;
    @FindBy(id="edittag")
    private WebElement editCategoryForm;
    @FindBy(id="name")
    private WebElement nameTextBox;
    @FindBy(id="slug")
    private WebElement slugTextBox;
    @FindBy(id="description")
    private WebElement descriptionTexBox;
    @FindBy(xpath = "//input[@class='button button-primary']")
    private WebElement updateButton;
    @FindBy(id="message")
    private WebElement categoryUpdatedMessage;
    @FindBy(xpath = "//div[@id='message']/descendant::a")
    private WebElement goToCategoriesPageButton;
    @FindBy(xpath = "//a[@class='delete']")
    private WebElement deleteButton;
    public EditCategoryPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }
    @Override
    public void waitUntilPageObjectIsLoaded() throws WebDriverException {
        editCategoryTitle = wait.until(ExpectedConditions.visibilityOf(editCategoryTitle));
    }
    public boolean isOnEditCategoryPage() {
        editCategoryForm = wait.until(ExpectedConditions.visibilityOf(editCategoryForm));
        return editCategoryForm.isDisplayed();
    }
    public String editNameTextBox() {
        nameTextBox = wait.until(ExpectedConditions.visibilityOf(nameTextBox));
        String newName = StringManager.generateAlphanumericString(STRING_LENGHT);
        nameTextBox.clear();
        nameTextBox.sendKeys(newName);
        return newName;
    }
    public String editSlugTextBox() {
        slugTextBox = wait.until(ExpectedConditions.visibilityOf(slugTextBox));
        String newSlug = StringManager.generateAlphanumericString(STRING_LENGHT);
        slugTextBox.clear();
        slugTextBox.sendKeys(newSlug);
        return newSlug;
    }
    public String editDescriptionTextBox() {
        descriptionTexBox = wait.until(ExpectedConditions.visibilityOf(descriptionTexBox));
        String newDescription = StringManager.generateAlphanumericString(STRING_LENGHT);
        descriptionTexBox.clear();
        descriptionTexBox.sendKeys(newDescription);
        return newDescription;
    }
    public void clickOnUpdateButton() {
        updateButton = wait.until(ExpectedConditions.visibilityOf(updateButton));
        updateButton.click();
    }
    public boolean isCategoryUpdatedMessageDisplayed() {
        try {
            categoryUpdatedMessage = wait.until(ExpectedConditions.visibilityOf(categoryUpdatedMessage));
            categoryUpdatedMessage.isDisplayed();
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    public CategoriesPage clickGoToCategoriesPageButton() {
        goToCategoriesPageButton = wait.until(ExpectedConditions.visibilityOf(goToCategoriesPageButton));
        goToCategoriesPageButton.click();
        return new CategoriesPage();
    }
    public void eraseTheCategorySlug() {
        slugTextBox = wait.until(ExpectedConditions.visibilityOf(slugTextBox));
        slugTextBox.clear();
    }
    public CategoriesPage clickDeleteButton() {
        deleteButton = wait.until(ExpectedConditions.visibilityOf(deleteButton));
        deleteButton.click();
        this.acceptAlert();
        return new CategoriesPage();
    }
}
