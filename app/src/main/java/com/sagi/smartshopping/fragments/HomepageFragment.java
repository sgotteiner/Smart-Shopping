package com.sagi.smartshopping.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.adapters.AdapterPostCategories;
import com.sagi.smartshopping.adapters.AdapterCategories;
import com.sagi.smartshopping.utilities.MockDataHandler;

import java.util.ArrayList;

public class HomepageFragment extends Fragment implements AdapterCategories.CallbackAdapterCategories {

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> mAllSuggestionsList = new ArrayList<>(), mAllCategoriesList = new ArrayList<>();
    private AdapterCategories mAdapterCategories;
    private AdapterPostCategories mAdapterPostCategories;
    private RecyclerView mRecyclerCategories;
    private RecyclerView mRecyclerPostCategories;

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

        mAdapterCategories = new AdapterCategories(mAllSuggestionsList, getContext(),this);
        mAdapterPostCategories = new AdapterPostCategories(mAllCategoriesList, getContext());

        mRecyclerCategories.setHasFixedSize(true);
        mRecyclerPostCategories.setHasFixedSize(true);

        mRecyclerCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerPostCategories.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerCategories.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerPostCategories.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mRecyclerCategories.setAdapter(mAdapterCategories);
        mRecyclerPostCategories.setAdapter(mAdapterPostCategories);


    }

    private void loadMockData() {
        MockDataHandler.addCategories(mAllCategoriesList);
        MockDataHandler.addSuggestions(mAllSuggestionsList);
    }

    private void loadViews(View view) {
        mRecyclerCategories = view.findViewById(R.id.recyclerCategories);
        mRecyclerPostCategories = view.findViewById(R.id.recyclerPostCategories);
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
