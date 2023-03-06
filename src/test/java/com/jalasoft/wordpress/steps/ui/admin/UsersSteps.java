/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package com.jalasoft.wordpress.steps.ui.admin;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import ui.admin.pages.EditUsersPage;
import ui.admin.pages.HomeAdminPage;
import ui.admin.pages.NewUsersPage;
import ui.admin.pages.ProfilePage;
import ui.admin.pages.UsersPage;
import ui.controller.UserController;

import java.util.List;
import java.util.Map;

public class UsersSteps {

    private final UserController controller;
    private final HomeAdminPage homeAdminPage;
    private UsersPage usersPage;
    private NewUsersPage newUsersPage;

    private EditUsersPage editUsersPage;
    private ProfilePage profilePage;


    public UsersSteps(UserController controller, HomeAdminPage homeAdminPage) {
        this.controller = controller;
        this.homeAdminPage = homeAdminPage;
    }

    @When("the user goes to User page using the left side menu bar")
    public void theUserGoesToUserPageUsingTheLeftSideMenuBar() {
        usersPage = homeAdminPage.getLeftSideBarMenu().goToUsersPage();
    }

    @When("the user goes to User Profile using the left side menu bar")
    public void theUserGoesToUserProfileUsingTheLeftSideMenuBar() {
        profilePage = homeAdminPage.getLeftSideBarMenu().goToProfilePage();
    }

    @And("the user goes to New User page using the Add New button on User page")
    public void theUserGoesToNewUserPageUsingTheAddNewButtonOnUserPage() {
        newUsersPage = homeAdminPage.getLeftSideBarMenu().goToNewUsersPage();
    }

    @And("the user creates a new User with the following values")
    public void theUserCreatesANewUserWithTheFollowingValues(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);

        String username = (String) values.get("username");
        String email = (String) values.get("email");
        String name = (String) values.get("name");
        String lastName = (String) values.get("lastName");
        String password = (String) values.get("password");
        String roles = (String) values.get("roles");

        controller.setUsername(username);
        controller.setEmail(email);
        controller.setName(name);
        controller.setLastName(lastName);
        controller.setPassword(password);
        controller.setRoles(roles);

        if (roles == null) {
            newUsersPage.createUser(username, email, name, lastName, password);
        } else {
            newUsersPage.createUser(username, email, name, lastName, password, roles);
        }
    }

    @Then("the User should have been published successfully")
    public void theUserShouldHaveBeenPublishedSuccessfully() {
        controller.setId(usersPage.getIdUser(controller.getUsername()));
        Assert.assertTrue(usersPage.existUserName(controller.getUsername()),
                "post published message was not displayed");
    }

    @And("the user selects the {string} role from the role bar")
    public void theUserSelectsTheRoleFromTheRoleBar(String role) {
        controller.setRoles(role);
        usersPage.filterARole(role);
    }

    @Then("the user sees a list of users with the chosen role")
    public void theUserSeesAListOfUsersWithTheChosenRole() {
        Assert.assertTrue(usersPage.verifyRoles(controller.getRoles()));
    }

    @And("the user searches for {string} with the Search Users option")
    public void theUserSearchesForWithTheSearchUsersOption(String user) {
        usersPage.searchUser(user);
        controller.setUsername(user);
    }

    @And("is sure that the user {string}")
    public void isSureThatTheUser(String exist) {
        controller.setMessage(exist);
    }

    @Then("the user should see the results of his search")
    public void theUserShouldSeeTheResultsOfHisSearch() {
        Assert.assertEquals(usersPage.ifExistUser(controller.getUsername()), controller.getMessage());
    }

    @And("the user hide column {string} from the users table")
    public void theUserHideColumnFromTheUsersTable(String column) {
        usersPage.hideColumn(column);
    }

    @Then("the user should see the list without the hidden column")
    public void theUserShouldSeeTheListWithoutTheHiddenColumn() {
        Assert.assertFalse(usersPage.verifyHideColumn());
    }

    @And("the user goes to Edit User using the buttons that of each row")
    public void theUserGoesToEditUserUsingTheButtonsThatOfEachRow() {
        editUsersPage = usersPage.goToEdit(controller.getUsername());
    }

    @And("the user goes to update a user with the following params")
    public void theUserGoesToUpdateAUserWithTheFollowingParams(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);

        String fisrtName = (String) values.get("lastName");
        String lastName = (String) values.get("lastName");
        String email = (String) values.get("email");
        editUsersPage.fieldsToEdit(fisrtName, lastName, email);
    }

    @Then("the User should have been updated successfully")
    public void theUserShouldHaveBeenUpdatedSuccessfully() {
        Assert.assertTrue(editUsersPage.getMsgUpdated());
    }

    @And("the user changes role from user list")
    public void theUserChangesRoleFromUserList(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);
        String roles = (String) values.get("role");
        controller.setRoles(roles);
        usersPage.chageRolFromList(roles, controller.getUsername());
    }

    @Then("the user should see the updated role in the user list")
    public void theUserShouldSeeTheUpdatedRoleInTheUserList() {
        String actualRole = usersPage.verifyTextRoleChanged(controller.getUsername());
        Assert.assertEquals(actualRole, controller.getRoles());
        Assert.assertTrue(usersPage.verifyRoleChanged(controller.getUsername()));
    }

    @And("the user goes to Delete User using the buttons that of each row")
    public void theUserGoesToDeleteUserUsingTheButtonsThatOfEachRow() {
        usersPage.goToDelete(controller.getUsername());
    }

    @Then("the User should have been deleted successfully")
    public void theUserShouldHaveBeenDeletedSuccessfully() {
        Assert.assertTrue(usersPage.verifyUserDeleted());
    }

    @And("the user changes to delete in Bulk Actions from user list")
    public void theUserChangesToDeleteInBulkActionsFromUserList() {
        usersPage.chageToDeleteBulkActions(controller.getUsername());
    }

    @And("the user goes to profile to update field with the following params")
    public void theUserGoesToProfileToUpdateFieldWithTheFollowingParams(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> values = queryParamsList.get(0);

        String fisrtName = (String) values.get("lastName");
        String lastName = (String) values.get("lastName");
        String email = (String) values.get("email");
        profilePage.FieldsToEdit(fisrtName, lastName, email);
    }

    @Then("the profile should have been updated successfully")
    public void theProfileShouldHaveBeenUpdatedSuccessfully() {
        Assert.assertTrue(profilePage.getMsgUpdated());
    }
}
