package com.sagi.smartshopping.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.viewModles.HomePageViewModel;
import com.sagi.smartshopping.adapters.AdapterPostsByCategory;
import com.sagi.smartshopping.adapters.AdapterTitleCategory;
import com.sagi.smartshopping.entities.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HomepageFragment extends Fragment implements AdapterTitleCategory.CallbackAdapterCategories,
        AdapterPostsByCategory.CallBackAdapterPost {

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> mAllSuggestionsList = new ArrayList<>();
    private AdapterTitleCategory mAdapterTitleCategory;
    private RecyclerView mRecyclerCategories;
    private AdapterPostsByCategory mAdapterPostsByCategory;
    private RecyclerView mRecyclerAllPostsCategories;
    private HomePageViewModel mViewModel;

    public HomepageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(HomePageViewModel.class);
        loadViews(view);
        mAllSuggestionsList = new ArrayList<>(Arrays.asList(mViewModel.loadAllCategoriesTitle()));
        configRecyclerViews();

        mViewModel.getHashMapAllPostsCategories().observe(this, new Observer<HashMap<String, List<Post>>>() {
            @Override
            public void onChanged(HashMap<String, List<Post>> stringListHashMap) {

                filterPostList(stringListHashMap);
                mAdapterPostsByCategory.notifyDataSetChanged();
            }
        });
    }

    private void filterPostList(HashMap<String, List<Post>> listHashMap) {
        for (int i = 0; i < mViewModel.getArrCategoriesWithPosts().size(); i++)
            if (listHashMap.get(mViewModel.getArrCategoriesWithPosts().get(i)).size() > 0) {
                mHashMapWithPostList.put(mViewModel.getArrCategoriesWithPosts().get(i), listHashMap.get(mViewModel.getArrCategoriesWithPosts().get(i)));

                if (!isCategoryNoExist(mViewModel.getArrCategoriesWithPosts().get(i)))
                    mCategoriesWithPosts.add(mViewModel.getArrCategoriesWithPosts().get(i));
            }
    }

    private boolean isCategoryNoExist(String category) {
        for (int i = 0; i < mCategoriesWithPosts.size(); i++) {
            if (mCategoriesWithPosts.get(i).equals(category))
                return true;
        }
        return false;
    }


    private HashMap<String, List<Post>> mHashMapWithPostList = new HashMap<>();
    private ArrayList<String> mCategoriesWithPosts = new ArrayList<>();


    private void configRecyclerViews() {
        mAdapterTitleCategory = new AdapterTitleCategory(mAllSuggestionsList, getContext(), this);
        mAdapterPostsByCategory = new AdapterPostsByCategory(mHashMapWithPostList, getContext(), mCategoriesWithPosts, this);

        mRecyclerCategories.setHasFixedSize(true);
        mRecyclerAllPostsCategories.setHasFixedSize(true);

        mRecyclerCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerAllPostsCategories.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerCategories.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerAllPostsCategories.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mRecyclerCategories.setAdapter(mAdapterTitleCategory);
        mRecyclerAllPostsCategories.setAdapter(mAdapterPostsByCategory);
    }

    private void loadViews(View view) {
        mRecyclerCategories = view.findViewById(R.id.recyclerTitleCategories);
        mRecyclerAllPostsCategories = view.findViewById(R.id.recyclerAllPostsByCategories);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(context, "onAttach", Toast.LENGTH_SHORT).show();
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
        if (mHashMapWithPostList.containsKey(categories))
            showPostsBySpecificCategory(mHashMapWithPostList.get(categories));
    }

    @Override
    public void showPostsBySpecificCategory(List<Post> specificPosts) {
        mListener.showPostsBySpecificCategory(specificPosts);
    }

    @Override
    public void showPost(Post post) {
        mListener.showPost(post);
    }

    public interface OnFragmentInteractionListener {
        void showPost(Post post);

        void showPostsBySpecificCategory(List<Post> specificPosts);
    }
}
