package com.sagi.smartshopping.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.interfaces.IPostFragment;
import com.sagi.smartshopping.utilities.DownloadImage;
import com.sagi.smartshopping.utilities.Patch;
import com.sagi.smartshopping.utilities.Utils;
import com.squareup.picasso.Picasso;

public class PostFragment extends Fragment implements IPostFragment {

    private OnFragmentInteractionListener mListener;
    private Post mPost;
    private TextView mTxtPostDate,mTxtUsername,mTxtPostBody,mTxtPostTitle,mTxtPostPrice;
    private ImageView mImgProfileImage,mImgPostImage;

    public PostFragment() {
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && getContext()!=null)
            Toast.makeText(getContext(), "Visible post", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadViews(view);

    }

    private void loadViews(View view) {
        mImgPostImage=view.findViewById(R.id.imgPostImage);
        mImgProfileImage=view.findViewById(R.id.imgProfileImage);
        mTxtPostBody=view.findViewById(R.id.txtPostBody);
        mTxtUsername=view.findViewById(R.id.txtUsername);
        mTxtPostDate=view.findViewById(R.id.txtPostDate);
        mTxtPostPrice=view.findViewById(R.id.txtPrice);
        mTxtPostTitle=view.findViewById(R.id.txtTitle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            mListener.registerEventFromMain(this);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.registerEventFromMain(null);
        mListener = null;
    }

    @Override
    public void openPost(Post post) {
        mTxtPostDate.setText(Utils.getDateAndTimeFromTimeStamp(post.getTimestamp()));
        mTxtUsername.setText(post.getUsername());
        mTxtPostBody.setText(post.getPostBody());
        mTxtPostPrice.setText(String.valueOf(post.getPrice()));
        mTxtPostTitle.setText(post.getTitle());
        new DownloadImage(Patch.POSTS_IMAGES, post.getTitle(), new DownloadImage.IDownloadImage() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getContext()).load(uri).fit().into(mImgPostImage);
            }

            @Override
            public void onFail(String error) {

            }
        }).startLoading();
        new DownloadImage(Patch.PROFILES, post.getUsername(), new DownloadImage.IDownloadImage() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getContext()).load(uri).fit().into(mImgProfileImage);
            }

            @Override
            public void onFail(String error) {

            }
        }).startLoading();
    }

    public interface OnFragmentInteractionListener {
        void registerEventFromMain(IPostFragment iPostFragment);
    }
}
