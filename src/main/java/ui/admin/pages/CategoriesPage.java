package ui.admin.pages;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;

public class CategoriesPage extends BaseAdminPage {
    @FindBy(xpath = "//h1[@class='wp-heading-inline' and text()='Categories']")
    WebElement categoriesPageTitle;

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
}
