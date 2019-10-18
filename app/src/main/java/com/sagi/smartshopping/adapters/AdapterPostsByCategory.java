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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterPostsByCategory extends RecyclerView.Adapter<AdapterPostsByCategory.PlaceHolder> implements AdapterItemPost.ICallbackPost {

    private HashMap<String,List<Post>> mPostOfCategory;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ArrayList<String> mArrCategoriesWithPosts;
    private AdapterPostsByCategory.CallBackAdapterPost mCallBackAdapterPost;

    public AdapterPostsByCategory(HashMap<String,List<Post>> postCategories, Context context, ArrayList<String> arrCategoriesWithPosts,
                                  AdapterPostsByCategory.CallBackAdapterPost callBackAdapterPost) {
        this.mPostOfCategory = postCategories;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mArrCategoriesWithPosts = arrCategoriesWithPosts;
        this.mCallBackAdapterPost = callBackAdapterPost;
    }

    @Override
    public void showPost(Post post) {
        mCallBackAdapterPost.showPost(post);
    }


    public class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView mTxtCategoryName;
        private RecyclerView mRecyclerPosts;


        public PlaceHolder(View view) {
            super(view);
            mTxtCategoryName = view.findViewById(R.id.txtCategoryName);
            mRecyclerPosts = view.findViewById(R.id.recyclerPostsByCategory);
        }
    }


    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_posts_category, parent, false);

        return new PlaceHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final PlaceHolder holder, final int position) {
//
//        if (!mPostOfCategory.containsKey(mArrCategoriesWithPosts.get(position)))
//            return;
        final List<Post> postList= mPostOfCategory.get(mArrCategoriesWithPosts.get(position));

        holder.mTxtCategoryName.setText(mArrCategoriesWithPosts.get(position));

        if(postList!=null) {
            AdapterItemPost adapterItemPost = new AdapterItemPost(postList, mContext,this);
            holder.mRecyclerPosts.setHasFixedSize(true);
            holder.mRecyclerPosts.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.mRecyclerPosts.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            holder.mRecyclerPosts.setAdapter(adapterItemPost);
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
        return mPostOfCategory.size();
    }

    public interface CallBackAdapterPost {
        void showSpecificPosts(List<Post> specificPosts);
        void showPost(Post post);
    }
}
