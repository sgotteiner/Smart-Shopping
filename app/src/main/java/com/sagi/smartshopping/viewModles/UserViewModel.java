package com.sagi.smartshopping.viewModles;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

import com.sagi.smartshopping.entities.User;
import com.sagi.smartshopping.reposetories.preferance.SharedPreferencesHelper;
import com.sagi.smartshopping.utilities.Utils;

public class UserViewModel extends ViewModel {

    private User mUser;
    private long mDateBirthDay = -1;
    private Bitmap mBitmapProfile = null;

    public User getUser() {
        if (mUser == null) {
            mUser = SharedPreferencesHelper.getInstance().getUser();
            mDateBirthDay =mUser.getBirthDay();
        }
        return mUser;
    }

    public boolean isValid(String name, String lastName, Context context) {
        return Utils.isValid(mUser.getEmail(),name,lastName, mDateBirthDay,context);
    }

    public Bitmap getImageProfile() {
        return mBitmapProfile;
    }

    public long getDateBirthDay() {
        return mDateBirthDay;
    }

    public void setDateBirthday(long timeStampFromDate) {
        mDateBirthDay =timeStampFromDate;
    }

    public void setImageProfile(Bitmap imageGallery) {
        mBitmapProfile=imageGallery;
    }
}
