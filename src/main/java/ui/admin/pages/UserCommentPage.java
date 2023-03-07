package ui.admin.pages;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import ui.admin.BaseAdminPage;

public class UserCommentPage extends BaseAdminPage {
    public UserCommentPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() throws WebDriverException {
    }
}