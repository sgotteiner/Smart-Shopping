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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.viewModles.PostViewModel;
import com.sagi.smartshopping.adapters.AdapterResponse;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.entities.Response;
import com.sagi.smartshopping.entities.User;
import com.sagi.smartshopping.interfaces.IPostFragment;
import com.sagi.smartshopping.utilities.DownloadImage;
import com.sagi.smartshopping.utilities.Patch;
import com.sagi.smartshopping.reposetories.preferance.SharedPreferencesHelper;
import com.sagi.smartshopping.utilities.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment implements IPostFragment {

    private OnFragmentInteractionListener mListener;
    private Post mPost;
    private TextView mTxtPostDate, mTxtUsername, mTxtPostBody, mTxtPostTitle, mTxtPostPrice, mTxtNumberOfLikes;
    private ImageView mImgProfileImage, mImgPostImage, mImgLike, mImgChat, mImgSend;
    private EditText mEdtResponse;
    private PostViewModel mViewModel;
    private boolean mIsLike;
    private RecyclerView mRecyclerViewResponses;
    private AdapterResponse mAdapterResponse;
    private List<Response> mPostResponses = new ArrayList<>();


    public PostFragment() {
    }

    private void configResponseRecyclerViews() {
        mAdapterResponse = new AdapterResponse(getContext());
        mRecyclerViewResponses.setHasFixedSize(true);
        mRecyclerViewResponses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerViewResponses.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerViewResponses.setAdapter(mAdapterResponse);
        mAdapterResponse.submitList(null);

    }

    //    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//
//        if (isVisibleToUser && getContext() != null)
//            Toast.makeText(getContext(), "Visible post", Toast.LENGTH_SHORT).show();
//    }

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
        setSendResponse();

    }


    private void loadResponses(String postKey) {
        configResponseRecyclerViews();

        mViewModel.getAllResponses(postKey).observe(this, new Observer<List<Response>>() {
            @Override
            public void onChanged(List<Response> postResponses) {
//               mPostResponses.clear();

                mAdapterResponse.submitList(postResponses);
                mAdapterResponse.notifyDataSetChanged();

//                if (mPostResponses.size() == 0)
//                    mPostResponses.addAll(postResponses);
//                else
//                    mPostResponses.add(postResponses.get(postResponses.size() - 1));
//                mAdapterResponse.notifyDataSetChanged();
//
//                mRecyclerViewResponses.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRecyclerViewResponses.scrollToPosition(mPostResponses.size() - 1);
//                    }
//                });
            }
        });


    }

    private void setSendResponse() {
        mImgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdtResponse.getText().toString().isEmpty())
                    return;
                User user = SharedPreferencesHelper.getInstance().getUser();
                mViewModel.sendResponse(new Response(mPost.getKey(), System.currentTimeMillis(),
                        user.getEmail(), mEdtResponse.getText().toString()));


                mEdtResponse.setText("");
            }
        });
    }


    private void loadViews(View view) {
        mImgPostImage = view.findViewById(R.id.imgPostImage);
        mImgProfileImage = view.findViewById(R.id.imgProfileImage);
        mImgLike = view.findViewById(R.id.imgIconLike);
        mImgChat = view.findViewById(R.id.imgIconChat);
        mTxtNumberOfLikes = view.findViewById(R.id.txtNumberOfLikes);
        mImgSend = view.findViewById(R.id.imgSend);
        mEdtResponse = view.findViewById(R.id.edtResponse);
        mTxtPostBody = view.findViewById(R.id.txtPostBody);
        mTxtUsername = view.findViewById(R.id.txtUsername);
        mTxtPostDate = view.findViewById(R.id.txtPostDate);
        mTxtPostPrice = view.findViewById(R.id.txtPrice);
        mTxtPostTitle = view.findViewById(R.id.txtTitle);
        mRecyclerViewResponses = view.findViewById(R.id.recyclerViewResponses);
    }

    @Override
    public void onAttach(@NonNull Context context) {
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
        loadResponses(post.getKey());

        mViewModel.getPostLiveData(post.getKey()).observe(this, new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                mPost = post;
                mTxtPostDate.setText(Utils.getDateAndTimeFromTimeStamp(post.getTimestamp()));
                mTxtUsername.setText(post.getUsername());
                mTxtPostBody.setText(post.getPostBody());
                mTxtPostPrice.setText(String.valueOf(post.getPrice()));
                mTxtPostTitle.setText(post.getTitle());
                mTxtNumberOfLikes.setText(String.valueOf(post.getLikes()));
                mTxtNumberOfLikes.setTextSize(post.getLikes() > 1000 ? 7 : 10);

                downloadImage(post.getTitle(), mImgPostImage);
                downloadImage(post.getUsername(), mImgProfileImage);
            }
        });

        loadIsLike(post.getKey()).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mIsLike = aBoolean;
                mImgLike.setImageResource(aBoolean ? R.drawable.like : R.drawable.not_like);
            }
        });
        mImgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsLike = !mIsLike;
                mImgLike.setImageResource(mIsLike ? R.drawable.like : R.drawable.not_like);
                mViewModel.setLike(mPost.getKey(), mIsLike);
            }
        });
    }

    private void downloadImage(String name, final ImageView imageView) {
        new DownloadImage(Patch.POSTS_IMAGES, name, new DownloadImage.IDownloadImage() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getContext()).load(uri).fit().into(imageView);
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
