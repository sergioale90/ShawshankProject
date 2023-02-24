package ui.admin.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;
import ui.admin.pages.PagesPage;
import ui.admin.pages.PostsPage;

public class LeftSideBarMenu extends BasePageObject {
    @FindBy(id = "menu-posts")
    WebElement postsMenuButton;

    @FindBy(id = "menu-pages")
    WebElement pagesMenuButton;

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
    public PagesPage goToNewPagePage() {
        pagesMenuButton = wait.until(ExpectedConditions.elementToBeClickable(pagesMenuButton));
        pagesMenuButton.click();
        return new PagesPage();
    }


}
