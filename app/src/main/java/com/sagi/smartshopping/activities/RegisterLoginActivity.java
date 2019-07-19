package com.sagi.smartshopping.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagi.smartshopping.R;
import com.sagi.smartshopping.entities.User;
import com.sagi.smartshopping.fragments.LoginFragment;
import com.sagi.smartshopping.fragments.RegisterFragment;
import com.sagi.smartshopping.interfaces.IWaitingProgressBar;
import com.sagi.smartshopping.utilities.constant.FireBaseConstant;
import com.sagi.smartshopping.utilities.Patch;
import com.sagi.smartshopping.utilities.SharedPreferencesHelper;
import com.sagi.smartshopping.utilities.UploadImage;

public class RegisterLoginActivity extends AppCompatActivity
        implements RegisterFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener {

    private DatabaseReference mMyRef;
    private FirebaseAuth mAuth;
    private IWaitingProgressBar mIWaitingProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        mAuth = FirebaseAuth.getInstance();
        mMyRef = FirebaseDatabase.getInstance().getReference();
        showFragment(new LoginFragment());
    }


    private void uploadBitmap(Bitmap bitmapProfile, User user, final boolean isRememberMe) {
        new UploadImage(Patch.PROFILES, user.getEmail(), bitmapProfile, new UploadImage.IUploadImage() {
            @Override
            public void onSuccess() {
                if (mIWaitingProgressBar != null)
                    mIWaitingProgressBar.stopProgressBar();
                showMainActivity(isRememberMe);
//                Toast.makeText(RegisterLoginActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String error) {
//                Toast.makeText(RegisterLoginActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                if (mIWaitingProgressBar != null)
                    mIWaitingProgressBar.stopProgressBar();
            }

            @Override
            public void onProgress(int progress) {
            }
        }).startUpload();
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.frameLayoutContainerLogin, fragment)
                .commit();
    }

    @Override
    public void createUser(final User user, String password, final boolean isRememberMe, final Bitmap newProfilePic) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            SharedPreferencesHelper.getInstance( ).setUser(user);
                            mMyRef.child(FireBaseConstant.USERS_TABLE).child(user.textEmailForFirebase()).setValue(user);
                            if (newProfilePic != null)
                                uploadBitmap(newProfilePic, user, isRememberMe);
                            else {
                                if (mIWaitingProgressBar != null)
                                    mIWaitingProgressBar.stopProgressBar();
                                showMainActivity(isRememberMe);
                            }
                        } else {
                            Toast.makeText(RegisterLoginActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void signIn(final String email, String password, final boolean isRememberMe) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            getUserFromFirebase(email, isRememberMe);
                        } else {
                            Toast.makeText(RegisterLoginActivity.this, "ERROR USER NOT FOUND", Toast.LENGTH_LONG).show();
                            if (mIWaitingProgressBar != null)
                                mIWaitingProgressBar.stopProgressBar();
                        }
                    }
                });
    }

    private void getUserFromFirebase(String email, final boolean isRememberMe) {
        User user = new User();
        user.setEmail(email);
        mMyRef.child(FireBaseConstant.USERS_TABLE).child(user.textEmailForFirebase()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);
                SharedPreferencesHelper.getInstance( ).setUser(userProfile);
                if (mIWaitingProgressBar != null)
                    mIWaitingProgressBar.stopProgressBar();
                showMainActivity(isRememberMe);
                Toast.makeText(RegisterLoginActivity.this, "OK", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RegisterLoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                if (mIWaitingProgressBar != null)
                    mIWaitingProgressBar.stopProgressBar();
            }
        });
    }

    @Override
    public void showRegisterFragment() {
        showFragment(new RegisterFragment());
    }

    @Override
    public void showLoginFragment() {
        showFragment(new LoginFragment());
    }

    @Override
    public void registerEventFromRegisterLogin(IWaitingProgressBar iWaitingProgressBar) {
        this.mIWaitingProgressBar = iWaitingProgressBar;
    }

    private void showMainActivity(boolean isRememberMe) {
        SharedPreferencesHelper.getInstance( ).setIsAlreadyLogin(isRememberMe);
        Intent intent = new Intent(RegisterLoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
