package ui.admin.pages;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;
import utils.StringManager;

public class EditCategoryPage extends BaseAdminPage {
    @FindBy(xpath = "//div[@class='wrap']//h1")
    WebElement editCategoryTitle;
    @FindBy(xpath = "//form[@id='edittag']")
    WebElement editCategoryForm;
    @FindBy(xpath = "//input[@id='name']")
    WebElement nameTextBox;
    @FindBy(xpath = "//input[@id='slug']")
    WebElement slugTextBox;
    @FindBy(xpath = "//textarea[@id='description']")
    WebElement descriptionTexBox;
    @FindBy(xpath = "//input[@class='button button-primary']")
    WebElement updateButton;
    @FindBy(xpath = "//div[@id='message']")
    WebElement categoryUpdatedMessage;
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
        String newName = StringManager.generateAlphanumericString(6);
        nameTextBox.clear();
        nameTextBox.sendKeys(newName);
        return newName;
    }
    public String editSlugTextBox() {
        slugTextBox = wait.until(ExpectedConditions.visibilityOf(slugTextBox));
        String newSlug = StringManager.generateAlphanumericString(6);
        slugTextBox.clear();
        slugTextBox.sendKeys(newSlug);
        return newSlug;
    }
    public String editDescriptionTextBox() {
        descriptionTexBox = wait.until(ExpectedConditions.visibilityOf(descriptionTexBox));
        String newDescription = StringManager.generateAlphanumericString(6);
        descriptionTexBox.clear();
        descriptionTexBox.sendKeys(newDescription);
        return newDescription;
    }
    public void clickOnUpdateButton() {
        updateButton = wait.until(ExpectedConditions.visibilityOf(updateButton));
        updateButton.click();
    }
    public boolean isCategoryUpdatedMessageDisplayed() {
        categoryUpdatedMessage = wait.until(ExpectedConditions.visibilityOf(categoryUpdatedMessage));
        return categoryUpdatedMessage.isDisplayed();
    }
}
