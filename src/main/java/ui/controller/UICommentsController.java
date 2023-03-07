package ui.controller;

public class UICommentsController {
    private String idComment;
    private String postId;
    private String postTitle;
    private String postLink;
    private String commentUser;

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
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

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }
}

