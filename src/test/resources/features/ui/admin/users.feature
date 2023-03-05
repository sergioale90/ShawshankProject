@Users @UI
Feature: Users

  @CreateAUser
  Scenario Outline: A user with proper role should be able to create a User
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to User page using the left side menu bar
    And the user goes to New Post page using the Add New button on Posts page
    And the user publishes a new Post with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been published successfully

    Examples:
      | User Role     | Title                  | Content                           |
      | administrator | Insert Funny Title     | Insert Funny Comment              |
      | author        | Eating People is Wrong | BTW, this is title of a real book |
      | editor        | I Yam What I Yam       | And Dats What I Yam!              |