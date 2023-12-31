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
import ui.admin.BaseEditPage;
import ui.sections.PublishPanel;

public class NewPostPage extends BaseEditPage {
    public NewPostPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
    }

    public void publishPost(String title, String content) {
        setTitleTextBox(title);
        setContentTextArea(content);

        PublishPanel panel = getEditHeader().clickPublishButton();
        panel.clickPublishButton();
    }

    public void draftPost(String title, String content) {
        setTitleTextBox(title);
        setContentTextArea(content);

        getEditHeader().clickSaveDraftButton();
    }

    public void createPost(String title, String content) {
        setTitleTextBox(title);
        setContentTextArea(content);
    }

    public boolean isPublishedMessageDisplayed() {
        String publishedMessageLocator = "//div[contains(@class, 'components-snackbar')][text()='Post published.']";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(publishedMessageLocator))).isDisplayed();
    }

    public boolean isSavedAsDraftMessageDisplayed() {
        String publishedMessageLocator = "//div[contains(@class, 'components-snackbar')][text()='Draft saved.']";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(publishedMessageLocator))).isDisplayed();
    }

    public boolean isUpdatedMessageDisplayed() {
        String publishedMessageLocator = "//div[contains(@class, 'components-snackbar')][text()='Post updated.']";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(publishedMessageLocator))).isDisplayed();
    }
}
