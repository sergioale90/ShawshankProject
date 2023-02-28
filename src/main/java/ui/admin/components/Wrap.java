package ui.admin.components;

import api.methods.APIPagesMethods;
import io.restassured.response.Response;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.BasePageObject;

public class Wrap extends BasePageObject {
    @FindBy(css = "li.trash a")
    WebElement linkTrash;

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
}
