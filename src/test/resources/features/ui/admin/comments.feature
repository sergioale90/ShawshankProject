@Comments @UI
Feature: Comments

  @UserComments
  Scenario Outline: A user with a proper role should be able to create a comment
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Post page
    And the user comments "<Comment User>" on the post page
    Then the user should review that his comment "<Comment User>" was added

    Examples: 
      | User Role   | Comment User                      |
      | editor      | This is my comment as editor      |
      | author      | This is my comment as author      |
      | contributor | This is my comment as contributor |
      | subscriber  | This is my comment as subscriber  |

  @AdminTrashComment
  Scenario Outline: A user with a proper role should be able to move a comment to trash
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user moves a comment to trash using the trash link on the Comments table
    Then the user should see that the comment was moved to trash successfully

    Examples: 
      | User Role     |
      | administrator |
      | editor        |

  @AdminApproveComment
  Scenario Outline: A user with a proper role should be able to approve a comment
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user moves a comment to approve using the approve link on the Comments table
    Then the user should see that the comment was moved to approve successfully

    Examples: 
      | User Role     |
      | administrator |

  @AdminSpamComment
  Scenario Outline: A user with a proper role should be able to move a comment to spam
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user moves a comment to spam using the spam link on the Comments table
    Then the user should see that the comment was moved to spam successfully

    Examples: 
      | User Role     |
      | administrator |
      | editor        |

  @AdminReplyComment
  Scenario Outline: A user with a proper role should be able to reply a comment
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user moves a comment to reply using the reply link on the Comments table
    Then the user should see a new comment created in the comments table

    Examples: 
      | User Role     |
      | administrator |
      | editor        |

  @AdminQuickEditComment
  Scenario Outline: A user with a proper role should be able to go a quick edit comment
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user moves a comment to edit using the quick edit link on the Comments table
    Then the user should see that the comment was updated successfully

    Examples: 
      | User Role     |
      | administrator |
      | editor        |

  @AdminEditComment
  Scenario Outline: A user with a proper role should be able to edit a comment
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user goes to a edit from the edit link on the Comments table
    And the user replace the old comment with a new one
    Then the user should see that the comment was updated successfully

    Examples: 
      | User Role     |
      | administrator |
      | editor        |

  @AdminFindComment
  Scenario Outline: A user with a proper role should be able to find a comment
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user searches for "<Comment>" with the Search Comments option
    Then the user should see the result of his comment searched

    Examples: 
      | User Role     | Comment          |
      | administrator | Message from api |

  @AdminEditAuthorAndEmail
  Scenario Outline: A user with a proper role should be able to edit an author and email of a comment
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user goes to a edit from the edit link on the Comments table
    And the user replace the old author and email with a new one
    Then the user should see that the author and email was updated successfully

    Examples: 
      | User Role     |
      | administrator |

  @AdminMoveCommentToTrashFromEdit
  Scenario Outline: A user with a proper role should be able to move a comment to trash from email
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Comments page
    And the user goes to a edit from the edit link on the Comments table
    And the user moves the comment to trash using the trash link
    Then the user should see that the comment was moved to trash successfully

    Examples: 
      | User Role     |
      | administrator |
