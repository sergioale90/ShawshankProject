/**
 * Copyright (c) 2023 Jala University.
 * <p>
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

/**
 * This class provides actions for a WordPress page
 */
public class PagesPage extends BaseAdminPage {
    @FindBy(xpath = "//a[@class='page-title-action'][text()='Add New']")
    private WebElement addNewPageButton;

    public PagesPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        addNewPageButton = wait.until(ExpectedConditions.elementToBeClickable(addNewPageButton));
    }

    public void clickAddNewPageButton() {
        addNewPageButton.click();
    }

    public NewPagesPage goToNewPostPage() {
        clickAddNewPageButton();
        return new NewPagesPage();
    }

    public NewPagesPage goToPagePageUsingLink(String title) {
        String titleLocator = String.format("//a[text()='%s']", title);
        WebElement pageTitleLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(titleLocator)));
        pageTitleLink.click();
        return new NewPagesPage();
    }

    public PagesPage movePageToTrashUsingLink(String title) {
        String titleLocator = String.format("//a[text()='%s']", title);
        WebElement pagesTitleLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(titleLocator)));
        UIMethods.moveToWebElement(pagesTitleLink);
        String trashLocator = String.format("//a[text()='%s']/ancestor::td//a[text()='Trash']", title);
        WebElement trashLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(trashLocator)));
        trashLink.click();
        return this;
    }

    public PagesPage deletePagePermanentlyUsingLink(String title) {
        String titleLocator = String.format("//td[contains(@class, 'column-title')]//span[contains(.,'%s')]", title);
        WebElement pagesTitleLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(titleLocator)));
        UIMethods.moveToWebElement(pagesTitleLink);

        String deleteLocator = String.format("//span[contains(@class, 'delete')]//a[text()='Delete Permanently']", title);
        WebElement deleteLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(deleteLocator)));
        deleteLink.click();
        return this;
    }

    public PagesPage restorePageUsingLink(String title) {
        String titleLocator = String.format("//td[contains(@class, 'column-title')]//span[contains(.,'%s')]", title);
        WebElement pagesTitleLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(titleLocator)));
        UIMethods.moveToWebElement(pagesTitleLink);

        String restoreLocator = String.format("//span[contains(@class, 'untrash')]//a[text()='Restore']", title);
        WebElement restoreLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(restoreLocator)));
        restoreLink.click();
        return this;
    }

    public boolean isPageMovedToTrashMessageDisplayed() {
        String trashMessageLocator = "//div[@id='message']/p[contains(text(), 'page moved to the Trash.')]";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(trashMessageLocator))).isDisplayed();
    }

    public boolean isPageTitleLinkNotPresent(String title) {
        String titleLocator = String.format("//a[text()='%s']", title);
        return UIMethods.isWebElementNotPresentByXpathJs(titleLocator);
    }

    public boolean isPagePermanentlyDeleteMessageDisplayed() {
        String deleteMessageLocator = "//div[@id='message']/p[contains(text(), 'page permanently deleted.')]";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(deleteMessageLocator))).isDisplayed();
    }

    public boolean isPageRestoredMessageDisplayed() {
        String restoreMessageLocator = "//div[@id='message']/p[contains(text(), '1 page restored from the Trash.')]";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(restoreMessageLocator))).isDisplayed();
    }

    public boolean isPageTitleLinkNotPresentInTrashSection(String title) {
        String titleLocator = String.format("//td[contains(@class, 'column-title')]//span[contains(.,'%s')]", title);
        return UIMethods.isWebElementNotPresentByXpathJs(titleLocator);
    }

    public boolean isInvalidPageTitleLinkNotPresent(String title) {
        String titleLocator = String.format("//tr[contains(@class, 'no-items')]//td", title);
        return UIMethods.isWebElementNotPresentByXpathJs(titleLocator);
    }
}
