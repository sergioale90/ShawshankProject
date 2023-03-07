@Comments @UI
Feature: Comments

  @UserComments
  Scenario Outline: A user with a proper role should be able to create a comment
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Post page
    And the user comments "<Comment User>" on the post page
    Then the user should review that his comment "<Comment User>" was added

    Examples:
      | User Role     | Comment User                        |
      | editor        | This is my comment as editor        |
      | author        | This is my comment as author        |
      | contributor   | This is my comment as contributor   |
      | subscriber    | This is my comment as subscriber    |

  @AdminTrashComment
  Scenario Outline: A user with a proper role should be able to move a comment to trash
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user moves a comment to trash using the trash link on the Comments table
    Then the user should see that the comment was moved to trash successfully

    Examples:
      | User Role           |
      | administrator       |
      | editor              |

  @AdminApproveComment
  Scenario Outline: A user with a proper role should be able to approve a comment
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user moves a comment to approve using the approve link on the Comments table
    Then the user should see that the comment was moved to approve successfully

    Examples:
      | User Role           |
      | administrator       |
      #| editor              |

  @AdminSpamComment
  Scenario Outline: A user with a proper role should be able to move a comment to spam
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user moves a comment to spam using the spam link on the Comments table
    Then the user should see that the comment was moved to spam successfully

    Examples:
      | User Role           |
      | administrator       |
      | editor              |




