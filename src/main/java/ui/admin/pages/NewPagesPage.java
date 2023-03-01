package ui.admin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseEditPage;
import ui.sections.PublishPanel;

public class NewPagesPage extends BaseEditPage {
    public NewPagesPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
    }

    public void publishPage(String title, String content) {
        setTitleTextBox(title);
        setContentTextArea(content);

        PublishPanel panel = editHeader.clickPublishButton();
        panel.clickPublishButton();
    }

    public void draftPage(String title, String content) {
        setTitleTextBox(title);
        setContentTextArea(content);

        editHeader.clickSaveDraftButton();
    }

    public void updatePagePublished(String title, String content) {
        setTitleTextBox(title);
        setContentTextArea(content);

        editHeader.clickUpdateButton();
    }

    public boolean isPublishedMessageDisplayed() {
        String publishedMessageLocator = "//div[contains(@class, 'components-snackbar')][text()='Page published.']";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(publishedMessageLocator))).isDisplayed();
    }

    public boolean isSaveDraftMessageDisplayed() {
        String draftMessageLocator = "//div[contains(@class, 'components-snackbar')][text()='Draft saved.']";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(draftMessageLocator))).isDisplayed();
    }

    public boolean isUpdateMessageDisplayed() {
        String updateMessageLocator = "//div[contains(@class, 'components-snackbar')][text()='Page updated.']";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(updateMessageLocator))).isDisplayed();
    }

    public boolean isPageRevertedToDraftMessageDisplayed() {
        String pageRevertedMessageLocator = "div.components-snackbar";
        WebElement notificationMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(pageRevertedMessageLocator)));
        return "Page reverted to draft.".equals(notificationMessage.getText());
    }
}
