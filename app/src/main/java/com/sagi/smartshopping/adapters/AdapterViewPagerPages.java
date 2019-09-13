package com.sagi.smartshopping.adapters;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.fragments.CreatePostFragment;
import com.sagi.smartshopping.fragments.HomepageFragment;
import com.sagi.smartshopping.fragments.PostFragment;
import com.sagi.smartshopping.fragments.SpecificPostsFragment;
import com.sagi.smartshopping.fragments.UserFragment;

public class AdapterViewPagerPages extends FragmentStatePagerAdapter {

    public static final int HOME_PAGE_POS = 0;
    public static final int POST_PAGE_POS = 1;
    public static final int SPECIFIC_PAGE_POS = 2;
    public static final int CREATE_PAGE_POS = 3;
    public static final int USER_PAGE_POS = 4;

    private Post mPost=null;

    public AdapterViewPagerPages(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("tag", "getItem");
        switch (position) {
            case HOME_PAGE_POS:
                return new HomepageFragment();
            case POST_PAGE_POS:
                return new PostFragment();
            case SPECIFIC_PAGE_POS:
                return new SpecificPostsFragment();
            case CREATE_PAGE_POS:
                return new CreatePostFragment();
            case USER_PAGE_POS:
                return new UserFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    public void setPost(Post post) {
        this.mPost = post;
        notifyDataSetChanged();
    }
}
