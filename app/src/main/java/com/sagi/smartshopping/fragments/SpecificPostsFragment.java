package com.sagi.smartshopping.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.adapters.AdapterSpecificPostsByCategoty;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.interfaces.IOpenPost;
import com.sagi.smartshopping.interfaces.ISpecificPostFragment;

import java.util.ArrayList;
import java.util.List;

public class SpecificPostsFragment extends Fragment implements ISpecificPostFragment, IOpenPost {

    private OnFragmentInteractionListener mListener;
    private AdapterSpecificPostsByCategoty mAdapterSpecificPostsByCategoty;
    private ArrayList<Post> mAllSpecificPosts;
    private RecyclerView mRecyclerViewSpecificPosts;

    public SpecificPostsFragment() {
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && getContext()!=null)
            Toast.makeText(getContext(), "Visible SpecificPostsFragment", Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specific_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewSpecificPosts=view.findViewById(R.id.recyclerSpecificPosts);
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

    @Override
    public void showSpecificPosts(List<Post> specificPosts) {
        mAllSpecificPosts = (ArrayList<Post>) specificPosts;
        mAdapterSpecificPostsByCategoty = new AdapterSpecificPostsByCategoty(mAllSpecificPosts, getContext(),this);
        mRecyclerViewSpecificPosts.setHasFixedSize(true);
        mRecyclerViewSpecificPosts.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerViewSpecificPosts.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerViewSpecificPosts.setAdapter(mAdapterSpecificPostsByCategoty);
        Toast.makeText(getContext(), "boom", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openPost(Post post) {
        mListener.openPost(post);
    }

    public interface OnFragmentInteractionListener {
        void registerEventFromMain(ISpecificPostFragment iSpecificPostFragment);
        void openPost(Post post);

    }
}
