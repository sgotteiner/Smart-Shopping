package com.sagi.smartshopping.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.sagi.smartshopping.entities.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by User on 14/09/2018.
 */

public class SharedPreferencesHelper {

    private static SharedPreferences mPreferences;
    private static SharedPreferencesHelper mInstance;

    private final String IS_ALREADY_LOGIN = "IS_ALREADY_LOGIN";
    private final String FIRST_NAME = "FIRST_NAME";
    private final String LAST_NAME = "LAST_NAME";
    private final String EMAIL = "EMAIL";
    private final String URL_PROFILE = "URL_PROFILE";
    private final String BIRTHDAY = "BIRTHDAY";
    private final String LAST_TIME_SEEN = "LAST_TIME_SEEN";
    private final String LAST_COUNT_REQUEST = "LAST_COUNT_REQUEST";
    private final String IS_MANAGER_APP = "IS_MANAGER_APP";

    private SharedPreferencesHelper(Context context) {
        final String SETTINGS_APP = "SETTINGS_APP";
        mPreferences = context.getSharedPreferences(SETTINGS_APP, MODE_PRIVATE);
    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedPreferencesHelper(context);

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
}
