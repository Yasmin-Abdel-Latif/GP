package model;

public class FacebookPost {


    private String userID;
    private String postID;
    private String postContent;
    private String creationDate;
    private int read;

    public FacebookPost() {
        super();
    }

    public FacebookPost(String userID, String postID, String postContent, String creationDate, int read) {
        super();
        this.userID = userID;
        this.postID = postID;
        this.postContent = postContent;
        this.creationDate = creationDate;
        this.read = read;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "FacebookPost [userID=" + userID + ", postID=" + postID + ", postContent=" + postContent + ", creationDate=" + creationDate + ", read="
                + read + "]";
    }


}
