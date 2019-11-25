package com.sagi.smartshopping.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.adapters.AdapterItemPost;
import com.sagi.smartshopping.adapters.AdapterSpecificPostsByCategoty;
import com.sagi.smartshopping.entities.Post;

import java.util.ArrayList;

public class DialogSpecificPostsByCategoryFragment extends DialogFragment  implements AdapterItemPost.ICallbackPost {

    private static final String POSTS_KEY = "POSTS_KEY";
    private OnFragmentInteractionListener mListener;
    private AdapterSpecificPostsByCategoty mAdapterSpecificPostsByCategoty;
    private ArrayList<Post> mAllSpecificPosts;
    private RecyclerView mRecyclerViewSpecificPosts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specific_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewSpecificPosts=view.findViewById(R.id.recyclerSpecificPosts);
        initPostsBySpecificCategory();
    }

    public static DialogSpecificPostsByCategoryFragment newInstance(ArrayList<Post> postsByCategory,OnFragmentInteractionListener listener) {
        Bundle args = new Bundle();
        args.putSerializable(POSTS_KEY,postsByCategory);
        DialogSpecificPostsByCategoryFragment fragment = new DialogSpecificPostsByCategoryFragment(listener);
        fragment.setArguments(args);
        return fragment;
    }

    public DialogSpecificPostsByCategoryFragment(OnFragmentInteractionListener listener) {
        mListener=listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getArguments();
        if (bundle!=null){
            mAllSpecificPosts= (ArrayList<Post>) bundle.getSerializable(POSTS_KEY);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (mListener!=null)
         mListener=null;
        super.onDismiss(dialog);
    }

     private void initPostsBySpecificCategory() {
         mAdapterSpecificPostsByCategoty = new AdapterSpecificPostsByCategoty(mAllSpecificPosts, getContext(),this);
        mRecyclerViewSpecificPosts.setHasFixedSize(true);
        mRecyclerViewSpecificPosts.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerViewSpecificPosts.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerViewSpecificPosts.setAdapter(mAdapterSpecificPostsByCategoty);
        Toast.makeText(getContext(), "boom", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showPost(Post post) {
        mListener.showPost(post);
    }

    public interface OnFragmentInteractionListener {
         void showPost(Post post);
    }
}
