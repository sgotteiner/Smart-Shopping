package com.sagi.smartshopping.entities;

import java.sql.Timestamp;

public class Post {

    private long timestamp;
    private String username;
    private String userProfile;
    private String key;
    private String postBody;

    public Post(){}

    public Post(long timestamp, String username, String key, String postBody) {
        this.timestamp = timestamp;
        this.username = username;
        this.key = key;
        this.postBody = postBody;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}
