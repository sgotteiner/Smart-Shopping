package com.sagi.smartshopping.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.entities.Post;

import java.util.List;

public class AdapterPostsInRecyclerCategories extends RecyclerView.Adapter<AdapterPostsInRecyclerCategories.PlaceHolder> {

    private List<Post> posts;
    private LayoutInflater layoutInflater;
    private Context context;


    public AdapterPostsInRecyclerCategories(List<Post> posts, Context context) {
        this.posts = posts;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }


    public class PlaceHolder extends RecyclerView.ViewHolder {

        private ImageView mImgPost;
        private TextView mTxtUsername;

        public PlaceHolder(View view) {
            super(view);
            mImgPost = view.findViewById(R.id.imgPost);
            mTxtUsername = view.findViewById(R.id.txtUsername);
        }
    }


    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_picture_post, parent, false);

        return new PlaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterPostsInRecyclerCategories.PlaceHolder holder, final int position) {

        final Post post = posts.get(position);
        holder.mTxtUsername.setText(post.getUsername());

//        holder.imgPost.setImageBitmap(bitmapPost);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

}
