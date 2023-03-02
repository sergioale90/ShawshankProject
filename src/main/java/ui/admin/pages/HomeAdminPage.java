/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.admin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;
import utils.LoggerManager;

public class HomeAdminPage extends BaseAdminPage {
    private static final LoggerManager LOG = LoggerManager.getInstance();

    @FindBy(id = "contextual-help-link")
    private WebElement helpButton;

    public HomeAdminPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        helpButton = wait.until(ExpectedConditions.visibilityOf(helpButton));
    }
}
