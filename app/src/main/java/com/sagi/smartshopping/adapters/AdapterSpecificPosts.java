package com.sagi.smartshopping.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.interfaces.IOpenPost;
import com.sagi.smartshopping.utilities.DownloadImage;
import com.sagi.smartshopping.utilities.Patch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSpecificPosts extends RecyclerView.Adapter<AdapterSpecificPosts.PlaceHolder> {

    private ArrayList<Post> mAllSpecificPosts;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private IOpenPost mOpenPost;

    public AdapterSpecificPosts(ArrayList<Post> allSpecificPostsPosts, Context context, IOpenPost iOpenPost) {
        this.mAllSpecificPosts = allSpecificPostsPosts;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mOpenPost = iOpenPost;
    }

    public class PlaceHolder extends RecyclerView.ViewHolder {

        private ImageView mImgPost;
        private TextView mTxtPrice;

        public PlaceHolder(View view) {
            super(view);
            mImgPost = view.findViewById(R.id.imgPost);
            mTxtPrice = view.findViewById(R.id.txtPrice);
        }
    }


    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_picture_post, parent, false);

        return new PlaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterSpecificPosts.PlaceHolder holder, final int position) {

        final Post post = mAllSpecificPosts.get(position);
        holder.mTxtPrice.setText(String.valueOf(post.getPrice()));
        new DownloadImage(Patch.POSTS_IMAGES, post.getTitle(), new DownloadImage.IDownloadImage() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(mContext).load(uri).fit().into(holder.mImgPost);
            }

            @Override
            public void onFail(String error) {

            }
        }).startLoading();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOpenPost.openPost(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAllSpecificPosts.size();
    }

}
