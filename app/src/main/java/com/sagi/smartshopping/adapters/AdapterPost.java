package com.sagi.smartshopping.adapters;

import android.content.Context;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.interfaces.IOpenPost;
import com.sagi.smartshopping.utilities.constant.GeneralConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.PlaceHolder> implements IOpenPost {

    private HashMap<String,List<Post>> mPostCategories;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ArrayList<String> mArrCategoriesWithPosts;
    private AdapterPost.CallBackAdapterPost mCallBackAdapterPost;

    public AdapterPost(HashMap<String,List<Post>> postCategories, Context context, ArrayList<String> arrCategoriesWithPosts,
                       AdapterPost.CallBackAdapterPost callBackAdapterPost) {
        this.mPostCategories = postCategories;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mArrCategoriesWithPosts = arrCategoriesWithPosts;
        this.mCallBackAdapterPost = callBackAdapterPost;
    }

    @Override
    public void openPost(Post post) {
        mCallBackAdapterPost.openPost(post);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final PlaceHolder holder, final int position) {

        final List<Post> postList= mPostCategories.get(mArrCategoriesWithPosts.get(position));

        holder.mTxtCategoryName.setText(mArrCategoriesWithPosts.get(position));

        if(postList!=null) {
            AdapterPostsInRecyclerCategories adapterPostsInRecyclerCategories = new AdapterPostsInRecyclerCategories(postList, mContext,this);
            holder.mRecyclerPosts.setHasFixedSize(true);
            holder.mRecyclerPosts.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.mRecyclerPosts.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

            holder.mRecyclerPosts.setAdapter(adapterPostsInRecyclerCategories);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBackAdapterPost.showSpecificPosts(postList);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mPostCategories.size();
    }

    public interface CallBackAdapterPost {
        void showSpecificPosts(List<Post> specificPosts);
        void openPost(Post post);
    }
}
