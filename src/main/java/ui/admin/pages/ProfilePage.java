package ui.admin.pages;

import framework.selenium.UIMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;

public class ProfilePage extends BaseAdminPage {

    private String editHover = "//a[text()='value']/ancestor::td/descendant::div/span[@class='edit']/a";
    private String auxColumn;

    @FindBy(id = "first_name")
    private WebElement firstNameField;

    @FindBy(id = "last_name")
    private WebElement lastNameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "submit")
    private WebElement submitUpdate;


    private String msgUpdated = "//strong[text()='Profile updated.']";

    public ProfilePage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
    }

    public void FieldsToEdit(String firstName, String lastName, String email) {
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        emailField.clear();
        emailField.sendKeys(email);
        submitUpdate.click();
    }

    public boolean getMsgUpdated(){
        return wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath(msgUpdated))).isDisplayed();
    }

}
