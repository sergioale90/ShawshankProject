package ui.admin.pages;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import ui.admin.BaseAdminPage;

public class EditCommentPage  extends BaseAdminPage {

    public EditCommentPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }
    @Override
    public void waitUntilPageObjectIsLoaded() throws WebDriverException {
    }
}
