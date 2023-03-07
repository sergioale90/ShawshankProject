package ui.controller;

public class UICommentsController {
    private String id;
    private String postId;
    private String postTitle;
    private String postLink;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }


    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public String getPostLink() {
        return postLink;
    }
}

