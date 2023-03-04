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
  Scenario Outline: A user with a proper role should be able to create a tag
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a tag
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the tag should have been created with the proper values

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |
      | editor        | HTTP/1.1 201 Created |

  @CreateATag
  Scenario Outline: A user with a proper role should be able to create a tag
    Given the user is authenticated with "<User Role>" role
    When the user creates a tag with the following params
      | name     | slug       | description                 |
      | Tag Test | tagtestone | description to create a tag |
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the new tag request response should have a valid schema
    And the user reviews that the tag should have been created with the proper values

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |
      | editor        | HTTP/1.1 201 Created |

  @RetrieveATag
  Scenario Outline: A user with a proper role should be able to retrieve a tag
    Given the user is authenticated with "<User Role>" role
    When the user tries to retrieve a tag
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the tag should have been retrieved with the proper values

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @UpdateATag
  Scenario Outline: A user with a proper role should be able to update a tag
    Given the user is authenticated with "<User Role>" role
    When the user updates a tag using a json file
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the tag should have been updated with the proper values

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @DeleteATag
  Scenario Outline: A user with a proper role should be able to delete a tag
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to delete a tag
    Then the user should get a "<Status Line>" response
    And the user should get a valid response and have a body
    And the user reviews that the tag should have been deleted

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | editor        | HTTP/1.1 200 OK |

  @DeleteWithoutForceParam
  Scenario Outline: A user with a proper role should not be able to delete a tag without force param
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to delete a tag without force param
    Then the user should get a "<Status Line>" response
    And the user should see the response invalid and have a body
    And the user reviews that the response should be returned and have a body with the following values
      | code   | message   | data   |
      | <Code> | <Message> | <Data> |

    Examples:
      | User Role     | Status Line                  | Code                     | Message                                                    | Data         |
      | administrator | HTTP/1.1 501 Not Implemented | rest_trash_not_supported | Terms do not support trashing. Set 'force=true' to delete. | [status:501] |
      | editor        | HTTP/1.1 501 Not Implemented | rest_trash_not_supported | Terms do not support trashing. Set 'force=true' to delete. | [status:501] |

  @CreateATagWithSameName @Bug
  Scenario Outline: A user with a proper role should not be able to create a tag with the same name
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a tag with the same name that other already created
    Then the user should get a "<Status Line>" response
    And the user should see the response invalid and have a body
    And the user reviews that the response should be returned and have a body with the following values
      | code   | message   | data   |
      | <Code> | <Message> | <Data> |

    Examples:
      | User Role     | Status Line              | Code        | Message                                                        | Data         |
      | administrator | HTTP/1.1 400 Bad Request | term_exists | A term with the name provided already exists in this taxonomy. | [status:400] |
      | editor        | HTTP/1.1 400 Bad Request | term_exists | A term with the name provided already exists in this taxonomy. | [status:400] |

  @UnableToGetAllTags @Bug
  Scenario Outline: A user without a proper role should not be able to retrieve all tags
    Given the user is authenticated with "<User Role>" role
    When the user tries to retrieve all tags list
    Then the user should get a "<Status Line>" response
    And the user should see the response invalid and have a body
    And the user should see the response returned and have a body with the following values
      | code   | message   | data   |
      | <Code> | <Message> | <Data> |

    Examples:
      | User Role   | Status Line            | Code                 | Message                                              | Data         |
      | author      | HTTP/1.1 403 Forbidden | rest_cannot_retrieve | Sorry, you are not allowed to get tags as this user. | [status:403] |
      | contributor | HTTP/1.1 403 Forbidden | rest_cannot_retrieve | Sorry, you are not allowed to get tags as this user. | [status:403] |
      | subscriber  | HTTP/1.1 403 Forbidden | rest_cannot_retrieve | Sorry, you are not allowed to get tags as this user. | [status:403] |

  @UnableCreateATag @Bug
  Scenario Outline: A user without a proper role should not be able to create a tag
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a tag
    Then the user should get a "<Status Line>" response
    And the user should see the response invalid and have a body
    And the user reviews that the response should be returned and have a body with the following values
      | code   | message   | data   |
      | <Code> | <Message> | <Data> |

    Examples:
      | User Role     | Status Line            | Code               | Message                                                      | Data         |
      | subscriber    | HTTP/1.1 403 Forbidden | rest_cannot_create | Sorry, you are not allowed to create terms in this taxonomy. | [status:403] |
      | author        | HTTP/1.1 403 Forbidden | rest_cannot_create | Sorry, you are not allowed to create terms in this taxonomy. | [status:403] |
      | contributor   | HTTP/1.1 403 Forbidden | rest_cannot_create | Sorry, you are not allowed to create terms in this taxonomy. | [status:403] |





