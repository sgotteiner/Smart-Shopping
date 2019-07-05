package com.sagi.smartshopping.activities;

import android.graphics.Bitmap;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.sagi.smartshopping.R;
import com.sagi.smartshopping.fragments.CreatePostFragment;
import com.sagi.smartshopping.fragments.HomepageFragment;
import com.sagi.smartshopping.fragments.PostFragment;
import com.sagi.smartshopping.utilities.PatchStorage;
import com.sagi.smartshopping.utilities.UploadImage;

public class MainActivity extends AppCompatActivity implements
        HomepageFragment.OnFragmentInteractionListener
        , PostFragment.OnFragmentInteractionListener
        , CreatePostFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        showFragment(new HomepageFragment());
        showFragment(new CreatePostFragment());
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.frameLayoutContainerMain, fragment)
                .commit();
    }

    @Override
    public void savePost(Bitmap bitmapPost, String postBody) {
      //  Post post=new Post(System.currentTimeMillis(),String.valueOf(++key), String.valueOf(++key),postBody);
       // uploadPostImage(bitmapPost,post.getKey());
    }

    private void uploadPostImage(Bitmap bitmapPost,String name) {
        new UploadImage(PatchStorage.POSTS_IMAGES,name, bitmapPost, new UploadImage.IUploadImage() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int progress) {
            }
        }).startUpload();
    }
}
