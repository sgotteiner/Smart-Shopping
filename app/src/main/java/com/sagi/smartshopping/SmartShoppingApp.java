package com.sagi.smartshopping;

import android.app.Application;
import android.content.Context;

import com.sagi.smartshopping.reposetories.database.post.PostRepository;

public class SmartShoppingApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        PostRepository.getInstance();
    }

    public static Context getContext() {
        return mContext;
    }
}
