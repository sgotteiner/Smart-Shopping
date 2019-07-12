package com.sagi.smartshopping.entities;

import java.io.Serializable;

public class Post implements Serializable {

    private long mTimestamp;
    private String mUsername;
    private String mPostBody;
    private String mCategory;
    private String mKey;
    private float mPrice;
    private String mTitle;

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float mPrice) {
        this.mPrice = mPrice;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Post(long mTimestamp, String mUsername, String mPostBody, String mCategory, String mKey, float mPrice, String mTitle) {
        this.mTimestamp = mTimestamp;
        this.mUsername = mUsername;
        this.mPostBody = mPostBody;
        this.mCategory = mCategory;
        this.mKey = mKey;
        this.mPrice = mPrice;
        this.mTitle = mTitle;
    }

    public Post(){}

    public Post(long mTimestamp, String mUsername, String key, String mPostBody, String mCategory) {
        this.mTimestamp = mTimestamp;
        this.mUsername = mUsername;
        this.mPostBody = mPostBody;
        this.mCategory = mCategory;
        this.mKey = key;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }


    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public String getPostBody() {
        return mPostBody;
    }

    public void setPostBody(String mPostBody) {
        this.mPostBody = mPostBody;
    }
}
