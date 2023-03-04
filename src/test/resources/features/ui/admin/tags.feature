@Tags @UI
Feature: Tags

  @CreateTag
  Scenario Outline: A user with the proper role should be able to create a tag
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Tags page
    And the user creates a new tag with the following values
      | name   | slug   | description     |
      | <Name> | <Slug> | <Description>   |
    Then the user reviews that the Page should have been published successfully

    Examples:
      | User Role       | Name                | Slug                  | Description                          |
      | administrator   | Test Tag Admin      | testtagadmin          | Testing description tag as admin     |
      | editor          | Test Tag Editor     | testtagedit           | Testing description tag as editor    |
