/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */

package ui.admin.pages;

import api.methods.APITagsMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.admin.BaseAdminPage;

import java.util.HashMap;
import java.util.Map;

public class TagsPage extends BaseAdminPage {
    @FindBy(css = "input#tag-search-input")
    private WebElement textboxSearch;

    @FindBy(css = "input#search-submit")
    private WebElement searchTagsButton;

    public TagsPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
    }

    public void setTextboxSearch(String nameTagSearch) {
        textboxSearch.click();
        textboxSearch.click();
        textboxSearch.sendKeys(nameTagSearch);
    }

    public void getSearchTagsButton() {
        searchTagsButton.submit();
    }

    public Map<String, String> verifyTheTagCreated(String tagName) {
        String tagRowLocator = "tr#tag-<id>";
        String id = APITagsMethods.findAIDTagByTitle(tagName);
        String rowTag = tagRowLocator.replace("<id>", id);

        WebElement element = (WebElement) driver.findElement(By.cssSelector(rowTag));
        String name = element.findElement(By.cssSelector("td.name")).getText();
        String slug = element.findElement(By.cssSelector("td.slug")).getText();
        String description = element.findElement(By.cssSelector("td.description p")).getText();

        Map<String, String> tagData = new HashMap<>();
        tagData.put("name", name);
        tagData.put("slug", slug);
        tagData.put("description", description);
        return tagData;
    }
}
