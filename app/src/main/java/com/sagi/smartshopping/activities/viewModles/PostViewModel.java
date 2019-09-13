package com.sagi.smartshopping.activities.viewModles;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagi.smartshopping.activities.reposetories.PostRepository;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.utilities.SharedPreferencesHelper;
import com.sagi.smartshopping.utilities.constant.FireBaseConstant;

public class PostViewModel extends ViewModel {
    private PostRepository mPostRepository = PostRepository.getInstance();
    private DatabaseReference myRef;

    public PostViewModel() {
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    public void setLike(String postKey, Boolean isLike) {
        mPostRepository.setLike(postKey, isLike);
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
        return mMutableLiveDataLiked;
    }

    public LiveData<Post> getPostLiveData(String postKey) {
        return mPostRepository.getPostLiveData(postKey);
    }
}
