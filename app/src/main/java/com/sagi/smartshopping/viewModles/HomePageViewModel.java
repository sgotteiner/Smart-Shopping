package com.sagi.smartshopping.viewModles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sagi.smartshopping.reposetories.database.post.PostRepository;
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

    public String[] loadAllCategoriesTitle() {
        return mPostRepository.getAllCategoriesTitle();
    }

    public ArrayList<String> getArrCategoriesWithPosts() {
        return mPostRepository.getArrCategoriesWithPosts();
    }

}
