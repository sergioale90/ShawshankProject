@UICategories @UI
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
    And the category is created with all values correctly
    Examples:
      | User Role       |      message    |
      | administrator   | Category added. |
      | editor          | Category added. |


  @NotCreateANewCategory
  Scenario Outline: A user with the proper role shouldn't be able to create a new category without name
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    And the user can create a new category without name
    Then the page displays a error "<message>"
    Examples:
      | User Role       |              message              |
      | administrator   | A name is required for this term. |
      | editor          | A name is required for this term. |

  @EnterToEditCategory
  Scenario Outline: A user with the proper role should enter to edit category page to edit a category
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    And the user hover over of one category created previously and enter to edit category page using the edit label
    Then the user redirects to the Edit Category Page
    Examples:
      | User Role       |
      | administrator   |
      | editor          |

  @EditCategorySuccessfully
  Scenario Outline: A user with the proper role should edit a existent category
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    And the user hover over of one category created previously and enter to edit category page using the edit label
    Then the user redirects to the Edit Category Page
    When the user edit the fields of the category and update the category information
    Then a message confirm that the category was updated with the new information
    When the user return to the categories page
    Then the information of the category was updated correctly
    Examples:
      | User Role       |
      | administrator   |
      | editor          |

  @EditCategoryUnsuccessfully @bug
  Scenario Outline: A user with the proper role shouldn't edit a existent category leaving the slug field in blank
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    And the user hover over of one category created previously and enter to edit category page using the edit label
    Then the user redirects to the Edit Category Page
    When the user erase the information of the slug field and update the category
    Then the page displays a error and the category is not update
    Examples:
      | User Role       |
      | administrator   |
      | editor          |

  @QuickEditCategory
  Scenario Outline: A user with the proper role should be able to quick edit a category
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    And the user hover over of one category created previously edit category page using the quick label
    Then the page displays the form to edit the category
    When the user edit the name and slug information of the category
    Then the information of the category was updated correctly
    Examples:
      | User Role       |
      | administrator   |
      | editor          |

  @SearchACategory
  Scenario Outline: A user with the proper role should be able to search a category using the search box
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    And the user can enter a word to search in the search box
    Then the list of categories display the coincidences
    Examples:
      | User Role       |
      | administrator   |
      | editor          |

  @DeleteCategorySuccessfully
  Scenario Outline: A user with the proper role should delete a existent category using the edit page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    And the user hover over of one category created previously and enter to edit category page using the edit label
    Then the user redirects to the Edit Category Page
    When the user delete the category using de delete button and accept the alert
    And the category was deleted successfully
    Examples:
      | User Role       |
      | administrator   |
      | editor          |

  @DeleteCategorySuccessfully
  Scenario Outline: A user with the proper role should delete a existent category directly in the categories page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    And the user hover over of one category created previously and enter to edit category page using the delete label
    And the category was deleted successfully
    Examples:
      | User Role       |
      | administrator   |
      | editor          |

  @DeleteCategorySuccessfully
  Scenario Outline: A user with the proper role should delete a existent category using the bulk actions
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Posts menu in the left side bar menu and click on the categories button
    And the user can select a category using the checkbox on the left side of the category
    And the user select delete in the bulk action menu and select apply
    And the category was deleted successfully and page display the delete message
    Examples:
      | User Role       |
      | administrator   |
      | editor          |