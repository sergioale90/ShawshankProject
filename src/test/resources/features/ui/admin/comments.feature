@Comments @UI
Feature: Comments

  @UserComments
  Scenario Outline: A user with the proper role should be able to create a comment
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Post page
    And the user comments "<Comment User>" on the post page
    Then the user should review that his comment "<Comment User>" was added

    Examples:
      | User Role     | Comment User                        |
      | editor        | This is my comment as editor        |
      | author        | This is my comment as author        |
      | contributor   | This is my comment as contributor   |
      | subscriber    | This is my comment as subscriber    |