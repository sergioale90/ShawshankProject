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
import org.openqa.selenium.support.ui.Select;
import ui.admin.BaseAdminPage;

public class NewUsersPage extends BaseAdminPage {

    @FindBy(id = "user_login")
    private WebElement usernameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "first_name")
    private WebElement nameField;

    @FindBy(id = "last_name")
    private WebElement lastNameField;

    @FindBy(id = "pass1")
    private WebElement passwordField;

    @FindBy(id = "createusersub")
    private WebElement newUserButton;

    @FindBy(xpath = "//tr[@class='pw-weak']/descendant::input")
    private WebElement weakCheck;

    public NewUsersPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {

    }

    public void createUser(String username, String mail, String name, String lastName, String password) {
        usernameField.sendKeys(username);
        emailField.sendKeys(mail);
        nameField.sendKeys(name);
        lastNameField.sendKeys(lastName);
        passwordField.clear();
        passwordField.sendKeys(password);
        weakCheck.click();
        newUserButton.click();
    }

    public void createUser(String username, String mail, String name, String lastName, String password, String role) {
        usernameField.sendKeys(username);
        emailField.sendKeys(mail);
        nameField.sendKeys(name);
        lastNameField.sendKeys(lastName);

        WebElement selectElement = driver.findElement(By.id("role"));
        Select select = new Select(selectElement);
        select.selectByValue(role);
        newUserButton.click();
    }

}
