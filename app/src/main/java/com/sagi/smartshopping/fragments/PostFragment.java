package com.sagi.smartshopping.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.activities.viewModles.PostViewModel;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.interfaces.IPostFragment;
import com.sagi.smartshopping.utilities.DownloadImage;
import com.sagi.smartshopping.utilities.Patch;
import com.sagi.smartshopping.utilities.Utils;
import com.squareup.picasso.Picasso;

public class PostFragment extends Fragment implements IPostFragment  {

    private OnFragmentInteractionListener mListener;
    private Post mPost;
    private TextView mTxtPostDate, mTxtUsername, mTxtPostBody, mTxtPostTitle, mTxtPostPrice;
    private ImageView mImgProfileImage, mImgPostImage, mImgLike, mImgChat;
    private PostViewModel mViewModel;
    private boolean mIsLike;
    private RecyclerView mRecyclerViewResponses;


    public PostFragment() {
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (isVisibleToUser && getContext() != null)
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

        mViewModel = ViewModelProviders.of(this).get(PostViewModel.class);

        loadViews(view);

    }




    private void loadViews(View view) {
        mImgPostImage = view.findViewById(R.id.imgPostImage);
        mImgProfileImage = view.findViewById(R.id.imgProfileImage);
        mImgLike = view.findViewById(R.id.imgIconLike);
        mImgChat = view.findViewById(R.id.imgIconChat);
        mTxtPostBody = view.findViewById(R.id.txtPostBody);
        mTxtUsername = view.findViewById(R.id.txtUsername);
        mTxtPostDate = view.findViewById(R.id.txtPostDate);
        mTxtPostPrice = view.findViewById(R.id.txtPrice);
        mTxtPostTitle = view.findViewById(R.id.txtTitle);
        mRecyclerViewResponses = view.findViewById(R.id.recyclerViewResponses);
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


    private LiveData<Boolean> loadIsLike(String postKey) {
        return mViewModel.loadIsLike(postKey);
    }

    @Override
    public void showPost(final Post post) {
        mViewModel.getPostLiveData(post.getKey()).observe(this, new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                mPost = post;
                mTxtPostDate.setText(Utils.getDateAndTimeFromTimeStamp(post.getTimestamp()));
                mTxtUsername.setText(post.getUsername());
                mTxtPostBody.setText(post.getPostBody());
                mTxtPostPrice.setText(String.valueOf(post.getPrice()));
                mTxtPostTitle.setText(post.getTitle());
                downloadImage(post.getTitle(),mImgPostImage);
                downloadImage(post.getUsername(),mImgProfileImage);
            }
        });

       loadIsLike(post.getKey()).observe(this, new Observer<Boolean>() {
           @Override
           public void onChanged(Boolean aBoolean) {
               mIsLike=aBoolean;
               mImgLike.setImageResource(aBoolean?R.drawable.like:R.drawable.not_like);
           }
       });
         mImgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsLike = !mIsLike;
                if (mIsLike)
                    mImgLike.setImageResource(R.drawable.like);
                mViewModel.setLike(mPost.getKey(), mIsLike);
            }
        });
    }

    private void downloadImage(String name,final ImageView imageView) {
        new DownloadImage(Patch.POSTS_IMAGES,name, new DownloadImage.IDownloadImage() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getContext()).load(uri).fit().into(imageView);
            }

            @Override
            public void onFail(String error) {

            }
        }).startLoading();
    }

//
//    @Override
//    public void loadLike() {
//        loadImageLike();
//        mImgLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mIsLike = !mIsLike;
//                if (mIsLike)
//                    mImgLike.setImageResource(R.drawable.like);
//                mViewModel.setLike(mPost.getKey(), mIsLike);
//            }
//        });
//    }

    public interface OnFragmentInteractionListener {
        void registerEventFromMain(IPostFragment iPostFragment);
    }


}
