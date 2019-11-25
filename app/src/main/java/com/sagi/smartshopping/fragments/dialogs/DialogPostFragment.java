package com.sagi.smartshopping.fragments.dialogs;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.sagi.smartshopping.utilities.DownloadImage;
import com.sagi.smartshopping.utilities.Patch;
import com.sagi.smartshopping.reposetories.preferance.SharedPreferencesHelper;
import com.sagi.smartshopping.utilities.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DialogPostFragment extends DialogFragment  {

    private static final String POST_KEY = "POST_KEY";
    private Post mPost;
    private TextView mTxtPostDate, mTxtUsername, mTxtPostBody, mTxtPostTitle, mTxtPostPrice, mTxtNumberOfLikes;
    private ImageView mImgProfileImage, mImgPostImage, mImgLike, mImgChat, mImgSend;
    private EditText mEdtResponse;
    private PostViewModel mViewModel;
    private boolean mIsLike;
    private RecyclerView mRecyclerViewResponses;
    private AdapterResponse mAdapterResponse;
    private List<Response> mPostResponses = new ArrayList<>();


    private void configResponseRecyclerViews() {
        mAdapterResponse = new AdapterResponse(getContext());
        mRecyclerViewResponses.setHasFixedSize(true);
        mRecyclerViewResponses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerViewResponses.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerViewResponses.setAdapter(mAdapterResponse);
        mAdapterResponse.submitList(null);
    }

    public static DialogPostFragment newInstance(Post post) {
        Bundle args = new Bundle();
        args.putSerializable(POST_KEY,post);
        DialogPostFragment fragment = new DialogPostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getArguments();
        if (bundle!=null){
            mPost= (Post) bundle.getSerializable(POST_KEY);
        }
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
        setSendResponse();

        initPost();
    }

    private void initPost() {
        loadResponses(mPost.getKey());

        mViewModel.getPostLiveData(mPost.getKey()).observe(this, new Observer<Post>() {
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

        loadIsLike(mPost.getKey()).observe(this, new Observer<Boolean>() {
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


    private void loadResponses(String postKey) {
        configResponseRecyclerViews();

        mViewModel.getAllResponses(postKey).observe(this, new Observer<List<Response>>() {
            @Override
            public void onChanged(List<Response> postResponses) {
                mAdapterResponse.submitList(postResponses);
                mAdapterResponse.notifyDataSetChanged();

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




    private LiveData<Boolean> loadIsLike(String postKey) {
        return mViewModel.loadIsLike(postKey);
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



}
