package ui.admin.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.admin.BaseAdminPage;

public class Wrap extends BaseAdminPage {
    @FindBy(css = "li.trash a")
    WebElement linkTrash;

    @FindBy(css = "input#post-search-input")
    WebElement inputSearch;

    @FindBy(css = "input#search-submit")
    WebElement buttonSearch;

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

    public void getButtonSearchSubmit() {
        buttonSearch.submit();
    }

    public void setTitleTextBox(String title) {
        inputSearch.click();
        inputSearch.clear();
        inputSearch.sendKeys(title);
    }
}
