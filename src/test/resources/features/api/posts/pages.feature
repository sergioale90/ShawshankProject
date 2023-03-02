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

  @UpdateAPagePublished
  Scenario Outline: A user with proper role should be able to update a page
    Given the user is authenticated with "<User Role>" role
    When the user updates a page with the following params
      | title                           | status         |  content                          | excerpt                      |
      | Test WAPIUpdate Page Title      | publish        |  Test WAPIUpdate Page Content     | Test WAPIUpdate Page Excerp  |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the page should have been updated with the proper values

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @DeleteAPageToTrash
  Scenario Outline: A user with proper role should be able to send a page to trash status
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to delete a page
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the page should have been send to trash status

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @DeleteAPagePermanently
  Scenario Outline: A user with proper role should be able to delete a page permanently
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to delete permanently a page
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the page should have been deleted permanently

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @UnableToCreateAndPublishAPage @Bug
  Scenario Outline: A user without a proper role should not be able to create a page with publish status
    Given the user is authenticated with "<User Role>" role
    When the user creates a page with the following values
      | title                     | status         |  content                    | excerpt                |
      | Test WAPI Page Title      | publish        |  Test WAPI Page Content     | Test WAPI Page Excerp  |
    Then the user should get a "<Status Line>" response
    And the user should not get a response valid and have a body
    And the user should see the response returned and have a body with the following values
      | code   | message   | data   |
      | <Code> | <Message> | <Data> |

    Examples:
      | User Role     | Status Line               | Code                | Message                                                   | Data         |
      | author        | HTTP/1.1 403 Forbidden    | rest_cannot_create  | Sorry, you are not allowed to create pages as this user.  | [status:403] |
      | contributor   | HTTP/1.1 403 Forbidden    | rest_cannot_create  | Sorry, you are not allowed to create pages as this user.  | [status:403] |
      | subscriber    | HTTP/1.1 403 Forbidden    | rest_cannot_create  | Sorry, you are not allowed to create pages as this user.  | [status:403] |

  @UnableToGetAllPages @Bug
  Scenario Outline: A user without a proper role should not be able to retrieve all pages
    Given the user is authenticated with "<User Role>" role
    When the user tries to retrieve all pages list
    Then the user should get a "<Status Line>" response
    And the user should not get a response valid and have a body
    And the user should see the response returned and have a body with the following values
      | code   | message   | data   |
      | <Code> | <Message> | <Data> |

    Examples:
      | User Role     | Status Line               | Code                | Message                                                | Data         |
      | author        | HTTP/1.1 403 Forbidden    | rest_cannot_create  | Sorry, you are not allowed to get posts as this user.  | [status:403] |
      | contributor   | HTTP/1.1 403 Forbidden    | rest_cannot_create  | Sorry, you are not allowed to get posts as this user.  | [status:403] |
      | subscriber    | HTTP/1.1 403 Forbidden    | rest_cannot_create  | Sorry, you are not allowed to get posts as this user.  | [status:403] |

  @UnableCreateAPageWithoutTitle @Bug
  Scenario Outline: A user with proper role should not able to create a page without title
    Given the user is authenticated with "<User Role>" role
    When the user creates a page with the following values
      | status         |  content                    | excerpt                |
      | publish        |  Test WAPI Page Content     | Test WAPI Page Excerp  |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the page have been created with the proper values
    And the user should see the response returned and have a body with the following values
      | code   | message   | data   |
      | <Code> | <Message> | <Data> |

    Examples:
      | User Role        | Status Line             	| Code                | Message                  | Data         |
      | administrator    | HTTP/1.1 400 Bad Request | rest_cannot_create  | page title is required.  | [status:400] |
      | editor           | HTTP/1.1 400 Bad Request | rest_cannot_create  | page title is required.  | [status:400] |
