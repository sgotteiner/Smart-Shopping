package com.sagi.smartshopping.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.reposetories.preferance.SharedPreferencesHelper;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION = 2;
    private TextView mTxtHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mTxtHeader = findViewById(R.id.txtHeader);



        if (isNeedAskPermission())
            requestPermissions();
        else
            handleShowingActivity();
    }

    private void requestPermissions() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LOCATION);
    }

    private boolean isNeedAskPermission() {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean locationPermissionDenied = false;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                locationPermissionDenied = true;
            }
        }
        if (locationPermissionDenied) {
            finish();
        } else {
            chooseScreen();
        }
    }


    private void handleShowingActivity() {
        final int DELAY_TIME = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chooseScreen();
            }
        }, DELAY_TIME);
    }

    private void chooseScreen() {
        if (SharedPreferencesHelper.getInstance( ).isAlreadyLogin())
            showActivity(MainActivity.class);
        else
            showActivity(RegisterLoginActivity.class);

    }

    private void showActivity(Class<?> aClass) {
        Intent intent = new Intent(SplashActivity.this, aClass);
        startActivity(intent);
        finish();
    }


}
