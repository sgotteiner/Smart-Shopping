package com.sagi.smartshopping.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sagi.smartshopping.reposetories.database.DatabaseConstant;

import java.io.Serializable;

@Entity(tableName = DatabaseConstant.POST_TABLE)
public class Post implements Serializable {

    @NonNull
    @PrimaryKey()
    private String mKey;
    private long mTimestamp;
    private String mUsername;
    private String mPostBody;
    private String mCategory;
    private float mPrice;
    private String mTitle;
    private int mLikes=0;
    private String mCityLocation;

    public String getShopKey() {
        return mShopKey;
    }

    public void setShopKey(String mShopKey) {
        this.mShopKey = mShopKey;
    }

    private String mShopKey;


    public String getCityLocation() {
        return mCityLocation;
    }

    public void setCityLocation(String cityLocation) {
        this.mCityLocation = cityLocation;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(int mLikes) {
        this.mLikes = mLikes;
    }

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

    @Override
    public String toString() {
        return "Post{" +
                "mKey='" + mKey + '\'' +
                ", mTimestamp=" + mTimestamp +
                ", mUsername='" + mUsername + '\'' +
                ", mPostBody='" + mPostBody + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mPrice=" + mPrice +
                ", mTitle='" + mTitle + '\'' +
                ", mLikes=" + mLikes +
                '}';
    }
}
