@Posts @UI
Feature: Posts

  @CreatePublishPost
  Scenario Outline: A user with proper role should be able to create and publish a Post
    Given the user logs in to the Admin page with the "<User Role>" role
    When I go to Posts page using the left side menu bar
    And I go to New Post page using the Add New button on Posts page
    And I publish a new Post with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been published successfully

    Examples:
     | User Role     | Title                  | Content                           |
     | administrator | Insert Funny Title     | Insert Funny Comment              |
     | author        | Eating People is Wrong | BTW, this is title of a real book |
     | editor        | I Yam What I Yam       | And Dats What I Yam!              |

  @EditPublishPost @Test
  Scenario Outline: A user with proper role should be able to edit and publish a Post
    Given the user logs in to the Admin page with the "<User Role>" role
    When I go to Posts page using the left side menu bar
    And I open the Post using the post title link on the Post page table
    And I edit and publish the Post with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been published successfully

    Examples:
      | User Role     | Title                                          | Content                                                                    |
      | administrator | My Homer is not a Communist                    | He may be a liar, a pig, an idiot, a Communist, but he is NOT a porn star! |
      | editor        | Doctor say Nordberg has 50-50 chance of living | Though there's only a 10 percent chance of that                            |

  @DeleteDraftPost
  Scenario Outline: A user with proper role should be able to delete a Post
    Given the user logs in to the Admin page with the "<User Role>" role
    When I go to Posts page using the left side menu bar
    And I move a Post to trash using the trash link on the Post page table
     Then the Post should have been moved to trash successfully

    Examples:
      | User Role     |
      | administrator |
      | editor        |