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
    @FindBy(xpath = "//h1[@class='wp-heading-inline' and text()='Categories']")
    WebElement categoriesPageTitle;
    @FindBy(xpath = "//input[@id='tag-name']")
    WebElement categoryNameField;
    @FindBy(xpath = "//input[@id='tag-slug']")
    WebElement categorySlugField;
    @FindBy(xpath = "//textarea[@id='tag-description']")
    WebElement categoryDescriptionField;
    @FindBy(xpath = "//input[@id='submit']")
    WebElement addNewCategoryButton;
    @FindBy(xpath = "//div[@class='notice notice-success is-dismissible']")
    WebElement successCreatedCategoryMessage;
    @FindBy(xpath = "//div[@class='notice notice-error']")
    WebElement errorMessage;
    @FindBy(xpath = "//button[@class='save button button-primary']")
    WebElement updateButton;

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
        categoryName = StringManager.generateAlphanumericString(7);
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
        String slug = StringManager.generateAlphanumericString(7);
        categorySlugField.clear();
        categorySlugField.sendKeys(slug);
        return slug;
    }
    public String fillCategoryDescriptionField() {
        categoryDescriptionField = wait.until(ExpectedConditions.visibilityOf(categoryDescriptionField));
        String description = StringManager.generateAlphanumericString(7);
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
        String categoryNameInListString = String.format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::a[@class='row-title']", categoryId);
        WebElement categoryNameInListLocator = driver.findElement(By.xpath(categoryNameInListString));
        categoryNameInListLocator = wait.until(ExpectedConditions.visibilityOf(categoryNameInListLocator));
        return categoryNameInListLocator.getText();
    }
    public String categorySlugCreated() {
        String categoryId = APICategoriesMethods.getTheIdByName(categoryName);
        String categorySlugInListString = String.format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::td[@class='slug column-slug']", categoryId);
        WebElement categorySlugInListLocator = driver.findElement(By.xpath(categorySlugInListString));
        categorySlugInListLocator = wait.until(ExpectedConditions.visibilityOf(categorySlugInListLocator));
        return categorySlugInListLocator.getText();
    }
    public String categoryDescriptionCreated() {
        String categoryId = APICategoriesMethods.getTheIdByName(categoryName);
        String categoryDescriptionInListString = String.format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::td[@class='description column-description']", categoryId);
        WebElement categoryDescriptionInListLocator = driver.findElement(By.xpath(categoryDescriptionInListString));
        categoryDescriptionInListLocator = wait.until(ExpectedConditions.visibilityOf(categoryDescriptionInListLocator));
        return categoryDescriptionInListLocator.getText();
    }
    public String categoryNameUpdated(String categoryId) {
        driver.navigate().refresh();
        String categoryNameInListString = String.format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::a[@class='row-title']", categoryId);
        WebElement categoryNameInListLocator = driver.findElement(By.xpath(categoryNameInListString));
        categoryNameInListLocator = wait.until(ExpectedConditions.visibilityOf(categoryNameInListLocator));
        return categoryNameInListLocator.getText();
    }
    public String categorySlugUpdated(String categoryId) {
        String categorySlugInListString = String.format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::td[@class='slug column-slug']", categoryId);
        WebElement categorySlugInListLocator = driver.findElement(By.xpath(categorySlugInListString));
        categorySlugInListLocator = wait.until(ExpectedConditions.visibilityOf(categorySlugInListLocator));
        return categorySlugInListLocator.getText();
    }
    public String categoryDescriptionUpdated(String categoryId) {
        String categoryDescriptionInListString = String.format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::td[@class='description column-description']", categoryId);
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
        String categoryRowByIdString = String.format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']", categoryId);
        WebElement categoryRowByIdLocator = driver.findElement(By.xpath(categoryRowByIdString));
        categoryRowByIdLocator = wait.until(ExpectedConditions.visibilityOf(categoryRowByIdLocator));
        hoverRowCategory = new Actions(driver);
        hoverRowCategory.moveToElement(categoryRowByIdLocator).perform();
    }
    public EditCategoryPage clickOnEditLabel(String id) {
        String editButtonInHiddenMenuString = String.format("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::span[@class='edit']", id);
        WebElement editButtonInHiddenMenuLocator = driver.findElement(By.xpath(editButtonInHiddenMenuString));
        editButtonInHiddenMenuLocator = wait.until(ExpectedConditions.visibilityOf(editButtonInHiddenMenuLocator));
        hoverRowCategory.moveToElement(editButtonInHiddenMenuLocator).perform();
        editButtonInHiddenMenuLocator.click();
        return new EditCategoryPage();
    }
    public void clickQuickEditButton(String id) {
        String quickEditButtonString = String.format ("//table[@class='wp-list-table widefat fixed striped table-view-list tags']/descendant::tr[@id='tag-%s']/descendant::span[@class='inline hide-if-no-js']", id);
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
        String newCategoryName = StringManager.generateAlphanumericString(7);
        String nameTextBoxString = String.format("//tr[@id='edit-%s']/descendant::input[@name='name']", id);
        WebElement nameTextBoxLocator = driver.findElement(By.xpath(nameTextBoxString));
        nameTextBoxLocator = wait.until(ExpectedConditions.visibilityOf(nameTextBoxLocator));
        nameTextBoxLocator.clear();
        nameTextBoxLocator.sendKeys(newCategoryName);
        return newCategoryName;
    }
    public String quickEditCategoryDescription(String id) {
        String newCategoryDescription = StringManager.generateAlphanumericString(7);
        String descriptionTextBoxString = String.format("//tr[@id='edit-%s']/descendant::input[@name='slug']", id);
        WebElement descriptionTextBoxLocator = driver.findElement(By.xpath(descriptionTextBoxString));
        descriptionTextBoxLocator.clear();
        descriptionTextBoxLocator.sendKeys(newCategoryDescription);
        return newCategoryDescription;
    }
    public void clickUpdateCategoryButton() {
        updateButton = wait.until(ExpectedConditions.visibilityOf(updateButton));
        updateButton.click();
    }
}
