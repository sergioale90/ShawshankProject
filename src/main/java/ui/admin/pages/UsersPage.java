/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package ui.admin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import ui.admin.BaseAdminPage;

import java.util.List;

/**
 * This class is responsible for identifying each web element that
 * contains the user's administrator page, methods and locators.
 *
 * @version 1.0
 */
public class UsersPage extends BaseAdminPage {

    private String idUser = "//a[text()='user']/ancestor::tr";
    private String userName = "//a[text()='user']";
    private String name = "//td[@class='name column-name'][text()='valor]']";
    private String email = "//td[@class='email column-email']//a[text()='mail']";
    private String role = "//td[@class='role column-role'][text()='role']";
    private String userResult = "//strong/a[contains(text(),'as')]";
    private String barRole = "//a[@href='users.php?role=option']";
    private String checkColumn = "//input[@value='param']/parent::label";
    private String tableColumn = "//th[@id='column'][contains(@class,'hidden')] ";
    private String editHover = "//a[text()='value']/ancestor::td/descendant::div/span[@class='edit']/a";
    private String deleteHover = "//a[text()='value']/ancestor::td/descendant::div/span[@class='delete']/a";
    private String userCheck = "//a[text()='value']/ancestor::tr/th/input";
    private String userRoleRow = " //a[text()='value']/ancestor::tr/td[@class='role column-role']";
    private String msgDeleted = "//p[text()='User deleted.']";


    private String auxColumn;

    @FindBy(id = "user-search-input")
    private WebElement searchUser;

    @FindBy(xpath = "//td[text()='No users found.']")
    private WebElement noUserFound;

    @FindBy(id = "search-submit")
    private WebElement searchUserButton;

    @FindBy(id = "show-settings-link")
    private WebElement screenOption;

    @FindBy(id = "changeit")
    private WebElement changeRoleButton;

    @FindBy(id = "doaction")
    private WebElement bulkActionButton;

    @FindBy(id = "submit")
    private WebElement deleteButton;

    public UsersPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        screenOption = wait.until(ExpectedConditions.elementToBeClickable(screenOption));
    }

    public String getUserName(String userX) {
        WebElement contentUserName = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath(userName.replace("user", userX))));
        return contentUserName.getText();
    }

    public boolean existUserName(String userX) {
        return wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath(userName.replace("user", userX)))).isDisplayed();
    }

    public String getIdUser(String userX) {
        WebElement idUsers = driver.findElement(By.xpath(idUser.replace("user", userX)));
        String idValue = idUsers.getAttribute("id");
        return idValue.substring(idValue.indexOf("-") + 1);
    }

    public void filterARole(String roleFilter) {
        WebElement filterRole = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath(barRole.replace("option", roleFilter))));
        filterRole.click();
    }

    public boolean verifyRoles(String roles) {
        WebElement tableElement = driver.findElement(By.id("the-list"));
        List<WebElement> rows = tableElement.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            WebElement roleTable = row.findElement(By.xpath("//td[@data-colname='Role']"));
            String roleLower = roleTable.getText().substring(0, 1).toLowerCase() + roleTable.getText().substring(1);
            if (!roleLower.equals(roles)) {
                return false;
            }
        }
        return true;
    }

    public void searchUser(String searchName) {
        searchUser.sendKeys(searchName);
        searchUserButton.click();
    }

    public String ifExistUser(String search) {
        WebElement searchedUser = wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath(userResult.replace("as", search))));

        if (searchedUser.isDisplayed()) {
            return "exist";
        } else if (noUserFound.isDisplayed()) {
            return "notExist";
        }
        return "Wrong in search";
    }

    public void hideColumn(String column) {
        screenOption.click();
        WebElement columnElement = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath(checkColumn.replace("param", column))));
        columnElement.click();
        screenOption.click();
        auxColumn = column;
    }

    public boolean verifyHideColumn() {
        return wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath(tableColumn.replace("column", auxColumn)))).isDisplayed();
    }

    public EditUsersPage goToEdit(String user) {
        WebElement columnElement = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath(editHover.replace("value", user))));

        String href = columnElement.getAttribute("href");
        driver.get(href);
        return new EditUsersPage();
    }

    public void chageRolFromList(String roleList, String user) {
        auxColumn = roleList;
        WebElement check = driver.findElement(By.xpath(userCheck.replace("value", user)));
        check.click();

        WebElement selectElement = driver.findElement(By.id("new_role"));
        Select select = new Select(selectElement);
        select.selectByValue(roleList);
        changeRoleButton.click();
    }

    public String verifyTextRoleChanged(String user) {
        WebElement selectElement = driver.findElement(By.xpath(userRoleRow.replace("value", user)));
        String roleLower = selectElement.getText().substring(0, 1).toLowerCase() + selectElement.getText().substring(1);
        return roleLower;
    }

    public boolean verifyRoleChanged(String user) {
        return wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath(userRoleRow.replace("value", user)))).isDisplayed();
    }

    public void goToDelete(String user) {
        WebElement columnElement = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath(deleteHover.replace("value", user))));

        String href = columnElement.getAttribute("href");
        driver.get(href);
        deleteButton.click();
    }

    public void chageToDeleteBulkActions(String user) {
        WebElement check = driver.findElement(By.xpath(userCheck.replace("value", user)));
        check.click();

        WebElement selectElement = driver.findElement(By.id("bulk-action-selector-top"));
        Select select = new Select(selectElement);
        select.selectByValue("delete");

        bulkActionButton.click();
        deleteButton.click();
    }

    public boolean verifyUserDeleted() {
        return wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath(msgDeleted))).isDisplayed();
    }
}
