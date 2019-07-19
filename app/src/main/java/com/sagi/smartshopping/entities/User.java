package com.sagi.smartshopping.entities;

import com.sagi.smartshopping.utilities.Utils;

import java.io.Serializable;

/**
 * Created by User on 21/09/2018.
 */

public class User implements Serializable {

    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private long mBirthDay;
    private long mLastTimeSeen;
    private boolean mIsManagerApp = false;



    public boolean ismIsManagerApp() {
        return mIsManagerApp;
    }

    public  String textEmailForFirebase() {
       return  Utils.textEmailForFirebase(mEmail);
    }

    public User(String firstName, String lastName, String email, long birthDay, long lastTimeSeen, boolean isManagerApp) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mEmail = email;
        this.mBirthDay = birthDay;
        this.mLastTimeSeen = lastTimeSeen;
        this.mIsManagerApp = isManagerApp;
    }


    public User(String firstName, String lastName, String email, long birthDay, long lastTimeSeen) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mEmail = email;
        this.mBirthDay = birthDay;
        this.mLastTimeSeen = lastTimeSeen;
    }

    public User() {
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public long getBirthDay() {
        return mBirthDay;
    }

    public void setBirthDay(long mBirthDay) {
        this.mBirthDay = mBirthDay;
    }

    public long getLastTimeSeen() {
        return mLastTimeSeen;
    }
}
