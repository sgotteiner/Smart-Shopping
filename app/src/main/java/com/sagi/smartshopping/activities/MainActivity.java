package com.sagi.smartshopping.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.fragments.HomepageFragment;
import com.sagi.smartshopping.fragments.PostFragment;

public class MainActivity extends AppCompatActivity implements
        HomepageFragment.OnFragmentInteractionListener
        , PostFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        showFragment(new HomepageFragment());
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.frameLayoutContainerMain, fragment)
                .commit();
    }
}
