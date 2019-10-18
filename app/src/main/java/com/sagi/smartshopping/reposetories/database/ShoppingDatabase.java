package com.sagi.smartshopping.reposetories.database;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sagi.smartshopping.SmartShoppingApp;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.entities.Response;
import com.sagi.smartshopping.reposetories.database.post.PostDao;
import com.sagi.smartshopping.reposetories.database.response.ResponseDao;

@Database(entities = {Post.class, Response.class}, version = DatabaseConstant.DATABASE_VERSION, exportSchema = false)
public abstract class ShoppingDatabase extends RoomDatabase {

    private static ShoppingDatabase sInstance;

    public abstract PostDao postDao();
    public abstract ResponseDao responseDao();



    public static synchronized ShoppingDatabase getInstance(/*Context context*/){
        if (sInstance==null){
            sInstance=  Room.databaseBuilder(SmartShoppingApp.getContext(),
                    ShoppingDatabase.class, DatabaseConstant.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(mRoomCallback)
                    .build();
        }
        return sInstance;
    }


    private static Callback mRoomCallback=new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };


}
