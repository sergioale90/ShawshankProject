@PostsUI @UI
Feature: Posts

  @CreatePublishPost
  Scenario Outline: A user with proper role should be able to create and publish a Post
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Posts page using the left side menu bar
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

  @CreateDraftPost
  Scenario Outline: A user with proper role should be able to create a Draft Post
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Posts page using the left side menu bar
    And the user goes to New Post page using the Add New button on Posts page
    And the user creates a new Draft Post with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been saved as a Draft successfully

    Examples:
      | User Role     | Title                  | Content                           |
      | administrator | Insert Funny Title     | Insert Funny Comment              |
      | author        | Eating People is Wrong | BTW, this is title of a real book |
      | editor        | I Yam What I Yam       | And Dats What I Yam!              |

  @OpenPublishPost
  Scenario Outline: A user with a proper role should be able to open a Published Post
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Posts page using the left side menu bar
    And the user opens the Post using the post title link on the Post page table
    Then the Post should have the correct info

    Examples:
      | User Role     |
      | administrator |
      | editor        |

  @OpenDraftPost
  Scenario Outline: A user with a proper role should be able to open a Draft Post
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Posts page using the left side menu bar
    And the user opens the Post using the post title link on the Post page table
    Then the Post should have the correct info

    Examples:
      | User Role     |
      | administrator |
      | editor        |

  @EditPublishPost
  Scenario Outline: A user with proper role should be able to edit and publish a Post
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Posts page using the left side menu bar
    And the user opens the Post using the post title link on the Post page table
    And the user edits and publishes the Post with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been updated successfully

    Examples:
      | User Role     | Title                                          | Content                                                                    |
      | administrator | My Homer is not a Communist                    | He may be a liar, a pig, an idiot, a Communist, but he is NOT a porn star! |
      | editor        | Doctor say Nordberg has 50-50 chance of living | Though there's only a 10 percent chance of that                            |

  @EditDraftPost
  Scenario Outline: A user with proper role should be able to edit and publish a Draft Post
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Posts page using the left side menu bar
    And the user opens the Post using the post title link on the Post page table
    And the user edits and publishes the Post with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been published successfully

    Examples:
      | User Role     | Title                                          | Content                                                                    |
      | administrator | My Homer is not a Communist                    | He may be a liar, a pig, an idiot, a Communist, but he is NOT a porn star! |
      | editor        | Doctor say Nordberg has 50-50 chance of living | Though there's only a 10 percent chance of that                            |

  @DeletePublishPost
  Scenario Outline: A user with proper role should be able to delete a Post
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Posts page using the left side menu bar
    And the user moves a Post to trash using the trash link on the Post page table
    Then the Post should have been moved to trash successfully

    Examples:
      | User Role     |
      | administrator |
      | editor        |

  @DeleteDraftPost
  Scenario Outline: A user with proper role should be able to delete a Draft Post
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Posts page using the left side menu bar
    And the user moves a Post to trash using the trash link on the Post page table
    Then the Post should have been moved to trash successfully

    Examples:
      | User Role     |
      | administrator |
      | editor        |