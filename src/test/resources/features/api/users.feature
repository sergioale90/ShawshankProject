@APIUsers @API
Feature: API Users

  @GetAllUsers
  Scenario Outline: A user with a proper role should be able to retrieve a list of users
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve all users
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body

    Examples: 
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |


  @GetAllUsersByRole
  Scenario Outline: A user with a proper role should be able to retrieve a list of users with a specific role
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve all users with "<Role>"
    Then the user should get a "<Status Line>" response
    And the user should get a list of users with the specified role

    Examples: 
      | User Role     | Status Line     | Role          |
      | administrator | HTTP/1.1 200 OK | administrator |


  @GetUsersWithPagination
  Scenario Outline: A user with a proper role should be able to retrieve two users per page
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve two users per page
    Then the user should get a "<Status Line>" response
    And the user should get a list of users with two users

    Examples: 
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |


  @CreateAUser
  Scenario Outline: A user with proper role should be able to create a new user
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a new user with the following params
      | username | email          | password | roles        |
      | pedro    | pedro@mail.com | pedro123 | contributor |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And a new user should have been created with the proper params

    Examples: 
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |


  @RetrieveAUser
  Scenario Outline: A user with proper role should be able to retrieve a user
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve a user
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And a user should have been retrieved with the proper params

    Examples: 
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |


  @UpdateAUser
  Scenario Outline: A user with proper role should be able to update a user
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to update a user with the following params
       | email            | password   | roles        |
      | pedrito@mail.com | pedrito123 | contributor |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And a user should have been updated with the proper params

    Examples: 
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |


  @DeleteAUser
  Scenario Outline: A user with proper role should be able to delete a user
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to delete a user
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user should have been deleted

    Examples: 
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |


  @RetrieveCurrentUser
  Scenario Outline: A user with the appropriate role must be able to retrieve the current user
    Given the current user is authenticated with "<User Role>" role
    When the user makes a request to retrieve the current user
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And a user should have been retrieved with the proper params

    Examples: 
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @UpdateCurrentUser
  Scenario Outline: A user with proper role should be able to update the current user
    Given the current user is authenticated with "<User Role>" role
    When the user makes a request to update a current user with the following params
      | email            | password   | roles         |
      | pedrito@mail.com | pedrito123 | administrator |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And a user should have been updated with the proper params

    Examples: 
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @DeleteCurrentUser
  Scenario Outline: A user with proper role should be able to the current user
    Given the current user is authenticated with "<User Role>" role
    When the user makes a request to delete the current user
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user should have been deleted

    Examples: 
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
