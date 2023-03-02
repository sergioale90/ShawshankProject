package ui.admin.components;

import framework.selenium.UIMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

import ui.admin.pages.PagesPage;

import ui.admin.pages.NewPostPage;
import ui.admin.pages.PostsPage;

public class LeftSideBarMenu extends BasePageObject {
    @FindBy(id = "menu-posts")
    WebElement postsMenuButton;

    @FindBy(id = "menu-pages")
    WebElement pagesMenuButton;
    @FindBy(xpath = "//li[@id='menu-posts']/descendant::a[text()='Add New']")
    WebElement addNewPostButton;

    public LeftSideBarMenu() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
    }

    public void clickPostMenuButton() {
        postsMenuButton = wait.until(ExpectedConditions.elementToBeClickable(postsMenuButton));
        postsMenuButton.click();
    }

    public void clickAddNewPostButton() {
        addNewPostButton = wait.until(ExpectedConditions.elementToBeClickable(addNewPostButton));
        addNewPostButton.click();
    }

    public PostsPage goToPostPage() {
        clickPostMenuButton();
        return new PostsPage();
    }

    public PagesPage goToNewPagePage() {
        pagesMenuButton = wait.until(ExpectedConditions.elementToBeClickable(pagesMenuButton));
        pagesMenuButton.click();
        return new PagesPage();
    }

    public NewPostPage goToNewPostPageUsingPopupMenu() {
        UIMethods.moveToWebElement(postsMenuButton);
        clickAddNewPostButton();
        return new NewPostPage();
    }
}
