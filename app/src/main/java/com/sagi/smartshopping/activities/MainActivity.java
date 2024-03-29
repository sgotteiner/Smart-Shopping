package com.sagi.smartshopping.activities;

import android.content.Intent;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagi.smartshopping.R;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.entities.User;
import com.sagi.smartshopping.fragments.ContainerPagesFragment;
import com.sagi.smartshopping.fragments.CreatePostFragment;
import com.sagi.smartshopping.fragments.HomepageFragment;
import com.sagi.smartshopping.fragments.DialogSpecificPostsByCategoryFragment;
import com.sagi.smartshopping.fragments.UserFragment;
import com.sagi.smartshopping.fragments.dialogs.DialogPostFragment;
import com.sagi.smartshopping.interfaces.ICreatePostFragment;
import com.sagi.smartshopping.services.LocationService;
import com.sagi.smartshopping.utilities.Patch;
import com.sagi.smartshopping.reposetories.preferance.SharedPreferencesHelper;
import com.sagi.smartshopping.utilities.UploadImage;
import com.sagi.smartshopping.utilities.Utils;
import com.sagi.smartshopping.utilities.constant.FireBaseConstant;

import java.util.ArrayList;
import java.util.List;

import static com.sagi.smartshopping.utilities.constant.FireBaseConstant.USERS_TABLE;

public class MainActivity extends AppCompatActivity implements
        HomepageFragment.OnFragmentInteractionListener
        , CreatePostFragment.OnFragmentInteractionListener
        , DialogSpecificPostsByCategoryFragment.OnFragmentInteractionListener
        , UserFragment.OnFragmentInteractionListener {

    private ICreatePostFragment mCreatePostFragment;
    private DatabaseReference myRef;
    private String nameDell = "Daniel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRef = FirebaseDatabase.getInstance().getReference();





        // loadAllCategoriesName();
        showFragment(new ContainerPagesFragment());
    }

    public void loadAllCategoriesName() {
        myRef.child(FireBaseConstant.NAME_CATEGORIES_TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] allCategoriesName = new String[(int) dataSnapshot.getChildrenCount() + 1];
                allCategoriesName[0] = "Press to select";
                int index = 1;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nameOfCategory = snapshot.getValue(String.class);
                    allCategoriesName[index++] = nameOfCategory;
                }

                if (mCreatePostFragment != null) {
                    for (int i = 0; i < allCategoriesName.length; i++) {
                        allCategoriesName[i] = Utils.geteFirstLattersUpperCase(allCategoriesName[i]);
                    }
                    mCreatePostFragment.getAllArrNamesCategory(allCategoriesName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.frameLayoutContainerMain, fragment)
                .commit();
    }

    @Override
    public void savePost(Bitmap bitmapPost, String postBody, String category, float price, String title) {
        User user = SharedPreferencesHelper.getInstance().getUser();
        String key = myRef.child(FireBaseConstant.POSTS_TABLE).push().getKey();
        Post post = new Post(System.currentTimeMillis(), user.getEmail(), postBody, category, key, price, title);
        post.setCityLocation(SharedPreferencesHelper.getInstance().getLastCityIThere());

        myRef.child(FireBaseConstant.POSTS_TABLE).child(key).setValue(post);

//        if (isNewCategory)
//            insertToFirebaseNewCategory(category);

        uploadPostImage(bitmapPost, post.getTitle());
    }

    private void insertToFirebaseNewCategory(String category) {
        String textForFirebase = getTextForFirebasse(category);
        myRef.child(FireBaseConstant.NAME_CATEGORIES_TABLE).child(textForFirebase).setValue(category);
    }

    private String getTextForFirebasse(String nameOfGroup) {
        String textForFirebase = nameOfGroup.replace(" ", "_");
        return textForFirebase;
    }

    private void uploadPostImage(Bitmap bitmapPost, String name) {
        new UploadImage(Patch.POSTS_IMAGES, name, bitmapPost, new UploadImage.IUploadImage() {
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

    @Override
    public void updateProfile(User user) {
        myRef.child(USERS_TABLE).child(user.textEmailForFirebase()).setValue(user);
    }

    @Override
    public void updateProfileWithoutBitmap(User user) {
        myRef.child(USERS_TABLE).child(user.textEmailForFirebase()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                if (iUserFragmentGetEventFromMain != null) {
//                    iUserFragmentGetEventFromMain.stopProgressBar();
//                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                if (iUserFragmentGetEventFromMain != null) {
//                    iUserFragmentGetEventFromMain.stopProgressBar();
//                }
            }
        });
    }


    @Override
    public void showPost(Post post) {
        DialogPostFragment dialogPostFragment = DialogPostFragment.newInstance(post);
        showDialogFragment(dialogPostFragment);
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        dialogFragment.setCancelable(true);
        dialogFragment.show(getSupportFragmentManager(), "TAG");

    }

    @Override
    public void showPostsBySpecificCategory(List<Post> specificPosts) {


        DialogFragment dialogFragment = DialogSpecificPostsByCategoryFragment.newInstance((ArrayList<Post>) specificPosts,this);
        showDialogFragment(dialogFragment);
    }


}
