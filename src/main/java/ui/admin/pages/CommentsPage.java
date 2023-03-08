/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.admin.pages;

import framework.selenium.UIMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;
import utils.StringManager;

/**
 * This class is responsible for identifying each web element that contains
 * the comment page using methods and locators.
 *
 * @version 1.0
 */
public class CommentsPage extends BasePageObject {
    @FindBy(css = "li.moderated a")
    WebElement pendingLink;

    @FindBy(css = "li.approved a")
    WebElement approveLink;

    @FindBy(css = "li.spam a")
    WebElement spamLink;

    @FindBy(css = "li.trash a")
    WebElement trashLink;

    @FindBy(id = "replycontent")
    WebElement textReply;

    @FindBy(id = "content")
    WebElement contentEdit;

    @FindBy(id = "save")
    WebElement saveEdit;

    @FindBy(xpath = "//button[@class='save button button-primary']")
    WebElement submitButton;

    @FindBy(id = "comment-search-input")
    private WebElement searchComment;

    @FindBy(id = "search-submit")
    private WebElement searchCommentButton;

    private String contentComment = "//p[text()='value']";
    private String authorRow = "//strong[text()=' author']";
    private String emailRow = "//a[text()='email']";

    private static final int REPLY_LENGHT = 15;

    public boolean isCommentOnTable(String comment) {
        String commentUser = String.format(
                "//tbody[@id='the-comment-list']//td[contains(@class, comment)]//p[contains(., '%s')]", comment);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(commentUser))).isDisplayed();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() throws WebDriverException {
    }

    public void moveCommentUserToTrash(String commentId) {
        String commentRowLocator = "//tr[@id='comment-%s']";
        String commentRow = String.format(commentRowLocator, commentId);
        WebElement elementRow = driver.findElement(By.xpath(commentRow));
        UIMethods.moveToWebElement(elementRow);

        WebElement trashLink = elementRow.findElement(By.cssSelector("span.trash a"));
        trashLink.click();
    }

    public void moveCommentUserToApprove(String commentId) {
        String commentRowLocator = "//tr[@id='comment-%s']";
        String commentRow = String.format(commentRowLocator, commentId);
        WebElement elementRow = driver.findElement(By.xpath(commentRow));
        UIMethods.moveToWebElement(elementRow);

        WebElement approveLink = elementRow.findElement(By.cssSelector("span.approve a"));
        approveLink.click();
    }

    public void moveCommentUserToSpam(String commentId) {
        String commentRowLocator = "//tr[@id='comment-%s']";
        String commentRow = String.format(commentRowLocator, commentId);
        WebElement elementRow = driver.findElement(By.xpath(commentRow));
        UIMethods.moveToWebElement(elementRow);

        WebElement spamLink = elementRow.findElement(By.cssSelector("span.spam a"));
        spamLink.click();
    }

    public String moveCommentUserToReply(String commentId) {
        String commentRowLocator = "//tr[@id='comment-%s']";
        String commentRow = String.format(commentRowLocator, commentId);
        String actualComment = StringManager.generateAlphanumericString(REPLY_LENGHT);
        WebElement elementRow = driver.findElement(By.xpath(commentRow));
        UIMethods.moveToWebElement(elementRow);

        WebElement replyLink = elementRow.findElement(By.cssSelector("span.reply"));
        replyLink.click();
        textReply.sendKeys(actualComment);
        submitButton.click();

        return actualComment;
    }

    public String moveCommentUserToQuickEdit(String commentId) {
        String commentRowLocator = "//tr[@id='comment-%s']";
        String commentRow = String.format(commentRowLocator, commentId);
        String actualComment = StringManager.generateAlphanumericString(REPLY_LENGHT);
        WebElement elementRow = driver.findElement(By.xpath(commentRow));
        UIMethods.moveToWebElement(elementRow);

        WebElement quickEditLink = elementRow.findElement(By.cssSelector("span.quickedit"));
        quickEditLink.click();
        textReply.clear();
        textReply.sendKeys(actualComment);
        submitButton.click();

        return actualComment;
    }

    public void moveCommentUserToEdit(String commentId) {
        String commentRowLocator = "//tr[@id='comment-%s']";
        String commentRow = String.format(commentRowLocator, commentId);
        WebElement elementRow = driver.findElement(By.xpath(commentRow));
        UIMethods.moveToWebElement(elementRow);

        WebElement quickEditLink = elementRow.findElement(By.xpath("//span[@class='edit']/a"));
        quickEditLink.click();
    }

    public String editCommentInPage() {
        String actualComment = StringManager.generateAlphanumericString(REPLY_LENGHT);
        contentEdit.clear();
        contentEdit.sendKeys(actualComment);
        saveEdit.click();
        return actualComment;
    }

    public void searchComment(String comment) {
        searchComment.sendKeys(comment);
        searchCommentButton.click();
    }

    public boolean isReplyDisplayed(String comment) {
        String commentUser = contentComment.replace("value", comment);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(commentUser))).isDisplayed();
    }

    public boolean verifyEmailAuthor(String name, String email) {
        WebElement author = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(authorRow.replace("author", name))));
        WebElement emailX = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(emailRow.replace("email", email))));

        return (author.isDisplayed() && emailX.isDisplayed());
    }

    public String goToTrash() {
        trashLink.click();
        return driver.getCurrentUrl();
    }

    public String goToApprove() {
        approveLink.click();
        return driver.getCurrentUrl();
    }

    public String goToSpam() {
        spamLink.click();
        return driver.getCurrentUrl();
    }

}
