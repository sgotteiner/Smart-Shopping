package com.sagi.smartshopping.viewModles;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagi.smartshopping.R;
import com.sagi.smartshopping.SmartShoppingApp;
import com.sagi.smartshopping.reposetories.database.post.PostRepository;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.entities.Response;
import com.sagi.smartshopping.reposetories.preferance.SharedPreferencesHelper;
import com.sagi.smartshopping.utilities.constant.FireBaseConstant;

import java.util.ArrayList;
import java.util.List;

public class PostViewModel extends ViewModel {
    private PostRepository mPostRepository = PostRepository.getInstance();
    private DatabaseReference myRef;

    public PostViewModel() {
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    public void setLike(String postKey, Boolean isLike) {
        mPostRepository.setLike(postKey, isLike);
    }


    public LiveData<Post> getPostLiveData(String postKey) {
        return mPostRepository.getPostLiveData(postKey);
    }

    public LiveData<List<Post>> getAllPostByCategory(String category ) {
       return mPostRepository.getAllPostByCategory(category );
    }

    public void sendResponse(Response response) {
        String key = myRef.child(FireBaseConstant.RESPONSES_TABLE).push().getKey();
        response.setKey(key);
        myRef.child(FireBaseConstant.RESPONSES_TABLE).child(response.getKeyPost()).child(key).setValue(response);
    }

    public LiveData<ArrayList<Response>> getAllResponses(String postKey) {
        return mPostRepository.loadAllResponse(postKey);
    }

    private MutableLiveData mMutableLiveDataLiked = new MutableLiveData();

    public LiveData<Boolean> loadIsLike(String postKey) {

        myRef.child(FireBaseConstant.LIKES_TABLE).child(SharedPreferencesHelper.getInstance().
                getUser().textEmailForFirebase()).child(postKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMutableLiveDataLiked.postValue(dataSnapshot.exists());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        myRef.child(FireBaseConstant.LIKES_TABLE).child(SharedPreferencesHelper.getInstance().
//                getUser().textEmailForFirebase()).child(postKey).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mMutableLiveDataLiked.postValue(dataSnapshot.exists());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        return mMutableLiveDataLiked;
    }


    public String[] getAllCategoriesList() {
        return SmartShoppingApp.getContext().getApplicationContext().getResources().getStringArray(R.array.categories);
    }
}
