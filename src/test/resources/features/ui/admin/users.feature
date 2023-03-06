@Users @UI
Feature: Users


  @ListUsersByRol
  Scenario Outline: A user with proper role should be able to filter the list of users by roles
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to User page using the left side menu bar
    And the user selects the "<Role>" role from the role bar
    Then the user sees a list of users with the chosen role

    Examples:
      | User Role     | Role          |
      | administrator | administrator |
      | administrator | editor        |
      | administrator | author        |
      | administrator | contributor   |
      | administrator | subscriber    |


  @FindAUser
  Scenario Outline: A user with proper role should be able to find a users
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to User page using the left side menu bar
    And the user searches for "<User Name>" with the Search Users option
    And is sure that the user "<Exist>"
    Then the user should see the results of his search

    Examples:
      | User Role     | User Name | Exist    |
      | administrator | James     | exist    |
      | administrator | JhonDoe   | notExist |


  @HideColumn
  Scenario Outline: A user with proper role should be able to hide a column from the users table
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to User page using the left side menu bar
    And the user hide column "<Column>" from the users table
    Then the user should see the list without the hidden column

    Examples:
      | User Role     | Column |
      | administrator | email  |
      | administrator | role   |
      | administrator | posts  |


  @CreateAUserUI
  Scenario Outline: A user with proper role should be able to create a User
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to User page using the left side menu bar
    And the user goes to New User page using the Add New button on User page
    And the user creates a new User with the following values
      | username   | name   | lastName   | email   | password   |
      | <Username> | <Name> | <Lastname> | <Email> | <Password> |
    Then the User should have been published successfully

    Examples:
      | User Role     | Username  | Name  | Lastname | Email          | Password |
      | administrator | pedro2023 | pedro | perez    | pedro@mail.com | pedro123 |


  @CreateAUserWithRoleUI
  Scenario Outline: A user with proper role should be able to create a User with a specific role
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to User page using the left side menu bar
    And the user goes to New User page using the Add New button on User page
    And the user creates a new User with the following values
      | username   | name   | lastName   | email   | password   | roles   |
      | <Username> | <Name> | <Lastname> | <Email> | <Password> | <Roles> |
    Then the User should have been published successfully

    Examples:
      | User Role     | Username | Name | Lastname | Email         | Password | Roles       |
      | administrator | juan2023 | juan | perez    | juan@mail.com | pedro123 | contributor |
      | administrator | juan2023 | juan | perez    | juan@mail.com | pedro123 | editor      |


  @UpdateAUserUI
  Scenario Outline: A user with proper role should be able to update a User
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to User page using the left side menu bar
    And the user goes to Edit User using the buttons that of each row
    And the user goes to update a user with the following params
      | email   | firstName    | lastName    |
      | <Email> | <First Name> | <Last Name> |
    Then the User should have been updated successfully

    Examples:
      | User Role     | First Name | Last Name | Email            |
      | administrator | pablito    | pelaez    | pablito@mail.com |
#      | administrator | First juan | pere N    | juan@mail.com |


  @UpdateAProfile
  Scenario Outline: A user with proper role should be able to update your profile
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to User page using the left side menu bar
    When the user goes to User Profile using the left side menu bar
    And the user goes to profile to update field with the following params
      | email   | firstName    | lastName    |
      | <Email> | <First Name> | <Last Name> |
    Then the profile should have been updated successfully

    Examples:
      | User Role     | First Name | Last Name | Email                      |
      | administrator | pablito    | pelaez    | tonio@mail.com             |
      | administrator | Jose       | Romay     | jose.romay@jala.university |


  @UpdateARoleUI
  Scenario Outline: A user with proper role should be able to update the role from List User
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to User page using the left side menu bar
    And the user changes role from user list
      | role   |
      | <Role> |
    Then the user should see the updated role in the user list

    Examples:
      | User Role     | Role        |
      | administrator | subscriber  |
      | administrator | editor      |
      | administrator | contributor |


  @DeleteAUserUI
  Scenario Outline: A user with proper role should be able to delete a User
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to User page using the left side menu bar
    And the user goes to Delete User using the buttons that of each row
    Then the User should have been deleted successfully

    Examples:
      | User Role     |
      | administrator |

  @DeleteAUserWithBulkActions
  Scenario Outline: A user with proper role should be able to delete a User from Bulk Actions
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to User page using the left side menu bar
    And the user changes to delete in Bulk Actions from user list
    Then the User should have been deleted successfully

    Examples:
      | User Role     |
      | administrator |