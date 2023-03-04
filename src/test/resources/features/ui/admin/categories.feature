@Categories @UI
Feature: Categories

  @EnterCategoriesPage
  Scenario Outline: A user with the proper role should be able to enter in the categories Page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    Then the user can access to the categories page
    Examples:
      | User Role       |
      | administrator   |
      | editor          |

  @CantEnterCategoriesPage
  Scenario Outline: A user without the proper role shouldn't be able to enter in the categories Page
    Given the user is logged in to Admin page with "<User Role>" role
    Then the user hover over of Posts menu in the left side bar menu can't see the categories button
    Examples:
      | User Role   |
      | author      |
      | contributor |

  @CantEnterCategoriesPage
  Scenario Outline: A user with the subscriber role shouldn't be able to enter in the categories Page
    Given the user is logged in to Admin page with "<User Role>" role
    Then the user doesn't have the option of Post Menu in the left side bar
    Examples:
      | User Role   |
      | subscriber  |