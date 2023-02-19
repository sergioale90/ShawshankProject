@LoginAdmin @UI
Feature: Login Admin

  Scenario Outline: A user with a proper role should be able to login to Admin page
    Given I navigate to Admin Login page
    When I login to Admin page as a user with "<User Role>" role
     Then I should login to Admin page successfully

    Examples:
      | User Role     |
      | administrator |
      | author        |
      | contributor   |
      | editor        |
      | subscriber    |