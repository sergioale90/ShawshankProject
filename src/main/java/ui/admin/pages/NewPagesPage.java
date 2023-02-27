package ui.admin.pages;

import org.openqa.selenium.By;
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


    public boolean isPublishedMessageDisplayed() {
        String publishedMessageLocator = "//div[contains(@class, 'components-snackbar')][text()='Page published.']";
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(publishedMessageLocator))).isDisplayed();
    }
}
