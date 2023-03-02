/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.sections;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

public class PublishPanel extends BasePageObject {
    @FindBy(xpath = "//button[contains(@class, ' editor-post-publish-button ')]")
    private WebElement publishButton;

    public PublishPanel() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        publishButton = wait.until(ExpectedConditions.elementToBeClickable(publishButton));
    }

    public void clickPublishButton() {
        publishButton.click();
    }
}
