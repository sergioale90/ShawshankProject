@Pages @UI
Feature: Pages

  @CreatePublishPage
  Scenario Outline: A user with the proper role should be able to create and publish a Page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user goes to New Page page using the Add New button on Pages page
    And the user publishes a new Page with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the user reviews that the Page should have been published successfully

    Examples:
      | User Role       | Title                  | Content                           |
      | administrator   | Test GPage Admin       | Testing content page as admin     |
      | editor          | Test GPage Editor      | Testing content page as editor    |

  @CreateDraftPage
  Scenario Outline: A user with the proper role should be able to create a draft Page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user goes to New Page page using the Add New button on Pages page
    And the user save as draft a new Page with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the user reviews that the Page should have been saved successfully

    Examples:
      | User Role       | Title                        | Content                                 |
      | administrator   | Test GPage Draft Admin       | Testing content draft page as admin     |
      | editor          | Test GPage Draft Editor      | Testing content draft page as editor    |

  @EditPublishPage
  Scenario Outline: A user with proper role should be able to edit and publish a draft Page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user opens the Page using the page title link on the Page page table
    And the user edits and publishes the Page with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the user reviews that the Page should have been published successfully

    Examples:
      | User Role         | Title                          | Content                                   |
      | administrator     | Test GPage Edit Admin          | Testing edit content page as admin        |
      | editor            | Test GPage Edit Editor         | Testing edit content page as editor       |

  @UpdatePublishPage
  Scenario Outline: A user with proper role should be able to update a publish Page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user opens the Page using the page title link on the Page page table
    And the user updates the Page with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the user reviews that the Page should have been updated successfully

    Examples:
      | User Role         | Title                                     | Content                                     |
      | administrator     | Test GPage Update Admin                   | Testing update content page as admin        |
      | editor            | Test GPage Update Editor                  | Testing update content page as editor       |

  @DeleteDraftPage
  Scenario Outline: A user with proper role should be able to carry a draft Page to trash
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user moves a Page to trash using the trash link on the Page page table
    Then the user reviews that the Page should have been moved to trash successfully

    Examples:
      | User Role     |
      | administrator |
      | editor        |

  @DeleteDraftPagePermanently
  Scenario Outline: A user with proper role should be able to delete a draft page permanently
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user moves a Page to trash using the trash link on the Page page table
    Then the user reviews that the Page should have been moved to trash successfully
    And the user deletes the Page using the Delete Permanently link on the Page page table
    And the user reviews that the Page should have been delete permanently

    Examples:
      | User Role     |
      | administrator |
      | editor        |

  @RestoreDraftPage
  Scenario Outline: A user with proper role should be able to restore a draft page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user moves a Page to trash using the trash link on the Page page table
    Then the user reviews that the Page should have been moved to trash successfully
    And the user restores the Page using the Restore link on the Page page table
    And the user reviews that the Page should have been restored

    Examples:
      | User Role     |
      | administrator |
      | editor        |

  @FindValidPage
  Scenario Outline: A user with proper role should be able to find a correct title page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user searches a valid title page
    Then the user should see the title page found

    Examples:
      | User Role     |
      | administrator |
      | editor        |

  @FindNoValidPage
  Scenario Outline: A user with the proper role should not be able to find an incorrect title page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user searches an invalid title page "<Invalid Page Title>"
    Then the user should see a "<Expected Result>" message

    Examples:
      | User Role     | Invalid Page Title  | Expected Result        |
      | administrator | PageInvalidTitle    | No pages found.        |
      | editor        | PageInvalidTitle    | No pages found.        |

  @PagePublishSwitchDraft @Bug @ST-65
  Scenario Outline: A user with the proper role should be able to switch to draft a Page published
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Pages using the left side menu bar
    And the user switches to draft a page published
    And the user presses OK option of the message displayed to continue with the process
    Then the user should review that the page was moved to draft status

    Examples:
      | User Role     |
      | administrator |
      | editor        |







