package com.sagi.smartshopping.activities.reposetories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.utilities.SharedPreferencesHelper;
import com.sagi.smartshopping.utilities.constant.FireBaseConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sagi.smartshopping.utilities.constant.GeneralConstants.TIME_STAMP_KEY;

public class PostRepository {
    private static final PostRepository ourInstance = new PostRepository();
    private DatabaseReference myRef;
    private ArrayList<Post> mAllPosts = new ArrayList<>();
    private String[] mCategories = null;
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
        dataSnapshot();


    }

    private void dataSnapshot() {
        myRef.child(FireBaseConstant.POSTS_TABLE).orderByChild(TIME_STAMP_KEY).startAt(SharedPreferencesHelper.getInstance().getLastPostRequest()).endAt(System.currentTimeMillis()).limitToLast(50).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final long size = dataSnapshot.getChildrenCount();

                Query query = myRef.child(FireBaseConstant.POSTS_TABLE).orderByChild(TIME_STAMP_KEY).startAt(SharedPreferencesHelper.getInstance().getLastPostRequest()).endAt(System.currentTimeMillis()).limitToLast(50);
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        Post post = dataSnapshot.getValue(Post.class);
                        mAllPosts.add(post);

                        if (mAllPosts.size() == size) {
                            SharedPreferencesHelper.getInstance().setLastPostRequest(/*post.getTimestamp()*/System.currentTimeMillis());
                            sortAllPostsByCategory();
                            mHashMapAllPostsCategories.postValue(mHashMapAllPostsCategories.getValue());
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d("Woow","onChildChanged "+dataSnapshot.toString());
                        Post newPost=dataSnapshot.getValue(Post.class);
                        String category=newPost.getCategory();
                        List<Post>posts=   mHashMapAllPostsCategories.getValue().get(category);

                        for (int i = 0; i < posts.size(); i++) {
                            Post oldPost=posts.get(i);
                            if (newPost.getKey().equals(oldPost.getKey())){
                                oldPost.setCategory(newPost.getCategory());
                                oldPost.setLikes(newPost.getLikes());
                                oldPost.setPostBody(newPost.getPostBody());
                                oldPost.setPrice(newPost.getPrice());
                                oldPost.setTimestamp(newPost.getTimestamp());
                                oldPost.setUsername(newPost.getUsername());
                                oldPost.setTitle(newPost.getTitle());
                            }
                        }
                        mHashMapAllPostsCategories.postValue(mHashMapAllPostsCategories.getValue());
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("Woow","onChildRemoved "+dataSnapshot.toString());
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//

//    public void loadAllPosts() {
//
//        Query query = myRef.child(FireBaseConstant.POSTS_TABLE).orderByChild(TIME_STAMP_KEY).startAt(SharedPreferencesHelper.getInstance().getLastPostRequest()).endAt(System.currentTimeMillis()).limitToLast(50);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                SharedPreferencesHelper.getInstance().setLastPostRequest(System.currentTimeMillis());
//
//                mAllPosts.clear();
//                mHashMapAllPostsCategories.getValue().clear();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Post post = snapshot.getValue(Post.class);
//                    mAllPosts.add(post);
//                }
//                sortAllPostsByCategory();
//
//                mHashMapAllPostsCategories.postValue(mHashMapAllPostsCategories.getValue());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

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
        this.mCategories = categories;
    }


    public void setLike(final String postKey, final Boolean isLike) {
        myRef.child(FireBaseConstant.POSTS_TABLE).child(postKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int val = isLike ? 1 : -1;
                myRef.child(FireBaseConstant.POSTS_TABLE).child(postKey).child("likes").setValue(mMutableLiveData.getValue().getLikes() + val);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference = myRef.child(FireBaseConstant.LIKES_TABLE).child(SharedPreferencesHelper.getInstance().getUser().textEmailForFirebase()).child(postKey);
        if (isLike)
            reference.setValue(postKey);
        else
            reference.removeValue();
    }

    private MutableLiveData<Post> mMutableLiveData = new MutableLiveData<>();


    public MutableLiveData<Post> getPostLiveData(String postKey) {

        myRef.child(FireBaseConstant.POSTS_TABLE).child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                mMutableLiveData.postValue(post);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return mMutableLiveData;

    }
}
