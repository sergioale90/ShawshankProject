package ui.admin.components;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;
import ui.admin.pages.PostsPage;

public class LeftSideBarMenu extends BasePageObject {
    @FindBy(id = "menu-posts")
    WebElement postsMenuButton;

    public LeftSideBarMenu() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
    }

    public PostsPage goToNewPostPage() {
        postsMenuButton = wait.until(ExpectedConditions.elementToBeClickable(postsMenuButton));
        postsMenuButton.click();
        return new PostsPage();
    }
}
