package com.sagi.smartshopping.activities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.sagi.smartshopping.R;
import com.sagi.smartshopping.reposetories.preferance.SharedPreferencesHelper;
import com.sagi.smartshopping.services.LocationService;
import com.sagi.smartshopping.utilities.CsvReader;

import java.util.ArrayList;


public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION = 2;
    private ImageView mImgIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mImgIcon = findViewById(R.id.imgIcon);


        //https://www.journaldev.com/9481/android-animation-example
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        animation.setDuration(2000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mImgIcon.startAnimation(animation);



        showAnotherAnimation();


        Intent intent = new Intent(this, LocationService.class);
        startService(intent);

        csvReader();

        if (isNeedAskPermission())
            requestPermissions();
        else
            handleShowingActivity();
    }

    private void showAnotherAnimation() {

        mImgIcon.setBackgroundResource(R.drawable.animation_images);
        AnimationDrawable rocketAnimation = (AnimationDrawable) mImgIcon.getBackground();
        rocketAnimation.start();
    }


    private void csvReader() {
//        final String FILE_NAME = "data_base_to_standart_users.csv";
//        final String FILE_NAME = "shopping_local_db.csv";
        final String FILE_NAME = "new_db.csv";

        new CsvReader(this, FILE_NAME, rows -> {
            Log.i("CsvReader", "csvReader: "+rows.size());
        }).execute();
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
        final int DELAY_TIME = 4000;
        new Handler().postDelayed(() -> chooseScreen(), DELAY_TIME);
    }

    private void chooseScreen() {
        if (SharedPreferencesHelper.getInstance().isAlreadyLogin())
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
