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
    private PatchStorage patchStorageImage;
    private String imageName;
    private IDownloadImage mListener;



    public DownloadImage(PatchStorage patchStorageImage, String imageName, IDownloadImage mListener) {
        this.patchStorageImage = patchStorageImage;
        this.imageName = patchStorageImage.name().equals(PatchStorage.POSTS_IMAGES.name())?imageName:imageName.toLowerCase().replace(" ", "_");
        this.mListener = mListener;
    }

    public interface IDownloadImage {
        void onSuccess(Uri uri);

        void onFail(String error);
    }

    public void startLoading() {

        mStorageRef.child(patchStorageImage.name()).child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
