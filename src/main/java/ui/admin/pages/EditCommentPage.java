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
import ui.admin.BaseAdminPage;
import utils.StringManager;

import java.util.HashMap;
import java.util.Map;

public class EditCommentPage  extends BaseAdminPage {

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "save")
    private WebElement updateButton;

    @FindBy(xpath = "//div[@id='delete-action']/a")
    private WebElement noveToTrash;

    private static final int STRING_LENGHT = 15;

    public EditCommentPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }
    @Override
    public void waitUntilPageObjectIsLoaded() throws WebDriverException {
    }

    public Map<String,String> fillEditComment(){
        Map<String,String> values = new HashMap<>();
        String name= StringManager.generateAlphanumericString(STRING_LENGHT);
        String email= StringManager.generateEmailString();

        values.put("name",name);
        values.put("email",email);

        nameField.clear();
        nameField.sendKeys(name);
        emailField.clear();
        emailField.sendKeys(email);
        updateButton.click();

        return values;
    }

    public void moveCommentToTrash(){
        noveToTrash.click();
    }

    public void timer() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
