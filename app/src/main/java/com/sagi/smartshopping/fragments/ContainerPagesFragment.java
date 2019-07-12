package com.sagi.smartshopping.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.adapters.AdapterPostsInRecyclerCategories;
import com.sagi.smartshopping.adapters.AdapterViewPagerPages;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.interfaces.IContainerPagesFragment;
import com.sagi.smartshopping.interfaces.IPostFragment;

public class ContainerPagesFragment extends Fragment implements IContainerPagesFragment {

    private ViewPager mViewPagerPages;
    private OnFragmentInteractionListener mListener;

    public ContainerPagesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_container_pages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPagerPages=view.findViewById(R.id.viewPagerPages);

        AdapterViewPagerPages adapterViewPagerPages=new AdapterViewPagerPages(getFragmentManager());
        mViewPagerPages.setAdapter(adapterViewPagerPages);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            mListener.registerEventFromMain(this);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.registerEventFromMain(null);
        mListener = null;
    }

    @Override
    public void switchToPostFragment() {
        mViewPagerPages.setCurrentItem(1);
    }

    @Override
    public void switchToSpecificPostsFragment() {
        mViewPagerPages.setCurrentItem(2);
    }

    public interface OnFragmentInteractionListener {
        void registerEventFromMain(IContainerPagesFragment iContainerPagesFragment);

    }
}
