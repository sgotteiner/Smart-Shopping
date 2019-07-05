package com.sagi.smartshopping.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.sagi.smartshopping.R;

import java.io.IOException;

public class CreatePostFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ImageView mImgPostImage, mImgSave;
    private Bitmap mBitmapPost;
    private EditText mEdtPostBody;
    private final int IMG_POST_FROM_GALLERY = 1;


    public CreatePostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImgPostImage = view.findViewById(R.id.imgPostImage);
        mImgPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });
        mImgSave = view.findViewById(R.id.imgSave);
        mImgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.savePost(mBitmapPost, mEdtPostBody.getText().toString());
            }
        });
        mEdtPostBody = view.findViewById(R.id.edtPostBody);
    }

    private void pickImageFromGallery() {
        showGallery();
    }

    private void showGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, IMG_POST_FROM_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == IMG_POST_FROM_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
//            Uri uriImageGallery = data.getData();
//
//            mImgPostImage.setBackgroundResource(0);
//            mBitmapPost = ImageUtils.handleImageGallery(uriImageGallery, getContext());
//            mImgPostImage.setImageBitmap(mBitmapPost);
//            mBitmapPost = ImageUtils.scaleDown(mBitmapPost, 200, false);
//
//        }

        if (data == null)
            return;
        if (requestCode == IMG_POST_FROM_GALLERY && resultCode == Activity.RESULT_OK && data != null) {

            try {
                mBitmapPost = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                mImgPostImage.setImageBitmap(mBitmapPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void savePost(Bitmap mBitmapPost, String postBody);
    }
}
