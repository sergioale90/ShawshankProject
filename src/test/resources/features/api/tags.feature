@APITags @API
Feature: API Tags

  @GetAllTags
  Scenario Outline: A user with a proper role should be able to retrieve all tags
    Given the user is authenticated with "<User Role>" role
    When the user tries to retrieve all tags list
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user should get a proper amount of tags

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @CreateATag
  Scenario Outline: A user with proper role should be able to create a tag
    Given the user is authenticated with "<User Role>" role
    When the user creates a tag with the following values
      | name            | slug           |  description              |
      | TagTest         | testtag        |  description tag test     |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the tag have been created with the proper values

    Examples:
      | User Role        | Status Line          |
      | administrator    | HTTP/1.1 201 Created |
      | editor           | HTTP/1.1 201 Created |




