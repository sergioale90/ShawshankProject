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
      | content                                         | title            | excerpt                    |
      | The content for this post is well, rather small | A new post title | This Post has this excerpt |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And post should have been created with the proper params
    And new post request response should have a valid schema for "<User Role>"

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |
      | author        | HTTP/1.1 201 Created |
      | editor        | HTTP/1.1 201 Created |

  @CreateADraftPost
  Scenario Outline: A user with proper role should be able to create a draft post
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a draft post with the following params
      | content                                               | title                  | excerpt                          |
      | The content for this draft post is well, rather small | A new draft post title | This draft Post has this excerpt |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And post should have been created with the proper params
    And new post request response should have a valid schema for "<User Role>"

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
      | author        | HTTP/1.1 200 OK |
      | contributor   | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |
      | subscriber    | HTTP/1.1 200 OK |

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
  Scenario Outline: A user with proper role should be able to update his own published post
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a post with the following params
      | content                                         | title            | excerpt                    |
      | The content for this post is well, rather small | A new post title | This Post has this excerpt |
    When the user makes a request to update a post with the following params
      | content                        | title                   | excerpt                   |
      | Test WAPI Post Content Updated | Test WAPI Title Updated | Test WAPI Excerpt Updated |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And post should have been updated with the proper params

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | author        | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @UpdateADraftPost
  Scenario Outline: A user with proper role should be able to update his own draft post to another state
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a post with the following params
      | content                                               | title                  | excerpt                          | status |
      | The content for this draft post is well, rather small | A new draft post title | This draft Post has this excerpt | draft  |
    When the user makes a request to update a post with the following params
      | content                        | title                   | excerpt                   | status   |
      | Test WAPI Post Content Updated | Test WAPI Title Updated | Test WAPI Excerpt Updated | <Status> |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response format and have a body
    And post should have been updated with the proper params

    Examples:
      | User Role     | Status Line            | Status  |
      | administrator | HTTP/1.1 200 OK        | draft   |
      | author        | HTTP/1.1 200 OK        | draft   |
      | contributor   | HTTP/1.1 200 OK        | draft   |
      | editor        | HTTP/1.1 200 OK        | draft   |
      | administrator | HTTP/1.1 200 OK        | future  |
      | author        | HTTP/1.1 200 OK        | future  |
      | contributor   | HTTP/1.1 403 Forbidden | future  |
      | editor        | HTTP/1.1 200 OK        | future  |
      | administrator | HTTP/1.1 200 OK        | pending |
      | author        | HTTP/1.1 200 OK        | pending |
      | contributor   | HTTP/1.1 200 OK        | pending |
      | editor        | HTTP/1.1 200 OK        | pending |
      | administrator | HTTP/1.1 200 OK        | private |
      | author        | HTTP/1.1 200 OK        | private |
      | contributor   | HTTP/1.1 403 Forbidden | private |
      | editor        | HTTP/1.1 200 OK        | private |

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

  @CreateAndRetrieveAPost
  Scenario Outline: A user with proper role should be able to create and retrieve a post
    Given the user is authenticated with "<Poster User Role>" role
    When the user makes a request to create a post with the following params
      | content                   | title              | excerpt              |
      | Test RU WAPI Post Content | Test RU WAPI Title | Test RU WAPI Excerpt |
    When the user is authenticated with "<Retriever User Role>" role
    When the user makes a request to retrieve a post
    Then the user should get a "<Status Line>" response
    And the user should get a valid response format and have a body
    And post should have been retrieved with the proper params

    Examples:
      | Poster User Role | Retriever User Role | Status Line     |
      | administrator    | administrator       | HTTP/1.1 200 OK |
      | administrator    | author              | HTTP/1.1 200 OK |
      | administrator    | contributor         | HTTP/1.1 200 OK |
      | administrator    | editor              | HTTP/1.1 200 OK |
      | administrator    | subscriber          | HTTP/1.1 200 OK |
      | author           | administrator       | HTTP/1.1 200 OK |
      | author           | author              | HTTP/1.1 200 OK |
      | author           | contributor         | HTTP/1.1 200 OK |
      | author           | editor              | HTTP/1.1 200 OK |
      | author           | subscriber          | HTTP/1.1 200 OK |
      | editor           | administrator       | HTTP/1.1 200 OK |
      | editor           | author              | HTTP/1.1 200 OK |
      | editor           | contributor         | HTTP/1.1 200 OK |
      | editor           | editor              | HTTP/1.1 200 OK |
      | editor           | subscriber          | HTTP/1.1 200 OK |

  @CreateAndRetrieveADraftPost
  Scenario Outline: A user with proper role should be able to create and retrieve a Draft post
    Given the user is authenticated with "<Poster User Role>" role
    When the user makes a request to create a draft post with the following params
      | content                         | title                    | excerpt                    |
      | Test Draft RU WAPI Post Content | Test Draft RU WAPI Title | Test Draft RU WAPI Excerpt |
    When the user is authenticated with "<Retriever User Role>" role
    When the user makes a request to retrieve a post
    Then the user should get a "<Status Line>" response
    And the user should get a valid response format and have a body
    And post should have been retrieved with the proper params

    Examples:
      | Poster User Role | Retriever User Role | Status Line            |
      | administrator    | administrator       | HTTP/1.1 200 OK        |
      | administrator    | author              | HTTP/1.1 403 Forbidden |
      | administrator    | contributor         | HTTP/1.1 403 Forbidden |
      | administrator    | editor              | HTTP/1.1 200 OK        |
      | administrator    | subscriber          | HTTP/1.1 403 Forbidden |
      | author           | administrator       | HTTP/1.1 200 OK        |
      | author           | author              | HTTP/1.1 200 OK        |
      | author           | contributor         | HTTP/1.1 403 Forbidden |
      | author           | editor              | HTTP/1.1 200 OK        |
      | author           | subscriber          | HTTP/1.1 403 Forbidden |
      | contributor      | administrator       | HTTP/1.1 200 OK        |
      | contributor      | author              | HTTP/1.1 403 Forbidden |
      | contributor      | contributor         | HTTP/1.1 200 OK        |
      | contributor      | editor              | HTTP/1.1 200 OK        |
      | contributor      | subscriber          | HTTP/1.1 403 Forbidden |
      | editor           | administrator       | HTTP/1.1 200 OK        |
      | editor           | author              | HTTP/1.1 403 Forbidden |
      | editor           | contributor         | HTTP/1.1 403 Forbidden |
      | editor           | editor              | HTTP/1.1 200 OK        |
      | editor           | subscriber          | HTTP/1.1 403 Forbidden |

  @CreateAndUpdateAPost
  Scenario Outline: A user with proper role should be able to create and update a post
    Given the user is authenticated with "<Poster User Role>" role
    When the user makes a request to create a post with the following params
      | content                   | title              | excerpt              |
      | Test CU WAPI Post Content | Test CU WAPI Title | Test CU WAPI Excerpt |
    When the user is authenticated with "<Updater User Role>" role
    When the user makes a request to update a post with the following params
      | content                           | title                      | excerpt                      |
      | Test RU WAPI Post Content Updated | Test RU WAPI Title Updated | Test RU WAPI Excerpt Updated |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response format and have a body
    And post should have been updated with the proper params

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
      | content                                 | title                            | excerpt                            | status  |
      | Draft Test CU WAPI Post Content Updated | Draft Test CU WAPI Title Updated | Draft Test CU WAPI Excerpt Updated | publish |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response format and have a body
    And post should have been updated with the proper params

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
      | contributor      | contributor       | HTTP/1.1 403 Forbidden |
      | contributor      | editor            | HTTP/1.1 200 OK        |
      | contributor      | subscriber        | HTTP/1.1 403 Forbidden |
      | editor           | administrator     | HTTP/1.1 200 OK        |
      | editor           | author            | HTTP/1.1 403 Forbidden |
      | editor           | contributor       | HTTP/1.1 403 Forbidden |
      | editor           | editor            | HTTP/1.1 200 OK        |
      | editor           | subscriber        | HTTP/1.1 403 Forbidden |

  @CreateAndDeleteAPostTrash
  Scenario Outline: A user with proper role should be able to Create and Delete a post by trash
    Given the user is authenticated with "<Poster User Role>" role
    When the user makes a request to create a post with the following params
      | content                   | title              | excerpt              |
      | Test CD WAPI Post Content | Test CD WAPI Title | Test CD WAPI Excerpt |
    When the user is authenticated with "<Deleter User Role>" role
    When the user makes a request to delete a published post by trash
    Then the user should get a "<Status Line>" response
    And the user should get a valid response format and have a body
    And the published post should have been trashed

    Examples:
      | Poster User Role | Deleter User Role | Status Line            |
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

  @CreateAndDeleteADraftPostTrash @Bug
  Scenario Outline: A user with proper role should be able Create and delete a Draft post by trash
    Given the user is authenticated with "<Poster User Role>" role
    When the user makes a request to create a draft post with the following params
      | content                         | title                    | excerpt                    |
      | Draft Test CD WAPI Post Content | Draft Test CD WAPI Title | Draft Test CD WAPI Excerpt |
    When the user is authenticated with "<Deleter User Role>" role
    When the user makes a request to delete a draft post by trash
    Then the user should get a "<Status Line>" response
    And the user should get a valid response format and have a body
    And the draft post should have been trashed

    Examples:
      | Poster User Role | Deleter User Role | Status Line            |
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
      | contributor      | contributor       | HTTP/1.1 403 Forbidden |
      | contributor      | editor            | HTTP/1.1 200 OK        |
      | contributor      | subscriber        | HTTP/1.1 403 Forbidden |
      | editor           | administrator     | HTTP/1.1 200 OK        |
      | editor           | author            | HTTP/1.1 403 Forbidden |
      | editor           | contributor       | HTTP/1.1 403 Forbidden |
      | editor           | editor            | HTTP/1.1 200 OK        |
      | editor           | subscriber        | HTTP/1.1 403 Forbidden |

  @CreateAndDeleteAPost
  Scenario Outline: A user with proper role should be able Create and Delete a Published post permanently
    Given the user is authenticated with "<Poster User Role>" role
    When the user makes a request to create a post with the following params
      | content                   | title              | excerpt              |
      | Test CD WAPI Post Content | Test CD WAPI Title | Test CD WAPI Excerpt |
    When the user is authenticated with "<Deleter User Role>" role
    When the user makes a request to delete a published post permanently
    Then the user should get a "<Status Line>" response
    And the user should get a valid response format and have a body
    And the published post should have been deleted permanently

    Examples:
      | Poster User Role | Deleter User Role | Status Line            |
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

  @CreateAndDeleteADraftPost @Bug
  Scenario Outline: A user with proper role should be able Create and Delete a Draft post permanently
    Given the user is authenticated with "<Poster User Role>" role
    When the user makes a request to create a draft post with the following params
      | content                         | title                    | excerpt                    |
      | Draft Test CD WAPI Post Content | Draft Test CD WAPI Title | Draft Test CD WAPI Excerpt |
    When the user is authenticated with "<Deleter User Role>" role
    When the user makes a request to delete a draft post permanently
    Then the user should get a "<Status Line>" response
    And the user should get a valid response format and have a body
    And the draft post should have been deleted permanently

    Examples:
      | Poster User Role | Deleter User Role | Status Line            |
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
      | contributor      | contributor       | HTTP/1.1 403 Forbidden |
      | contributor      | editor            | HTTP/1.1 200 OK        |
      | contributor      | subscriber        | HTTP/1.1 403 Forbidden |
      | editor           | administrator     | HTTP/1.1 200 OK        |
      | editor           | author            | HTTP/1.1 403 Forbidden |
      | editor           | contributor       | HTTP/1.1 403 Forbidden |
      | editor           | editor            | HTTP/1.1 200 OK        |
      | editor           | subscriber        | HTTP/1.1 403 Forbidden |