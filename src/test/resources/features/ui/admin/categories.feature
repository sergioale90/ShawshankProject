@Categories @UI
Feature: Categories

  @CreatePublishPage
  Scenario Outline: A user with the proper role should be able to create and publish a Page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user hover over of Pages menu in the left side bar menu and click on the categories button
    Then the user can access to the categories page
    Examples:
      | User Role       |
      | administrator   |
      | editor          |