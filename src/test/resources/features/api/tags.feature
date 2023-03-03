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
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the tag have been created with the proper values

    Examples:
      | User Role        | Status Line          |
      | administrator    | HTTP/1.1 201 Created |
      | editor           | HTTP/1.1 201 Created |

  @RetrieveATag
  Scenario Outline: A user with proper role should be able to retrieve a tag
    Given the user is authenticated with "<User Role>" role
    When the user tries to retrieve a tag
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the tag should have been retrieved with the proper values

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @UnableToGetAllTags @Bug
  Scenario Outline: A user without a proper role should not be able to retrieve all tags
    Given the user is authenticated with "<User Role>" role
    When the user tries to retrieve all tags list
    Then the user should get a "<Status Line>" response
    And the user should not get a response valid and have a body
    And the user should see the response returned and have a body with the following values
      | code   | message   | data   |
      | <Code> | <Message> | <Data> |

    Examples:
      | User Role     | Status Line               | Code                | Message                                               | Data         |
      | author        | HTTP/1.1 403 Forbidden    | rest_cannot_create  | Sorry, you are not allowed to get tags as this user.  | [status:403] |
      | contributor   | HTTP/1.1 403 Forbidden    | rest_cannot_create  | Sorry, you are not allowed to get tags as this user.  | [status:403] |
      | subscriber    | HTTP/1.1 403 Forbidden    | rest_cannot_create  | Sorry, you are not allowed to get tags as this user.  | [status:403] |



