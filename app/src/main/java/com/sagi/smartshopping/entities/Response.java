package com.sagi.smartshopping.entities;

public class Response {
    private String mKey;
    private String mKeyPost;
    private long mTimeStamp;
    private String mEmailUser;
    private String mUsername;

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    private String mBody;
    private String mImageUrl;


    public Response() {
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public String getKeyPost() {
        return mKeyPost;
    }

    public void setKeyPost(String keyPost) {
        this.mKeyPost = keyPost;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.mTimeStamp = timeStamp;
    }

    public String getEmailUser() {
        return mEmailUser;
    }

    public void setEmailUser(String emailUser) {
        this.mEmailUser = emailUser;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        this.mBody = body;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }
}
