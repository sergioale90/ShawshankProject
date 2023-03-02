/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
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
    private WebElement postsMenuButton;

    @FindBy(id = "menu-pages")
    private WebElement pagesMenuButton;
    @FindBy(xpath = "//li[@id='menu-posts']/descendant::a[text()='Add New']")
    private WebElement addNewPostButton;

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
