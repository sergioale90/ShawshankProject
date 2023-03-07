package ui.admin.pages;

import framework.selenium.UIMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

public class CommentsPage extends BasePageObject {
    @FindBy(css = "li.moderated a")
    WebElement pendingLink;

    @FindBy(css = "li.approved a")
    WebElement approveLink;

    @FindBy(css = "li.spam a")
    WebElement spamLink;

    @FindBy(css = "li.trash a")
    WebElement trashLink;

    public boolean isCommentOnTable(String comment) {
        String commentUser = String.format("//tbody[@id='the-comment-list']//td[contains(@class, comment)]//p[contains(., '%s')]", comment);
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
