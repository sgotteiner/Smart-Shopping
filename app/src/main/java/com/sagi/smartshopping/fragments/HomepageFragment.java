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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sagi.smartshopping.R;
import com.sagi.smartshopping.adapters.AdapterPost;
import com.sagi.smartshopping.adapters.AdapterCategories;
import com.sagi.smartshopping.entities.Post;
import com.sagi.smartshopping.utilities.MockDataHandler;
import com.sagi.smartshopping.utilities.Utils;
import com.sagi.smartshopping.utilities.constant.FireBaseConstant;
import com.sagi.smartshopping.utilities.constant.GeneralConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sagi.smartshopping.utilities.constant.GeneralConstants.TIME_STAMP_KEY;

public class HomepageFragment extends Fragment implements AdapterCategories.CallbackAdapterCategories,
AdapterPost.CallBackAdapterPost{

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> mAllSuggestionsList = new ArrayList<>();
    private HashMap<String, List<Post>> mListHashMapCategories = new HashMap<>();
    private AdapterCategories mAdapterCategories;
    private AdapterPost mAdapterPost;
    private RecyclerView mRecyclerCategories;
    private RecyclerView mRecyclerPost;
    private DatabaseReference myRef;
    private ArrayList<Post> mAllPosts = new ArrayList<>();
    private ArrayList<String> mArrCategoriesWithPosts = new ArrayList<>();

    public HomepageFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && getContext()!=null)
            Toast.makeText(getContext(), "Visible home page", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myRef = FirebaseDatabase.getInstance().getReference();

        loadAllPosts();

        loadViews(view);
        loadMockData();
        configRecyclerViews();

    }

    private void configRecyclerViews() {
        mAdapterCategories = new AdapterCategories(mAllSuggestionsList, getContext(), this);
        mAdapterPost = new AdapterPost(mListHashMapCategories, getContext(), mArrCategoriesWithPosts,this);

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
        allPostsCategories.put("אוכל", categoriesListFood);
        allPostsCategories.put("פנאי", categoriesListGadgets);
        allPostsCategories.put("בגדי גברים", categoriesListFashion);
        return allPostsCategories;
    }

    private void loadMockData() {
        MockDataHandler.addSuggestions(mAllSuggestionsList);
//        mListHashMapCategories = getMapList();
    }

    private void loadViews(View view) {
        mRecyclerCategories = view.findViewById(R.id.recyclerCategories);
        mRecyclerPost = view.findViewById(R.id.recyclerPost);
    }

    private void loadAllPosts() {
        Query query = myRef.child(FireBaseConstant.POSTS_TABLE).orderByChild(TIME_STAMP_KEY).startAt(Utils.getStartTimeStamp()).endAt(System.currentTimeMillis()).limitToLast(50);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    mAllPosts.add(post);
                }
                sortAllPostsByCategory();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sortAllPostsByCategory() {

        String[] categoriesArr=  getResources().getStringArray(R.array.categories);

        for (int i = 0; i < categoriesArr.length; i++) {
            ArrayList<Post> postsCategory = new ArrayList<>();
            for (int j = mAllPosts.size()-1; j >=0 ; j--) {
                if (mAllPosts.get(j).getCategory().equals(categoriesArr[i])) {
                    postsCategory.add(mAllPosts.get(j));
                }
            }
            if (!postsCategory.isEmpty()) {
                mArrCategoriesWithPosts.add(categoriesArr[i]);
                mListHashMapCategories.put(categoriesArr[i], postsCategory);
            }
        }
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
        Toast.makeText(getContext(), categories, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSpecificPosts(List<Post> specificPosts) {
        mListener.showSpecificPosts(specificPosts);
    }

    @Override
    public void openPost(Post post) {
        mListener.openPost(post);
    }

    public interface OnFragmentInteractionListener {
        void openPost(Post post);
        void showSpecificPosts(List<Post> specificPosts);
    }
}
