@APIComments @API
Feature: API Comments

  @GetAllComments
  Scenario Outline: A user with a proper role should be able to retrieve a list of all comments
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve all comments
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | author        | HTTP/1.1 200 OK |

  @GetCommentsWithPagination
  Scenario Outline: A user with a proper role should be able to retrieve two comments per page
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve two comments per page
    Then the user should get a "<Status Line>" response
    And the user should get a list with two comments

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @GetAllCommentsOfAPost
  Scenario Outline: A user with a proper role should be able to retrieve all comments of a post
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve all comments of a post
    Then the user should get a "<Status Line>" response
    And the user should get a list of comments with all comments of a post

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @PublishAComment
  Scenario Outline: A user with proper role should be able to publish a new comment
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to publish a new comment with the following content
      | content       |
      | i'm a content |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And a new comment should have been publish

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |

  @RetrieveAComment
  Scenario Outline: A user with proper role should be able to retrieve a specific comment
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve a comment
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And a comment should have been retrieved

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @UpdateAComment
  Scenario Outline: A user with proper role should be able to update a specific comment
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to update a comment with the following params
      | content           |
      | i'm a new comment |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And a comment should have been updated with the proper params

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @UpdateACommentNonexistent
  Scenario Outline: A user with proper role should't be able to update a comment nonexistent
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to update a comment nonexistent
      | id   |
      | <Id> |
    Then the user should get a "<Status Line>" response
    And the user should get a invalid response and have a body
    And the comment shouldn't have been update and the response has a error "<Message>"

    Examples:
      | Id     | User Role     | Status Line            | Message                                                 |
      | -4     | administrator | HTTP/1.1 404 Not Found | No route was found matching the URL and request method. |
      | I'm ID | administrator | HTTP/1.1 404 Not Found | No route was found matching the URL and request method. |
      | 1000   | administrator | HTTP/1.1 404 Not Found | Invalid comment ID.                                     |


  @DeleteAComment
  Scenario Outline: A user with proper role should be able to delete a comment
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to delete a comment published
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the comment should have been deleted

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @SimplyDeleteAComment
  Scenario Outline: A user with proper role should be able to move a trash a comment
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to moved to the trash a comment published
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the comment should be moved to the trash

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |


  @DeleteACommentNonexistent
  Scenario Outline: A user with proper role should't be able to delete a comment Nonexistent
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to delete a comment nonexistent
      | id   |
      | <Id> |
    Then the user should get a "<Status Line>" response
    And the user should get a invalid response and have a body
    And the comment shouldn't have been created and the response has a error "<Message>"

    Examples:
      | Id     | User Role     | Status Line            | Message                                                 |
      | -4     | administrator | HTTP/1.1 404 Not Found | No route was found matching the URL and request method. |
      | I'm ID | administrator | HTTP/1.1 404 Not Found | No route was found matching the URL and request method. |
      | 1000   | administrator | HTTP/1.1 404 Not Found | Invalid comment ID.                                     |
