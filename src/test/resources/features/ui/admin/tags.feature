@Tags @UI
Feature: Tags

  @CreateTag
  Scenario Outline: A user with the proper role should be able to create a tag
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Tags page
    And the user creates a new tag with the following values
      | name   | slug   | description     |
      | <Name> | <Slug> | <Description>   |
    Then the user should review that the tag was created successfully

    Examples:
      | User Role       | Name                | Slug                  | Description                          |
      | administrator   | Test Tag Admin      | testtagadmin          | Testing description tag as admin     |
      | editor          | Test Tag Editor     | testtagedit           | Testing description tag as editor    |

  @OpenTag
  Scenario Outline: A user with a proper role should be able to open a Tag
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Tags page
    And the user opens the tag using the View link on the Tag page table
    Then the user should review that the Tag has the correct info

    Examples:
      | User Role     |
      | administrator |
      | editor        |

  @UpdateTag
  Scenario Outline: A user with proper role should be able to update a tag
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Tags page
    And the user opens the tag using the tag name link on the Tag page table
    And the user edits the tag with the following values
      | name   | slug   | description     |
      | <Name> | <Slug> | <Description>   |
    Then the user should review that the tag has been updated successfully

    Examples:
      | User Role       | Name                  | Slug                | Description                                 |
      | administrator   | TestTagUpdateAdmin    | testuptagadmin      | Testing update description tag as admin     |
      | editor          | TestTagUpdateEditor   | testuptagedit       | Testing update description tag as editor    |

  @DeleteTagFromEditPage
  Scenario Outline: A user with a proper role should be able to delete a tag from Edit tag page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Tags page
    And the user opens the tag using the tag name link on the Tag page table
    And the user deletes the tag using the Delete link
    Then the user should review that the Tag has been deleted

    Examples:
      | User Role     |
      | administrator |
      | editor        |

  @DeleteTag
  Scenario Outline: A user with a proper role should be able to delete a tag
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Tags page
    And the user deletes the tag using the Delete link on the Tag page table
    And the user should review that the Tag has been deleted

    Examples:
      | User Role     |
      | administrator |
      | editor        |

  @FindValidTag
  Scenario Outline: A user with proper role should be able to find a correct name tag
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Tags page
    And the user searches a valid name tag
    Then the user should review the name tag found

    Examples:
      | User Role     |
      | administrator |
      | editor        |

  @FindNotTag
  Scenario Outline: A user with proper role should not be able to find an incorrect name tag
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Tags page
    And the user searches a invalid tag name "<Invalid Tag Name>"
    Then the user should review a "<Expected Result>" message

    Examples:
      | User Role     | Invalid Tag Name    | Expected Result    |
      | administrator | noneexisttagadmin   | No tags found.     |
      | editor        | noneexisttagedit    | No tags found.     |

  @CreateTagSameNameAndSlug @Bug @ST-77
  Scenario Outline: A user with a proper role should not be able to create a tag with the same name and slug
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Tags page
    And the user creates a new tag with the same name and slug
    Then the user should review that an error "<Expected Result>" message is displayed

    Examples:
      | User Role       | Expected Result      |
      | administrator   | A term with the name provided already exists in this taxonomy.  |
      | editor          | A term with the name provided already exists in this taxonomy.  |


  @CreateTagSameName @Bug @ST-80
  Scenario Outline: A user with a proper role should not be able to create a tag with the same name
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Tags page
    And the user creates a new tag with the same name
    Then the user should review that an error "<Expected Result>" message is displayed
    Examples:
      | User Role       | Expected Result                                 |
      | administrator   | A tag with the name provided already exists     |
      | editor          | A tag with the name provided already exists     |
