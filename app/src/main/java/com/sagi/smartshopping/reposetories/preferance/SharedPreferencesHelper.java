package com.sagi.smartshopping.reposetories.preferance;

import android.content.Context;
import android.content.SharedPreferences;

import com.sagi.smartshopping.SmartShoppingApp;
import com.sagi.smartshopping.entities.User;
import com.sagi.smartshopping.utilities.Utils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by User on 14/09/2018.
 */

public class SharedPreferencesHelper {

    private static SharedPreferences mPreferences;
    private static SharedPreferencesHelper mInstance;

    private final String IS_ALREADY_LOGIN = "IS_ALREADY_LOGIN";
    private static final String LAST_CITY_I_BEEN_THARE = "LAST_CITY_I_BEEN_THARE";
    private final String FIRST_NAME = "FIRST_NAME";
    private final String LAST_NAME = "LAST_NAME";
    private final String EMAIL = "EMAIL";
    private final String URL_PROFILE = "URL_PROFILE";
    private final String BIRTHDAY = "BIRTHDAY";
    private final String LAST_TIME_SEEN = "LAST_TIME_SEEN";
    private final String LAST_COUNT_REQUEST = "LAST_COUNT_REQUEST";
    private final String LAST_TIMESTAMP_REQUEST_POSTS = "LAST_TIMESTAMP_REQUEST_POSTS";
    private final String IS_MANAGER_APP = "IS_MANAGER_APP";

    private SharedPreferencesHelper(Context context) {
        final String SETTINGS_APP = "SETTINGS_APP";
        mPreferences = context.getSharedPreferences(SETTINGS_APP, MODE_PRIVATE);
    }

    public static SharedPreferencesHelper getInstance() {
        if (mInstance == null)
            mInstance = new SharedPreferencesHelper(SmartShoppingApp.getContext());

        return mInstance;
    }

    public boolean isAlreadyLogin() {
        return mPreferences.getBoolean(IS_ALREADY_LOGIN, false);
    }

    public int getLastCountRequest() {
        return mPreferences.getInt(LAST_COUNT_REQUEST, 0);
    }

    public void setIsAlreadyLogin(boolean isAlreadyLogin) {
        mPreferences.edit().putBoolean(IS_ALREADY_LOGIN, isAlreadyLogin).apply();
    }

    public User getUser() {
        String firstName = mPreferences.getString(FIRST_NAME, null);
        String lastName = mPreferences.getString(LAST_NAME, null);
        String email = mPreferences.getString(EMAIL, null);
        String urlProfile = mPreferences.getString(URL_PROFILE, null);
        long birthday = mPreferences.getLong(BIRTHDAY, -1);

        long lastTimeSeen = mPreferences.getLong(LAST_TIME_SEEN, -1);
        boolean isManagerApp = mPreferences.getBoolean(IS_MANAGER_APP, false);

        if (email == null || birthday == -1)
            return null;

        return new User(firstName, lastName, email, birthday, lastTimeSeen, isManagerApp);
    }

    public void setUser(User user) {
        mPreferences.edit().putString(FIRST_NAME, user.getFirstName()).apply();
        mPreferences.edit().putString(LAST_NAME, user.getLastName()).apply();
        mPreferences.edit().putString(EMAIL, user.getEmail()).apply();
        mPreferences.edit().putString(URL_PROFILE, user.getEmail()).apply();
        mPreferences.edit().putLong(BIRTHDAY, user.getBirthDay()).apply();
        mPreferences.edit().putLong(LAST_TIME_SEEN, user.getLastTimeSeen()).apply();
//        preferences.edit().putFloat(LEVEL,user.getLevelUser()).commit();
        mPreferences.edit().putBoolean(IS_MANAGER_APP, user.ismIsManagerApp()).apply();
    }

    public void setLastCountRequest(int total) {
        mPreferences.edit().putInt(LAST_COUNT_REQUEST, total).apply();
    }


    public void resetSharedPreferences() {
        mPreferences.edit().clear().apply();
    }

    public long getLastPostRequest() {
//        return 0;
//        //TODO back this after DB
       return mPreferences.getLong(LAST_TIMESTAMP_REQUEST_POSTS, Utils.getStartTimeStamp())+1;
    }
    public void setLastPostRequest(long timeStamp) {
        mPreferences.edit().putLong(LAST_TIMESTAMP_REQUEST_POSTS,timeStamp).apply();
    }

    public void setLastCityIThere(String myCity) {
        mPreferences.edit().putString(LAST_CITY_I_BEEN_THARE, myCity).apply();
    }
    public String getLastCityIThere( ) {
        return mPreferences.getString(LAST_CITY_I_BEEN_THARE,"DEFAULT");
    }
}
