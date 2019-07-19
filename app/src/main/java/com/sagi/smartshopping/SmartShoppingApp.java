package com.sagi.smartshopping;

import android.app.Application;
import android.content.Context;

public class SmartShoppingApp extends Application {

    private static Context iContext;

    @Override
    public void onCreate() {
        super.onCreate();
        iContext = this;
    }

    public static Context getContext() {
        return iContext;
    }
}
