package com.sagi.smartshopping.utilities;

import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by User on 01/03/2019.
 */

public class DownloadImage {
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private Patch mPatchStorageImage;
    private String mImageName;
    private IDownloadImage mListener;



    public DownloadImage(Patch patchStorageImage, String imageName, IDownloadImage mListener) {
        this.mPatchStorageImage = patchStorageImage;
        this.mImageName = patchStorageImage.name().equals(Patch.POSTS_IMAGES.name())?imageName:imageName.toLowerCase().replace(" ", "_");
        this.mListener = mListener;
    }

    public interface IDownloadImage {
        void onSuccess(Uri uri);

        void onFail(String error);
    }

    public void startLoading() {


        mStorageRef.child(mPatchStorageImage.name()).child(mImageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(Uri uri) {
                if (mListener != null) {
                    mListener.onSuccess(uri);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if (mListener != null)
                    mListener.onFail(exception.getMessage());
            }
        });

    }
}
