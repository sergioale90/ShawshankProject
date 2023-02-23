@APIPosts @API
Feature: API Posts

  @GetAllPosts
  Scenario Outline: A user with a proper role should be able to retrieve all posts
    Given I am authenticated with "<User Role>" role
    When I make a request to retrieve all posts
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
     And response should have a proper amount of posts

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |
    | author        | HTTP/1.1 200 OK |
    | contributor   | HTTP/1.1 200 OK |
    | editor        | HTTP/1.1 200 OK |
    | subscriber    | HTTP/1.1 200 OK |

  @CreateAPost
  Scenario Outline: A user with proper role should be able to create a post
    Given I am authenticated with "<User Role>" role
    When I make a request to create a post with the following params
      | content                | title           | excerpt           |
      | Test WAPI Post Content | Test WAPI Title | Test WAPI Excerpt |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
     And post should have been created with the proper params

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |
      | author        | HTTP/1.1 201 Created |
      | contributor   | HTTP/1.1 201 Created |
      | editor        | HTTP/1.1 201 Created |


  @RetrieveAPost
  Scenario Outline: A user with proper role should be able to retrieve a post
    Given I am authenticated with "<User Role>" role
    When I make a request to retrieve a post
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
     And post should have been retrieved with the proper params

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @UpdateAPost
  Scenario Outline: A user with proper role should be able to update a post
    Given I am authenticated with "<User Role>" role
    When I make a request to update a post with the following params
      | content                        | title                   | excerpt                   |
      | Test WAPI Post Content Updated | Test WAPI Title Updated | Test WAPI Excerpt Updated |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
     And post should have been updated with the proper params

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @DeleteAPost
  Scenario Outline: A user with proper role should be able to delete a post
    Given I am authenticated with "<User Role>" role
    When I make a request to delete a post
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
     And post should have been deleted

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |