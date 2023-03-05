package ui.admin.pages;

import api.methods.APICategoriesMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;
import utils.StringManager;

public class CategoriesPage extends BaseAdminPage {
    private String categoryName;
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
        String slug = StringManager.generateAlphanumericString(4);
        categorySlugField.clear();
        categorySlugField.sendKeys(slug);
        return slug;
    }
    public String fillCategoryDescriptionField() {
        categoryDescriptionField = wait.until(ExpectedConditions.visibilityOf(categoryDescriptionField));
        String description = StringManager.generateAlphanumericString(15);
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
    public boolean isErrorCreatedMessageDisplayed() {
        errorMessage = wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.isDisplayed();
    }
    public String errorMessageDisplayed() {
        errorMessage = wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }
}
