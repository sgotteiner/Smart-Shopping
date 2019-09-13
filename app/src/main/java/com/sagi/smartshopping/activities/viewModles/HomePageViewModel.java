package com.sagi.smartshopping.activities.viewModles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sagi.smartshopping.activities.reposetories.PostRepository;
import com.sagi.smartshopping.entities.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePageViewModel extends ViewModel {

    private PostRepository mPostRepository = PostRepository.getInstance();
    private MutableLiveData<HashMap<String, List<Post>>> mHashMapAllPostsCategories = mPostRepository.getHashMapAllPostsCategories();


    public LiveData<HashMap<String, List<Post>>> getHashMapAllPostsCategories() {
        return mHashMapAllPostsCategories;
    }

    public void getAllPosts() {
        mPostRepository.loadAllPosts();
    }

    public String[] loadAllCategoriesTitle() {
        return mPostRepository.loadAllCategoriesTitle();
    }

    public void setCategories(String[] categories) {
        mPostRepository.setCategories(categories);
    }

    public ArrayList<String> getArrCategoriesWithPosts() {
        return mPostRepository.getArrCategoriesWithPosts();
    }


}
