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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;

public class PostsPage extends BaseAdminPage {
    @FindBy(xpath = "//a[@class='page-title-action'][text()='Add New']")
    private WebElement addNewPostButton;

    public PostsPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        addNewPostButton = wait.until(ExpectedConditions.elementToBeClickable(addNewPostButton));
    }

    public void clickAddNewPostButton() {
        addNewPostButton.click();
    }

    public NewPostPage goToNewPostPage() {
        clickAddNewPostButton();
        return new NewPostPage();
    }

    public NewPostPage goToPostPageUsingLink(String title) {
        String titleLocator = String.format("//a[text()='%s']", title);
        WebElement postTitleLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(titleLocator)));
        postTitleLink.click();
        return new NewPostPage();
    }

    public PostsPage movePostToTrashUsingLink(String title) {
        String titleLocator = String.format("//a[text()='%s']", title);
        WebElement postTitleLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(titleLocator)));
        UIMethods.moveToWebElement(postTitleLink);
        String trashLocator = String.format("//a[text()='%s']/ancestor::td//a[text()='Trash']", title);
        WebElement trashLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(trashLocator)));
        trashLink.click();
        return this;
    }

    public boolean isPostMovedToTrashMessageDisplayed() {
        String trashMessageLocator = "//div[@id='message']/p[contains(text(), 'post moved to the Trash')]";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(trashMessageLocator))).isDisplayed();
    }

    public boolean isPostTitleLinkNotPresent(String title) {
        String titleLocator = String.format("//a[text()='%s']", title);
        return UIMethods.isWebElementNotPresentByXpathJs(titleLocator);
    }
}
