package com.sagi.smartshopping.reposetories.database.post;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sagi.smartshopping.R;
import com.sagi.smartshopping.SmartShoppingApp;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.entities.Response;
import com.sagi.smartshopping.reposetories.database.ShoppingDatabase;
import com.sagi.smartshopping.reposetories.preferance.SharedPreferencesHelper;
import com.sagi.smartshopping.utilities.constant.FireBaseConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.sagi.smartshopping.utilities.constant.GeneralConstants.TIME_STAMP_KEY;

public class PostRepository {
    private static final PostRepository ourInstance = new PostRepository();
    private ShoppingDatabase mShoppingDatabase;
    private DatabaseReference myRef;
    private ArrayList<Post> mAllPosts = new ArrayList<>();
    private String[] mCategories = null;
    private MutableLiveData<HashMap<String, List<Post>>> mHashMapAllPostsCategories = new MutableLiveData<>();
    private ArrayList<String> mArrCategoriesWithPosts = new ArrayList<>();

    public MutableLiveData<HashMap<String, List<Post>>> getHashMapAllPostsCategories() {
        return mHashMapAllPostsCategories;
    }

    public ArrayList<String> getArrCategoriesWithPosts() {
        if (mArrCategoriesWithPosts.size()==0)
            initCategoryList();
        return mArrCategoriesWithPosts;
    }

    private void initCategoryList() {
        mArrCategoriesWithPosts.addAll(Arrays.asList(mCategories).subList(0, getAllCategoriesTitle().length));
    }

    public static PostRepository getInstance() {
        return ourInstance;
    }

    private PostRepository() {
        myRef = FirebaseDatabase.getInstance().getReference();
        String[] allCategories = getAllCategoriesTitle();

        HashMap<String, List<Post>> listHashMap=new HashMap<>();
        for (String category : allCategories) {
            listHashMap.put(category, new ArrayList<Post>());
        }

        mHashMapAllPostsCategories.setValue(listHashMap);
//        mHashMapAllPostsCategories.setValue(new HashMap<String, List<Post>>());
        mShoppingDatabase = ShoppingDatabase.getInstance();
        listenerAllDeltaChanges();
        listenerAllHistoryChanges();
        startObserveToAllCategories(allCategories);
    }

    private void startObserveToAllCategories(String[] allCategories) {
        for (String category : allCategories) {
            Log.d("post", "Start listener to DB category=> " + category);
            getAllPostByCategory(category).observeForever(mObserveDB);
        }
    }

    public void removeObserveToAllCategories( ) {
        for (String category : mCategories) {
            Log.d("post", "Start listener to DB category=> " + category);
            getAllPostByCategory(category).removeObserver(mObserveDB);
        }
    }

    private Observer<List<Post>> mObserveDB = new Observer<List<Post>>() {
        @Override
        public void onChanged(List<Post> posts) {
            if (posts.size() <= 0)
                return;
            mHashMapAllPostsCategories.getValue().put(posts.get(0).getCategory(), posts);
            mHashMapAllPostsCategories.postValue(mHashMapAllPostsCategories.getValue());
            Log.d("post", "Category=> " + posts.get(0).getCategory() + ", " + posts.size() + "");
            Log.d("post", posts.toString());
        }
    };

    private void listenerAllHistoryChanges() {

        Query query = myRef.child(FireBaseConstant.POSTS_TABLE).orderByChild(TIME_STAMP_KEY)
                .endAt(SharedPreferencesHelper.getInstance().getLastPostRequest());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post newPost = dataSnapshot.getValue(Post.class);
                Log.d("post", "listenerAllHistoryChanges onChildAdded() => " + newPost.toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post newPost = dataSnapshot.getValue(Post.class);

                Log.d("post", "listenerAllHistoryChanges onChildChanged() => " + newPost.toString());
                SharedPreferencesHelper.getInstance().setLastPostRequest(newPost.getTimestamp());
                //  String category = newPost.getCategory()
                mShoppingDatabase.postDao().updatePost(newPost);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Woow", "onChildRemoved " + dataSnapshot.toString());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listenerAllDeltaChanges() {
        Query query = myRef.child(FireBaseConstant.POSTS_TABLE).orderByChild(TIME_STAMP_KEY)
                .startAt(SharedPreferencesHelper.getInstance().getLastPostRequest());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post post = dataSnapshot.getValue(Post.class);
                Log.d("post", "onChildAdded() => " + post.toString());
                SharedPreferencesHelper.getInstance().setLastPostRequest(post.getTimestamp());
                mShoppingDatabase.postDao().insertPost(post);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("Woow", "onChildChanged " + dataSnapshot.toString());
                Post newPost = dataSnapshot.getValue(Post.class);
                Log.d("post", "onChildChanged() => " + newPost.toString());
                SharedPreferencesHelper.getInstance().setLastPostRequest(newPost.getTimestamp());
                //  String category = newPost.getCategory()
                mShoppingDatabase.postDao().updatePost(newPost);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                Log.d("Woow", "onChildRemoved " + dataSnapshot.toString());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private long startOfDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis()); // compute start of the day for the timestamp
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);
        return cal.getTimeInMillis();
    }

    public LiveData<List<Post>> getAllPostByCategory(String category ) {
        return mShoppingDatabase.postDao().getAllCategoryPostList(category ,SharedPreferencesHelper.getInstance().getLastCityIThere());
    }

    public String[] getAllCategoriesTitle() {
        if (mCategories == null)
            mCategories = SmartShoppingApp.getContext().getApplicationContext().getResources().getStringArray(R.array.categories);
        return mCategories;
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

    public LiveData<ArrayList<Response>> loadAllResponse(String postKey) {
        final MutableLiveData<ArrayList<Response>> postResponses = new MutableLiveData<>();
        postResponses.setValue(new ArrayList<Response>());
        myRef.child(FireBaseConstant.RESPONSES_TABLE).child(postKey).orderByChild(TIME_STAMP_KEY)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Response response = dataSnapshot.getValue(Response.class);
                        postResponses.getValue().add(response);
                        postResponses.postValue(postResponses.getValue());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        return postResponses;
    }
}
