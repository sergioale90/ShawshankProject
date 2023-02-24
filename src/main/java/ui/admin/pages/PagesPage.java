package ui.admin.pages;

import framework.selenium.UIMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;

public class PagesPage extends BaseAdminPage {
    @FindBy(xpath = "//a[@class='page-title-action'][text()='Add New']")
    WebElement addNewPageButton;

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

    public boolean isPageMovedToTrashMessageDisplayed() {
        String trashMessageLocator = "//div[@id='message']/p[contains(text(), 'page moved to the Trash')]";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(trashMessageLocator))).isDisplayed();
    }

    public boolean isPageTitleLinkPresent(String title) {
        String titleLocator = String.format("//a[text()='%s']", title);
        return UIMethods.isWebElementPresentByXpathJs(titleLocator);
    }
}
