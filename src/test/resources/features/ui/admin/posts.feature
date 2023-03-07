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
    And the user creates a new Draft Post with the following params
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been saved as a Draft successfully

    Examples:
      | User Role     | Title                                                          | Content                           |
      | administrator | Insert Funny Title                                             | Insert Funny Comment              |
      | author        | Eating People is Wrong                                         | BTW, this is title of a real book |
      | contributor   | Eating People well don't do that                               | As simple as that                 |
      | editor        | Just because it's free doesn't mean that I will give it to you | And that's how we met             |

  @OpenPublishPost
  Scenario Outline: A user with a proper role should be able to open a Published Post with the title
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
      | User Role     | Title                                                          | Content               |
      | administrator | My Homer is not a Communist                                    | He may be a liar      |
      | editor        | Just because it's free doesn't mean that I will give it to you | And that's how we met |

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
      | User Role     | Title                                                          | Content               |
      | administrator | My Homer is not a Communist                                    | He may be a liar      |
      | editor        | Just because it's free doesn't mean that I will give it to you | And that's how we met |

  @EditOwnPublishPost
  Scenario Outline: A user with proper role should be able to edit and publish his own Post
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a post with the following params
      | content                 | title            | excerpt            |
      | Test U WUI Post Content | Test U WUI Title | Test U WUI Excerpt |
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Posts page using the left side menu bar
    And the user opens the Post using the post title link on the Post page table
    And the user edits and publishes the Post with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been updated successfully

    Examples:
      | User Role     | Title                                                          | Content                                         |
      | administrator | My Homer is not a Communist                                    | He may be a liar                                |
      | author        | Doctor say Nordberg has 50-50 chance of living                 | Though there's only a 10 percent chance of that |
      | editor        | Just because it's free doesn't mean that I will give it to you | And that's how we met                           |

  @EditOwnDraftPost
  Scenario Outline: A user with proper role should be able to edit and publish his own draft Post
    Given the user is authenticated with "<User Role>" role
    When the user makes a request to create a draft post with the following params
      | content                        | title                   | excerpt                   |
      | Test Draft RU WUI Post Content | Test Draft RU WUI Title | Test Draft RU WUI Excerpt |
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to Posts page using the left side menu bar
    And the user opens the Post using the post title link on the Post page table
    And the user edits a Draft Post with the following params
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been saved as a Draft successfully

    Examples:
      | User Role     | Title                                                          | Content                                         |
      | administrator | My Homer is not a Communist                                    | He may be a liar                                |
      | author        | Yeah 4:00 am and I'm still going                               | I will need a coffee                            |
      | contributor   | Doctor say Nordberg has 50-50 chance of living                 | Though there's only a 10 percent chance of that |
      | editor        | Just because it's free doesn't mean that I will give it to you | And that's how we met                           |

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

  @CreatePublishPost
  Scenario Outline: A user with proper role should be able to create and publish a Post with the submenu
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to New Post page using the popup submenu button from the left side menu bar
    And the user publishes a new Post with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been published successfully

    Examples:
      | User Role     | Title                                                          | Content                           |
      | administrator | Insert Funny Title                                             | Insert Funny Comment              |
      | author        | Eating People is Wrong                                         | BTW, this is title of a real book |
      | editor        | Just because it's free doesn't mean that I will give it to you | And that's how we met             |

  @CreateDraftPost
  Scenario Outline: A user with proper role should be able to create a Draft Post with the submenu
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to New Post page using the popup submenu button from the left side menu bar
    And the user creates a new Draft Post with the following params
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been saved as a Draft successfully

    Examples:
      | User Role     | Title                            | Content                      |
      | administrator | Insert Funny Title               | Insert Funny Comment         |
      | author        | Yeah 4:00 am and I'm still going | I will need a coffee         |
      | contributor   | Eating People is Wrong twice     | now think about what you did |
      | editor        | I Yam What I Yam                 | And Dats What I Yam!         |

  @CreatePublishPost
  Scenario Outline: A user with proper role should be able to create and publish a Post in the new post page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to New Post page
    And the user publishes a new Post with the following values
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been published successfully

    Examples:
      | User Role     | Title                                                          | Content                           |
      | administrator | Insert Funny Title                                             | Insert Funny Comment              |
      | author        | Eating People is Wrong                                         | BTW, this is title of a real book |
      | editor        | Just because it's free doesn't mean that I will give it to you | And that's how we met             |

  @CreateDraftPost
  Scenario Outline: A user with proper role should be able to create a Draft Post in the new post page
    Given the user is logged in to Admin page with "<User Role>" role
    When the user goes to New Post page
    And the user creates a new Draft Post with the following params
      | title   | content   |
      | <Title> | <Content> |
    Then the Post should have been saved as a Draft successfully

    Examples:
      | User Role     | Title                                                          | Content                           |
      | administrator | Insert Funny Title                                             | Insert Funny Comment              |
      | author        | Eating People is Wrong                                         | BTW, this is title of a real book |
      | contributor   | Eating People is Wrong twice                                   | now think about what you did      |
      | editor        | Just because it's free doesn't mean that I will give it to you | And that's how we met             |