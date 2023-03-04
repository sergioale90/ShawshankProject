@APIPosts @API
Feature: API Posts

  @GetAllPosts
  Scenario Outline: A user with a proper role should be able to retrieve all posts
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve all posts
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
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a post with the following params
      | content                | title           | excerpt           |
      | Test WAPI Post Content | Test WAPI Title | Test WAPI Excerpt |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And post should have been created with the proper params

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |
      | author        | HTTP/1.1 201 Created |
      | editor        | HTTP/1.1 201 Created |

  @CreateADraftPost
  Scenario Outline: A user with proper role should be able to create a draft post
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a draft post with the following params
      | content                      | title                 | excerpt                 |
      | Draft Test WAPI Post Content | Draft Test WAPI Title | Draft Test WAPI Excerpt |
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
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve a post
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And post should have been retrieved with the proper params

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @RetrieveADraftPost
  Scenario Outline: A user with proper role should be able to retrieve a draft post
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to retrieve a post
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And post should have been retrieved with the proper params

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @UpdateAPost
  Scenario Outline: A user with proper role should be able to update a post
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to update a post with the following params
      | content                        | title                   | excerpt                   |
      | Test WAPI Post Content Updated | Test WAPI Title Updated | Test WAPI Excerpt Updated |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And post should have been updated with the proper params

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @UpdateADraftPost
  Scenario Outline: A user with proper role should be able to update a draft post
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to update a post with the following params
      | content                        | title                   | excerpt                   | status  |
      | Test WAPI Post Content Updated | Test WAPI Title Updated | Test WAPI Excerpt Updated | publish |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And post should have been updated with the proper params

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @CreateAndUpdateAPost
  Scenario Outline: A user with proper role should be able to create and update a post
    Given the user is authenticated with "<Poster User Role>" role
    When the user makes a request to create a post with the following params
      | content                   | title              | excerpt              |
      | Test CU WAPI Post Content | Test CU WAPI Title | Test CU WAPI Excerpt |
    When the user is authenticated with "<Updater User Role>" role
    When the user makes a request to update a post with the following params
      | content                        | title                   | excerpt                   |
      | Test WAPI Post Content Updated | Test WAPI Title Updated | Test WAPI Excerpt Updated |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response format and have a body

    Examples:
      | Poster User Role | Updater User Role | Status Line            |
      | administrator    | administrator     | HTTP/1.1 200 OK        |
      | administrator    | author            | HTTP/1.1 403 Forbidden |
      | administrator    | contributor       | HTTP/1.1 403 Forbidden |
      | administrator    | editor            | HTTP/1.1 200 OK        |
      | administrator    | subscriber        | HTTP/1.1 403 Forbidden |
      | author           | administrator     | HTTP/1.1 200 OK        |
      | author           | author            | HTTP/1.1 200 OK        |
      | author           | contributor       | HTTP/1.1 403 Forbidden |
      | author           | editor            | HTTP/1.1 200 OK        |
      | author           | subscriber        | HTTP/1.1 403 Forbidden |
      | editor           | administrator     | HTTP/1.1 200 OK        |
      | editor           | author            | HTTP/1.1 403 Forbidden |
      | editor           | contributor       | HTTP/1.1 403 Forbidden |
      | editor           | editor            | HTTP/1.1 200 OK        |
      | editor           | subscriber        | HTTP/1.1 403 Forbidden |

  @CreateAndUpdateADraftPost
  Scenario Outline: A user with proper role should be able to create and update a Draft post
    Given the user is authenticated with "<Poster User Role>" role
    When the user makes a request to create a draft post with the following params
      | content                      | title                 | excerpt                 |
      | Draft Test WAPI Post Content | Draft Test WAPI Title | Draft Test WAPI Excerpt |
    When the user is authenticated with "<Updater User Role>" role
    When the user makes a request to update a post with the following params
      | content                              | title                         | excerpt                         | status  |
      | Draft Test WAPI Post Content Updated | Draft Test WAPI Title Updated | Draft Test WAPI Excerpt Updated | publish |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response format and have a body

    Examples:
      | Poster User Role | Updater User Role | Status Line            |
      | administrator    | administrator     | HTTP/1.1 200 OK        |
      | administrator    | author            | HTTP/1.1 403 Forbidden |
      | administrator    | contributor       | HTTP/1.1 403 Forbidden |
      | administrator    | editor            | HTTP/1.1 200 OK        |
      | administrator    | subscriber        | HTTP/1.1 403 Forbidden |
      | author           | administrator     | HTTP/1.1 200 OK        |
      | author           | author            | HTTP/1.1 200 OK        |
      | author           | contributor       | HTTP/1.1 403 Forbidden |
      | author           | editor            | HTTP/1.1 200 OK        |
      | author           | subscriber        | HTTP/1.1 403 Forbidden |
      | contributor      | administrator     | HTTP/1.1 200 OK        |
      | contributor      | author            | HTTP/1.1 403 Forbidden |
      | contributor      | contributor       | HTTP/1.1 200 OK        |
      | contributor      | editor            | HTTP/1.1 200 OK        |
      | contributor      | subscriber        | HTTP/1.1 403 Forbidden |
      | editor           | administrator     | HTTP/1.1 200 OK        |
      | editor           | author            | HTTP/1.1 403 Forbidden |
      | editor           | contributor       | HTTP/1.1 403 Forbidden |
      | editor           | editor            | HTTP/1.1 200 OK        |
      | editor           | subscriber        | HTTP/1.1 403 Forbidden |

  @DeleteAPostTrash
  Scenario Outline: A user with proper role should be able to delete a published post by trash
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to delete a published post by trash
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the published post should have been trashed

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @DeleteAPost
  Scenario Outline: A user with proper role should be able to delete a published post permanently
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to delete a published post permanently
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the published post should have been deleted permanently

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @DeleteADraftPostTrash
  Scenario Outline: A user with proper role should be able to delete a Draft post by trash
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to delete a draft post by trash
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the draft post should have been trashed

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @DeleteADraftPost
  Scenario Outline: A user with proper role should be able to delete a Draft post permanently
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to delete a draft post permanently
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the draft post should have been deleted permanently

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |