package com.sagi.smartshopping.adapters;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.entities.Post;

import java.util.HashMap;
import java.util.List;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.PlaceHolder> {

    private HashMap<String,List<Post>> mPostCategories;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public AdapterPost(HashMap<String,List<Post>> postCategories, Context context) {
        this.mPostCategories = postCategories;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    public class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView mTxtCategoryName;
        private RecyclerView mRecyclerPosts;


        public PlaceHolder(View view) {
            super(view);
            mTxtCategoryName = view.findViewById(R.id.txtCategoryName);
            mRecyclerPosts = view.findViewById(R.id.recyclerPosts);
        }
    }


    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_post_category, parent, false);

        return new PlaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlaceHolder holder, final int position) {

        List<Post> postList= mPostCategories.get(String.valueOf(position));

//        holder.mTxtCategoryName.setText(category);

        AdapterPostsInRecyclerCategories adapterPostsInRecyclerCategories = new AdapterPostsInRecyclerCategories(postList, mContext);
        holder.mRecyclerPosts.setHasFixedSize(true);
        holder.mRecyclerPosts.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.mRecyclerPosts.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        holder.mRecyclerPosts.setAdapter(adapterPostsInRecyclerCategories);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return mPostCategories.size();
    }

    public interface CallBackAdapterPostCategories {
        void showCategoryPost(String category);
    }
}
