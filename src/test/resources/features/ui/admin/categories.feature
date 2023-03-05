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

  @CreateANewCategory
  Scenario Outline: A user with the proper role should be able to create a new category
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    And the user can create a new category only with a category name
    Then the page displays a successful "<message>"
    And the category was created successfully with a name and the slug value is set by default
    Examples:
      | User Role       |      message    |
      | administrator   | Category added. |
      | editor          | Category added. |

  @CreateANewCategory
  Scenario Outline: A user with the proper role should be able to create a new category with all params
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    And the user can create a new category with all params
    Then the page displays a successful "<message>"
    And the page is created with all values correctly
    Examples:
      | User Role       |      message    |
      | administrator   | Category added. |
      | editor          | Category added. |