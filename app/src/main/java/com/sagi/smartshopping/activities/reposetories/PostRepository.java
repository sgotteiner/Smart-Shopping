package com.sagi.smartshopping.activities.reposetories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.utilities.SharedPreferencesHelper;
import com.sagi.smartshopping.utilities.Utils;
import com.sagi.smartshopping.utilities.constant.FireBaseConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sagi.smartshopping.utilities.constant.GeneralConstants.TIME_STAMP_KEY;

public class PostRepository {
    private static final PostRepository ourInstance = new PostRepository();
    private DatabaseReference myRef;
    private ArrayList<Post> mAllPosts = new ArrayList<>();
    private String[] mCategories =null;
    private MutableLiveData<HashMap<String, List<Post>>> mHashMapAllPostsCategories = new MutableLiveData<>();
    private ArrayList<String> mArrCategoriesWithPosts = new ArrayList<>();


    public MutableLiveData<HashMap<String, List<Post>>> getHashMapAllPostsCategories() {
        return mHashMapAllPostsCategories;
    }

    public ArrayList<String> getArrCategoriesWithPosts() {
        return mArrCategoriesWithPosts;
    }

    public static PostRepository getInstance() {
        return ourInstance;
    }

    private PostRepository() {
        myRef = FirebaseDatabase.getInstance().getReference();
        mHashMapAllPostsCategories.setValue(new HashMap<String, List<Post>>());
    }

     public void loadAllPosts() {

        Query query = myRef.child(FireBaseConstant.POSTS_TABLE).orderByChild(TIME_STAMP_KEY).startAt(SharedPreferencesHelper.getInstance().getLastPostRequest()).endAt(System.currentTimeMillis()).limitToLast(50);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferencesHelper.getInstance().setLastPostRequest(System.currentTimeMillis());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    mAllPosts.add(post);
                }
                sortAllPostsByCategory();

                mHashMapAllPostsCategories.postValue(mHashMapAllPostsCategories.getValue());
             }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sortAllPostsByCategory() {
        String[] categoriesArr = loadAllCategoriesTitle();

        for (int i = 0; i < categoriesArr.length; i++) {
            ArrayList<Post> postsCategory = new ArrayList<>();
            for (int j = mAllPosts.size() - 1; j >= 0; j--) {
                if (mAllPosts.get(j).getCategory().equals(categoriesArr[i])) {
                    postsCategory.add(mAllPosts.get(j));
                }
            }
            if (!postsCategory.isEmpty()) {
                mArrCategoriesWithPosts.add(categoriesArr[i]);
                mHashMapAllPostsCategories.getValue().put(categoriesArr[i], postsCategory);
            }

        }
    }

    public String[] loadAllCategoriesTitle() {
        return mCategories;
    }

    public void setCategories(String[] categories) {
        this.mCategories=categories;
    }
}
