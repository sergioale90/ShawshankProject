package ui.admin.pages;

import framework.selenium.UIMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

public class CommentsPage extends BasePageObject {

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
        String commentRowAux = String.format(commentRowLocator,commentId);
        WebElement elementRow = driver.findElement(By.xpath(commentRowAux));
        UIMethods.moveToWebElement(elementRow);

        WebElement trashLink = elementRow.findElement(By.cssSelector("span.trash a"));
        trashLink.click();
    }

    public String goToTrash() {
        trashLink.click();
        return driver.getCurrentUrl();
    }
}
