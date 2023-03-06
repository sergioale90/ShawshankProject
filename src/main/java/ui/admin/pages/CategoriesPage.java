/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.admin.pages;

import api.methods.APICategoriesMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;
import utils.StringManager;

public class CategoriesPage extends BaseAdminPage {
    private String categoryName;
    private Actions hoverRowCategory;
    private final int stringLenght = 7;
    @FindBy(xpath = "//h1[@class='wp-heading-inline' and text()='Categories']")
    private WebElement categoriesPageTitle;
    @FindBy(xpath = "//input[@id='tag-name']")
    private WebElement categoryNameField;
    @FindBy(xpath = "//input[@id='tag-slug']")
    private WebElement categorySlugField;
    @FindBy(xpath = "//textarea[@id='tag-description']")
    private WebElement categoryDescriptionField;
    @FindBy(xpath = "//input[@id='submit']")
    private WebElement addNewCategoryButton;
    @FindBy(xpath = "//div[@class='notice notice-success is-dismissible']")
    private WebElement successCreatedCategoryMessage;
    @FindBy(xpath = "//div[@class='notice notice-error']")
    private WebElement errorMessage;
    @FindBy(xpath = "//button[@class='save button button-primary']")
    private WebElement updateButton;
    @FindBy(xpath = "//input[@id='tag-search-input']")
    private WebElement searchBox;
    @FindBy(xpath = "//input[@id='search-submit']")
    private WebElement searchButton;
    @FindBy(xpath = "//select[@id='bulk-action-selector-top']")
    private WebElement bulkActionMenu;
    @FindBy(xpath = "//select[@id='bulk-action-selector-top']/descendant::option[@value='delete']")
    private WebElement deleteOptionBulkMenu;
    @FindBy(xpath = "//input[@id='doaction']")
    private WebElement applyButton;
    @FindBy(xpath = "//div[@class='updated notice is-dismissible']")
    private WebElement deleteMessage;
    public CategoriesPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }
    @Override
    public void waitUntilPageObjectIsLoaded() throws WebDriverException {
        categoriesPageTitle = wait.until(ExpectedConditions.visibilityOf(categoriesPageTitle));
    }
    public boolean isOnTheCategoryPage() {
        return categoriesPageTitle.isDisplayed();
    }
    public String fillCategoryNameField() {
        categoryNameField = wait.until(ExpectedConditions.visibilityOf(categoryNameField));
        categoryName = StringManager.generateAlphanumericString(stringLenght);
        categoryNameField.clear();
        categoryNameField.sendKeys(categoryName);
        return categoryName;
    }
    public void blankCategoryNameField(String blankName) {
        categoryNameField = wait.until(ExpectedConditions.visibilityOf(categoryNameField));
        categoryNameField.clear();
        categoryNameField.sendKeys(blankName);
    }
    public String fillCategorySlugField() {
        categorySlugField = wait.until(ExpectedConditions.visibilityOf(categorySlugField));
        String slug = StringManager.generateAlphanumericString(stringLenght);
        categorySlugField.clear();
        categorySlugField.sendKeys(slug);
        return slug;
    }
    public String fillCategoryDescriptionField() {
        categoryDescriptionField = wait.until(ExpectedConditions.visibilityOf(categoryDescriptionField));
        String description = StringManager.generateAlphanumericString(stringLenght);
        categoryDescriptionField.clear();
        categoryDescriptionField.sendKeys(description);
        return description;
    }
    public void clickAddNewCategoryButton() {
        addNewCategoryButton = wait.until(ExpectedConditions.elementToBeClickable(addNewCategoryButton));
        addNewCategoryButton.click();
    }
    public boolean isSuccessCreatedMessageDisplayed() {
        successCreatedCategoryMessage = wait.until(ExpectedConditions.visibilityOf(successCreatedCategoryMessage));
        return successCreatedCategoryMessage.isDisplayed();
    }
    public String createdMessageDisplayed() {
        successCreatedCategoryMessage = wait.until(ExpectedConditions.visibilityOf(successCreatedCategoryMessage));
        return successCreatedCategoryMessage.getText();
    }
    public String categoryNameCreated() {
        driver.navigate().refresh();
        String categoryId = APICategoriesMethods.getTheIdByName(categoryName);
        String categoryNameInListString = String
                .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::a[@class='row-title']", categoryId);
        WebElement categoryNameInListLocator = driver.findElement(By.xpath(categoryNameInListString));
        categoryNameInListLocator = wait.until(ExpectedConditions.visibilityOf(categoryNameInListLocator));
        return categoryNameInListLocator.getText();
    }
    public String categorySlugCreated() {
        String categoryId = APICategoriesMethods.getTheIdByName(categoryName);
        String categorySlugInListString = String
                .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::td[@class='slug column-slug']", categoryId);
        WebElement categorySlugInListLocator = driver.findElement(By.xpath(categorySlugInListString));
        categorySlugInListLocator = wait.until(ExpectedConditions.visibilityOf(categorySlugInListLocator));
        return categorySlugInListLocator.getText();
    }
    public String categoryDescriptionCreated() {
        String categoryId = APICategoriesMethods.getTheIdByName(categoryName);
        String categoryDescriptionInListString = String
                .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::td[@class='description column-description']", categoryId);
        WebElement categoryDescriptionInListLocator = driver.findElement(By.xpath(categoryDescriptionInListString));
        categoryDescriptionInListLocator = wait.until(ExpectedConditions.visibilityOf(categoryDescriptionInListLocator));
        return categoryDescriptionInListLocator.getText();
    }
    public String categoryNameUpdated(String categoryId) {
        driver.navigate().refresh();
        String categoryNameInListString = String
                .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::a[@class='row-title']", categoryId);
        WebElement categoryNameInListLocator = driver.findElement(By.xpath(categoryNameInListString));
        categoryNameInListLocator = wait.until(ExpectedConditions.visibilityOf(categoryNameInListLocator));
        return categoryNameInListLocator.getText();
    }
    public String categorySlugUpdated(String categoryId) {
        String categorySlugInListString = String
                .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::td[@class='slug column-slug']", categoryId);
        WebElement categorySlugInListLocator = driver.findElement(By.xpath(categorySlugInListString));
        categorySlugInListLocator = wait.until(ExpectedConditions.visibilityOf(categorySlugInListLocator));
        return categorySlugInListLocator.getText();
    }
    public String categoryDescriptionUpdated(String categoryId) {
        String categoryDescriptionInListString = String
                .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::td[@class='description column-description']", categoryId);
        WebElement categoryDescriptionInListLocator = driver.findElement(By.xpath(categoryDescriptionInListString));
        categoryDescriptionInListLocator = wait.until(ExpectedConditions.visibilityOf(categoryDescriptionInListLocator));
        return categoryDescriptionInListLocator.getText();
    }
    public boolean isErrorCreatedMessageDisplayed() {
        errorMessage = wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.isDisplayed();
    }
    public String errorMessageDisplayed() {
        errorMessage = wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }
    public void hoverOneCategoryCreated(String categoryId) {
        String categoryRowByIdString = String
                .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']", categoryId);
        WebElement categoryRowByIdLocator = driver.findElement(By.xpath(categoryRowByIdString));
        categoryRowByIdLocator = wait.until(ExpectedConditions.visibilityOf(categoryRowByIdLocator));
        hoverRowCategory = new Actions(driver);
        hoverRowCategory.moveToElement(categoryRowByIdLocator).perform();
    }
    public EditCategoryPage clickOnEditLabel(String id) {
        String editButtonInHiddenMenuString = String
                .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::span[@class='edit']", id);
        WebElement editButtonInHiddenMenuLocator = driver.findElement(By.xpath(editButtonInHiddenMenuString));
        editButtonInHiddenMenuLocator = wait.until(ExpectedConditions.visibilityOf(editButtonInHiddenMenuLocator));
        hoverRowCategory.moveToElement(editButtonInHiddenMenuLocator).perform();
        editButtonInHiddenMenuLocator.click();
        return new EditCategoryPage();
    }
    public void clickQuickEditButton(String id) {
        String quickEditButtonString = String
                .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::span[@class='inline hide-if-no-js']", id);
        WebElement quickEditButtonLocator = driver.findElement(By.xpath(quickEditButtonString));
        quickEditButtonLocator = wait.until(ExpectedConditions.visibilityOf(quickEditButtonLocator));
        hoverRowCategory.moveToElement(quickEditButtonLocator).perform();
        quickEditButtonLocator.click();
    }
    public boolean quickEditFormIsVisible(String id) {
        String quickEditFormString = String.format("//tr[@id='edit-%s']", id);
        WebElement quickEditFormLocator = driver.findElement(By.xpath(quickEditFormString));
        quickEditFormLocator = wait.until(ExpectedConditions.visibilityOf(quickEditFormLocator));
        return quickEditFormLocator.isDisplayed();
    }
    public String quickEditCategoryName(String id) {
        String newCategoryName = StringManager.generateAlphanumericString(stringLenght);
        String nameTextBoxString = String.format("//tr[@id='edit-%s']/descendant::input[@name='name']", id);
        WebElement nameTextBoxLocator = driver.findElement(By.xpath(nameTextBoxString));
        nameTextBoxLocator = wait.until(ExpectedConditions.visibilityOf(nameTextBoxLocator));
        nameTextBoxLocator.clear();
        nameTextBoxLocator.sendKeys(newCategoryName);
        return newCategoryName;
    }
    public String quickEditCategorySlug(String id) {
        String newCategorySlug = StringManager.generateAlphanumericString(stringLenght);
        String slugTextBoxString = String.format("//tr[@id='edit-%s']/descendant::input[@name='slug']", id);
        WebElement slugTextBoxLocator = driver.findElement(By.xpath(slugTextBoxString));
        slugTextBoxLocator.clear();
        slugTextBoxLocator.sendKeys(newCategorySlug);
        return newCategorySlug;
    }
    public void clickUpdateCategoryButton() {
        updateButton = wait.until(ExpectedConditions.visibilityOf(updateButton));
        updateButton.click();
        driver.navigate().refresh();
    }
    public void fillSearchBox(String argument) {
        searchBox = wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(argument);
    }
    public void clickSearchButton() {
        searchButton = wait.until(ExpectedConditions.visibilityOf(searchButton));
        searchButton.click();
    }
    public boolean isCategoryPresentOnTheList(String argument) {
        try {
            String resultsOfSearchString = String
                    .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::a[contains(text(),'%s')]", argument);
            WebElement resultsOfSearchLocator = driver.findElement(By.xpath(resultsOfSearchString));
            resultsOfSearchLocator.isDisplayed();
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    public void quickDeleteButton(String categoryId) {
        String deleteButtonString = String
                .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::span[@class='delete']", categoryId);
        WebElement deleteButtonLocator = driver.findElement(By.xpath(deleteButtonString));
        deleteButtonLocator = wait.until(ExpectedConditions.visibilityOf(deleteButtonLocator));
        deleteButtonLocator.click();
        this.acceptAlert();
        driver.navigate().refresh();
    }
    public void selectCategoryCheckBox(String id) {
        String categoryCheckBoxString = String
                .format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::input[@type='checkbox']", id);
        WebElement categoryCheckBoxLocator = driver.findElement(By.xpath(categoryCheckBoxString));
        categoryCheckBoxLocator = wait.until(ExpectedConditions.elementToBeClickable(categoryCheckBoxLocator));
        categoryCheckBoxLocator.click();
    }
    public void selectActionBulkMenu() {
        bulkActionMenu = wait.until(ExpectedConditions.elementToBeClickable(bulkActionMenu));
        bulkActionMenu.click();
    }
    public void selectDeleteOptionBulkMenu() {
        deleteOptionBulkMenu = wait.until(ExpectedConditions.elementToBeClickable(deleteOptionBulkMenu));
        deleteOptionBulkMenu.click();
    }
    public void clickApplyButton() {
        applyButton = wait.until(ExpectedConditions.elementToBeClickable(applyButton));
        applyButton.click();
    }
    public boolean deleteMessageIsDisplayed() {
        deleteMessage = wait.until(ExpectedConditions.visibilityOf(deleteMessage));
        return deleteMessage.isDisplayed();
    }
}
