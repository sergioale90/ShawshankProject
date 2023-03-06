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
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;

public class ViewTagPage extends BaseAdminPage {
    public ViewTagPage() {
        PageFactory.initElements(driver, this);
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
    }

    public boolean isTagNameDisplayed(String nameTag) {
        String tagNameLocator = String.format("//main[contains(@class, 'wp-block-group')]//h1//span[text()='%s']", nameTag);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(tagNameLocator))).isDisplayed();
    }
}
