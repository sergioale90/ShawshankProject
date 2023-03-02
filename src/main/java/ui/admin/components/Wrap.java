/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.admin.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.admin.BaseAdminPage;

public class Wrap extends BaseAdminPage {
    @FindBy(css = "li.trash a")
    private WebElement linkTrash;

    @FindBy(css = "li.publish a")
    private WebElement linkPublished;

    @FindBy(css = "li.draft a")
    private WebElement linkDraft;

    @FindBy(css = "input#post-search-input")
    private WebElement inputSearch;

    @FindBy(css = "input#search-submit")
    private WebElement buttonSearch;

    public Wrap() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
    }

    public void getLinkTrash() {
        linkTrash.click();
    }

    public void getLinkPublish() {
        linkPublished.click();
    }

    public void getLinkDraft() {
        linkDraft.click();
    }
    public void getButtonSearchSubmit() {
        buttonSearch.submit();
    }

    public void setTitleTextBox(String title) {
        inputSearch.click();
        inputSearch.clear();
        inputSearch.sendKeys(title);
    }
}
