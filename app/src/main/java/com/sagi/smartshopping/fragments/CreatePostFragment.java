package com.sagi.smartshopping.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sagi.smartshopping.R;
import com.sagi.smartshopping.interfaces.ICreatePostFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatePostFragment extends Fragment implements ICreatePostFragment {

    private String[] mListAllNamesCategories=new String[1]  ;
    private OnFragmentInteractionListener mListener;
    private ImageView mImgPostImage, mImgSave;
    private Bitmap mBitmapPost;
    private EditText mEdtPostBody, mEdtPostPrice, mEdtPostTitle;
    private Spinner mSpnCategory;
    private TextView mTxtCategory;
    private final int IMG_POST_FROM_GALLERY = 1;
    private boolean isNewCategory;
    private DatabaseReference myRef;


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

        myRef = FirebaseDatabase.getInstance().getReference();

        loadViews(view);

        mImgPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });
        mImgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.savePost(mBitmapPost, mEdtPostBody.getText().toString(), mTxtCategory.getText().toString(), Float.parseFloat(mEdtPostPrice.getText().toString()), mEdtPostTitle.getText().toString());
            }
        });

//        loadCategoriesToSpnCategories();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.categories));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnCategory.setAdapter(adapter);

        mSpnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                    return;

                mTxtCategory.setText(adapterView.getItemAtPosition(i).toString());
                isNewCategory = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadViews(View view) {
        mImgPostImage = view.findViewById(R.id.imgPostImage);
        mImgSave = view.findViewById(R.id.imgSave);
        mSpnCategory = view.findViewById(R.id.spnCategory);
        mEdtPostBody = view.findViewById(R.id.edtPostBody);
        mEdtPostPrice = view.findViewById(R.id.edtPostPrice);
        mEdtPostTitle = view.findViewById(R.id.edtPostTitle);
        mTxtCategory = view.findViewById(R.id.txtCategory);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && getContext()!=null)
            Toast.makeText(getContext(), "Visible create post", Toast.LENGTH_SHORT).show();
    }

  

    private Boolean isNewCategoryCreated(String category, Spinner spnCategory) {
        for (int i = 1; i < mListAllNamesCategories.length; i++) {
            if (mListAllNamesCategories[i].toLowerCase().equals(category.toLowerCase())) {
                spnCategory.setSelection(i);
                mTxtCategory.setText(category);
                return false;
            }
        }
        spnCategory.setSelection(0);
        return true;
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

    @Override
    public void getAllArrNamesCategory(String[] allNamesCategories) {
        mListAllNamesCategories = allNamesCategories;
        List<String> list = new ArrayList<>();
        list.add("press to select");
        list.addAll(Arrays.asList(allNamesCategories));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnCategory.setAdapter(adapter);
        isNewCategoryCreated(mTxtCategory.getText().toString(), mSpnCategory);
    }

    public interface OnFragmentInteractionListener {

        void savePost(Bitmap mBitmapPost, String postBody, String category, float price, String title);
    }
}
