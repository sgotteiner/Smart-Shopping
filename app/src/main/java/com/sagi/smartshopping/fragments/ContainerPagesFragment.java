package com.sagi.smartshopping.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.adapters.AdapterViewPagerPages;


public class ContainerPagesFragment extends Fragment  {

    private ViewPager mViewPagerPages;

    public ContainerPagesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_container_pages, container, false);
    }

    private AdapterViewPagerPages mAdapterViewPagerPages;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPagerPages=view.findViewById(R.id.viewPagerPages);
        mAdapterViewPagerPages=new AdapterViewPagerPages(getFragmentManager());
        mViewPagerPages.setAdapter(mAdapterViewPagerPages);
    }






}
