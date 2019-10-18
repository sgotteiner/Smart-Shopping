package com.sagi.smartshopping.reposetories.database.post;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.reposetories.database.DatabaseConstant;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PostDao {

    @Insert
    void insertPost(Post post);

    @Insert
    void insertListPost(ArrayList<Post> posts);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void updatePost(Post newPost);

    @Query("SELECT * FROM "+ DatabaseConstant.POST_TABLE+" WHERE mCategory = :category ORDER BY mTimestamp DESC")
    LiveData<List<Post>> getAllCategoryPostList(String category);


    @Query("SELECT * FROM "+ DatabaseConstant.POST_TABLE+" WHERE mTimestamp >= :timeStampStart AND mTimestamp <= :timeStampEnd ORDER BY mTimestamp DESC")
    LiveData<List<Post>> getPostListBetweenDate(long timeStampStart,long timeStampEnd);


}
