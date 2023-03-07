/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.sections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * This class handles the comments in a Published Post
 *
 */

public class PublishedPostPage extends BasePageObject {
    @FindBy(id = "comment")
    private WebElement commentBox;

    @FindBy(id = "submit")
    private WebElement postCommentButton;

    @Override
    public void waitUntilPageObjectIsLoaded() throws WebDriverException {
    }

    public void setCommentBox(String comment) {
        commentBox.click();
        commentBox.clear();
        commentBox.sendKeys(comment);
        postCommentButton.submit();
    }

    public boolean isCommentDisplayed(String comment) {
        String commentUser = String.format("//ol[@class='wp-block-comment-template']//li[contains(.,'%s')]", comment);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(commentUser))).isDisplayed();
    }
}
