package com.sagi.smartshopping.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.adapters.AdapterPost;
import com.sagi.smartshopping.adapters.AdapterCategories;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.utilities.MockDataHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomepageFragment extends Fragment implements AdapterCategories.CallbackAdapterCategories {

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> mAllSuggestionsList = new ArrayList<>();
    private  HashMap<String, List<Post>> mListHashMapCategories;
    private AdapterCategories mAdapterCategories;
    private AdapterPost mAdapterPost;
    private RecyclerView mRecyclerCategories;
    private RecyclerView mRecyclerPost;

    public HomepageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadViews(view);
        loadMockData();
        configRecyclerViews();

    }

    private void configRecyclerViews() {
        mAdapterCategories = new AdapterCategories(mAllSuggestionsList, getContext(), this);
        mAdapterPost = new AdapterPost(mListHashMapCategories, getContext());

        mRecyclerCategories.setHasFixedSize(true);
        mRecyclerPost.setHasFixedSize(true);

        mRecyclerCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerPost.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerCategories.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerPost.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mRecyclerCategories.setAdapter(mAdapterCategories);
        mRecyclerPost.setAdapter(mAdapterPost);
    }



    private HashMap<String, List<Post>> getMapList() {
        ArrayList<Post> categoriesListFood = new ArrayList<>();
        ArrayList<Post> categoriesListGadgets = new ArrayList<>();
        ArrayList<Post> categoriesListFashion = new ArrayList<>();

        MockDataHandler.getPosts(categoriesListFood);
        MockDataHandler.getPosts(categoriesListGadgets);
        MockDataHandler.getPosts(categoriesListFashion);

        HashMap<String, List<Post>> allPostsCategories = new HashMap<>();
        allPostsCategories.put("0", categoriesListFood);
        allPostsCategories.put("1", categoriesListGadgets);
        allPostsCategories.put("2", categoriesListFashion);
        return allPostsCategories;
    }

    private void loadMockData() {
        MockDataHandler.addSuggestions(mAllSuggestionsList);
        mListHashMapCategories=getMapList();
    }

    private void loadViews(View view) {
        mRecyclerCategories = view.findViewById(R.id.recyclerCategories);
        mRecyclerPost = view.findViewById(R.id.recyclerPost);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClickCategory(String categories) {
        Toast.makeText(getContext(), categories, Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {

    }
}
