@APITags @API
Feature: API Pages

  @GetAllPages
  Scenario Outline: A user with a proper role should be able to retrieve all pages
    Given the user is authenticated with "<User Role>" role
    When the user tries to retrieve all pages list
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user should get a proper amount of pages

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |
