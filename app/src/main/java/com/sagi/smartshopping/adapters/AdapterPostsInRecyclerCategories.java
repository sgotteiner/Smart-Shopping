package com.sagi.smartshopping.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sagi.smartshopping.R;

import java.util.List;

public class AdapterPostsInRecyclerCategories extends RecyclerView.Adapter<AdapterPostsInRecyclerCategories.PlaceHolder> {

    private List<Bitmap> posts;
    private LayoutInflater layoutInflater;
    private Context context;


    public AdapterPostsInRecyclerCategories(List<Bitmap> posts, Context context) {
        this.posts = posts;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }


    public class PlaceHolder extends RecyclerView.ViewHolder {

        private ImageView imgPost;

        public PlaceHolder(View view) {
            super(view);
            imgPost = view.findViewById(R.id.imgPost);
        }
    }


    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_picture_post, parent, false);

        return new PlaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterPostsInRecyclerCategories.PlaceHolder holder, final int position) {

        final Bitmap bitmapPost = posts.get(position);
        holder.imgPost.setImageBitmap(bitmapPost);
        holder.imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "ejfoije", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

}
