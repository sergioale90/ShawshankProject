@Pages @UI
Feature: Pages

  @CreatePublishPage
  Scenario Outline: A user with the proper role should be able to create and publish a Page
    Given the user logs in to the Admin page with the "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user goes to New Page page using the Add New button on Pages page
    And the user publishes a new Page with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the user reviews that the Page should have been published successfully

    Examples:
      | User Role       | Title                  | Content                           |
      | administrator   | Test GPage Admin       | Testing content page as admin     |
      #| editor          | Test GPage Editor      | Testing content page as editor    |

  # Verify that a Page with draft status can be created.
  @CreateDraftPage
  Scenario Outline: A user with the proper role should be able to create a draft Page
    Given the user logs in to the Admin page with the "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user goes to New Page page using the Add New button on Pages page
    And the user save as draft a new Page with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the user reviews that the Page should have been saved successfully

    Examples:
      | User Role       | Title                        | Content                                 |
      | administrator   | Test GPage Draft Admin       | Testing content draft page as admin     |
      #| editor          | Test GPage Draft Editor      | Testing content draft page as editor    |



  @EditPublishPage
  Scenario Outline: A user with proper role should be able to edit and publish a Page
    Given the user logs in to the Admin page with the "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user opens the Page using the page title link on the Page page table
    And the user edits and publishes the Page with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the user reviews that the Page should have been published successfully

    Examples:
      | User Role         | Title                                     | Content                                     |
      | administrator     | Test GPage Update Admin                   | Testing update content page as admin        |
      | editor            | Test GPage Update Editor                  | Testing update content page as editor       |

  @DeleteDraftPage
  Scenario Outline: A user with proper role should be able to carry a draft Page to trash
    Given the user logs in to the Admin page with the "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user moves a Page to trash using the trash link on the Page page table
    Then the user reviews that the Page should have been moved to trash successfully

    Examples:
      | User Role     |
      | administrator |
      | editor        |








