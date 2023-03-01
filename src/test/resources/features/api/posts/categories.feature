@APICategories @API
Feature: API Categories

  @GetAllCategories
  Scenario Outline: A user with a proper role should be able to retrieve all categories
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve all categories
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And response should have a proper amount of categories
    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @GetAllCategories
  Scenario Outline: A user without a proper role shouldn't be able to retrieve all categories
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve all categories
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    Examples:
      | User Role     | Status Line               |
      | author        | HTTP/1.1 403 Unauthorized |
      | contributor   | HTTP/1.1 403 Unauthorized |
      | subscriber    | HTTP/1.1 200 Unauthorized |

  @CreateACategory
  Scenario Outline: A user with proper role should be able to create a category
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a category only with a name
      | name                |
      | Category name test  |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And category should have been created with the proper name
    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |
      | editor        | HTTP/1.1 201 Created |