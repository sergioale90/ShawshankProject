/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.admin.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditUsersPage extends BaseAdminPage {

    @FindBy(id = "first_name")
    private WebElement firstNameField;

    @FindBy(id = "last_name")
    private WebElement lastNameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "submit")
    private WebElement submitUpdate;

    private String msgUpdated = "//strong[text()='User updated.']";

    @Override
    public void waitUntilPageObjectIsLoaded() {

    }

    public void fieldsToEdit(String firstName, String lastName, String email) {
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        emailField.clear();
        emailField.sendKeys(email);
        submitUpdate.click();
    }

    public boolean getMsgUpdated() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(msgUpdated))).isDisplayed();
    }

}
