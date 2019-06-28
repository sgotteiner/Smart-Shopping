package com.sagi.smartshopping.entities;

import java.sql.Timestamp;

public class Post {

    private Timestamp date;
    private String username;
    private String userProfile;
    private String key;
    private String postBody;

    public Post(){}

    public Post(Timestamp date, String username, String userProfile, String key, String postBody) {
        this.date = date;
        this.username = username;
        this.userProfile = userProfile;
        this.key = key;
        this.postBody = postBody;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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
