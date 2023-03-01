@LoginAdmin @UI
Feature: Login Admin

  Scenario Outline: A user with a proper role should be able to login to Admin page
    Given the user goes to Admin Login page
    When the user login to Admin page as a user with "<User Role>" role
    Then the user should login to Admin page successfully

    Examples:
      | User Role     |
      | administrator |
      | author        |
      | contributor   |
      | editor        |
      | subscriber    |

  Scenario: A user with invalid credentials should not be able to login to Admin page
    Given the user goes to Admin Login page
    When the user login to Admin page using invalid credentials
    Then the user should not be able to login to Admin page
    And an error message that indicates that the username is not registered should be displayed

  Scenario: A user with invalid email should not be able to login to Admin page
    Given the user goes to Admin Login page
    When the user login to Admin page using invalid email
    Then the user should not be able to login to Admin page
    And an error message that indicates that the email address is unknown should be displayed

  Scenario: Error messages should be displayed when a user tries to login to Admin page with no credentials
    Given the user goes to Admin Login page
    When the user login to Admin page with no credentials
    Then the user should not be able to login to Admin page
    And error messages that indicates that the username and password fields are empty should be displayed

  Scenario: An error message should be displayed when a user tries to login to Admin page with no username
    Given the user goes to Admin Login page
    When the user login to Admin page with no username
    Then the user should not be able to login to Admin page
    And an error message that indicates that the username field is empty should be displayed

  Scenario: An error message should be displayed when a user tries to login to Admin page with no password
    Given the user goes to Admin Login page
    When the user login to Admin page with no password
    Then the user should not be able to login to Admin page
    And an error message that indicates that the password field is empty should be displayed