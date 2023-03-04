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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

import ui.admin.pages.CategoriesPage;
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
    @FindBy(xpath = "//div[@class='wp-menu-name' and text()='Posts']")
    WebElement postsMenu;
    @FindBy(xpath = "//a[@href='edit-tags.php?taxonomy=category']")
    WebElement categoriesButton;
    Actions hoverPostMenu;

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
    public void hoverPostMenu() {
        hoverPostMenu = new Actions(driver);
        hoverPostMenu.moveToElement(postsMenu).perform();
    }
    public void clickCategoriesButton() {
        hoverPostMenu.moveToElement(categoriesButton).perform();
        categoriesButton.click();

    }
    public boolean categoriesButtonIsNotAvailable() {
        hoverPostMenu();
        try {
            categoriesButton.isDisplayed();
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    public boolean postMenuIsNotAvailable() {
        try {
            postsMenu.isDisplayed();
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    public CategoriesPage goToCategoriesPage() {
        hoverPostMenu();
        clickCategoriesButton();
        return new CategoriesPage();
    }
}
