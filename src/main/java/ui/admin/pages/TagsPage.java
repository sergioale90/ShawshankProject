package ui.admin.pages;

import framework.selenium.UIMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.admin.BaseAdminPage;

public class TagsPage extends BaseAdminPage {

    private String tagRowLocator = "tr#post-<id>";

    @FindBy(css = "input#tag-search-input")
    WebElement textboxSearch;

    @FindBy(css = "input#search-submit")
    WebElement searchTagsButton;

    public TagsPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        // addNewPageButton = wait.until(ExpectedConditions.elementToBeClickable(addNewPageButton));
    }

    public boolean isTagNameLinkNotPresent(String title) {
        String tagNameLocator = String.format("//td[contains(@class, 'column-title')]//a[contains(.,'%s')]", title);
        return UIMethods.isWebElementNotPresentByXpathJs(tagNameLocator);
    }

    public void setTextboxSearch(String nameTagSearch) {
        textboxSearch.click();
        textboxSearch.click();
        textboxSearch.sendKeys(nameTagSearch);
    }

    public void getSearchTagsButton() {
        searchTagsButton.submit();
    }

    public boolean isTagFoundOnTheTableById(String idTag) {
        String tagFoundById = tagRowLocator.replace("<id>", idTag);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(tagRowLocator))).isDisplayed();
    }
}
