package com.sagi.smartshopping.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.sagi.smartshopping.R;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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

        boolean locationPermissionDenied=false;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                  locationPermissionDenied = true;
            }
        }
        if (locationPermissionDenied){
            finish();
        }else {
            showMainActivity();
        }
    }



    private void handleShowingActivity() {
        final int DELAY_TIME = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showMainActivity();
            }
        }, DELAY_TIME);
    }

    private void showMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
