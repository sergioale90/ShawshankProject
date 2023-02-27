@APIPages @API
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

  @CreateAPagePublish
  Scenario Outline: A user with proper role should be able to create a page with publish status
    Given the user is authenticated with "<User Role>" role
    When the user creates a page with the following values
      | title                     | status         |  content                    | excerpt                |
      | Test WAPI Page Title      | publish        |  Test WAPI Page Content     | Test WAPI Page Excerp  |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the page have been created with the proper values

    Examples:
      | User Role        | Status Line          |
      | administrator    | HTTP/1.1 201 Created |
      | editor           | HTTP/1.1 201 Created |

  @CreateAPageDraft
  Scenario Outline: A user with proper role should be able to create a page with draft status
    Given the user is authenticated with "<User Role>" role
    When the user creates a page with the following values
      | title                           |  content                          | excerpt                      |
      | Test WAPI Page Draft Title      |  Test WAPI Page Draft Content     | Test WAPI Page Draft Excerp  |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the page have been created with the proper values

    Examples:
      | User Role        | Status Line          |
      | administrator    | HTTP/1.1 201 Created |
      | editor           | HTTP/1.1 201 Created |

  @RetrieveAPage
  Scenario Outline: A user with proper role should be able to retrieve a page
    Given the user is authenticated with "<User Role>" role
    When the user tries to retrieve a page
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the page should have been retrieved with the proper values

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |


  @Negative
  Scenario Outline: A user with no proper role should not be able to create a page with publish status
    Given the user is authenticated with "<User Role>" role
    When the user creates a page with the following values
      | title                     | status         |  content                    | excerpt                |
      | Test WAPI Page Title      | publish        |  Test WAPI Page Content     | Test WAPI Page Excerp  |
    Then the user should not get a response
    And the user should get an error message

    Examples:
      | User Role             |
      | author                |
      | contributor           |
      | subscriber            |



