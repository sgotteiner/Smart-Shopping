package com.sagi.smartshopping.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sagi.smartshopping.R;

import java.util.List;

public class AdapterTitleCategory extends RecyclerView.Adapter<AdapterTitleCategory.PlaceHolder> {

    private List<String> mCategoriesTitleList;
    private LayoutInflater mLayoutInflater;
    private CallbackAdapterCategories mListener;


    public AdapterTitleCategory(List<String> categoriesTitleList, Context context, CallbackAdapterCategories callbackAdapterCategories) {
        this.mCategoriesTitleList = categoriesTitleList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mListener = callbackAdapterCategories;
    }


    public class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView mTxtTitleCategories;

        public PlaceHolder(View view) {
            super(view);
            mTxtTitleCategories = view.findViewById(R.id.txtTitleCategory);
        }
    }


    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_title_category, parent, false);

        return new PlaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlaceHolder holder, final int position) {

        final String categories = mCategoriesTitleList.get(position);
        holder.mTxtTitleCategories.setText(categories);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onClickCategory(categories);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCategoriesTitleList.size();
    }

    public interface CallbackAdapterCategories {
        void onClickCategory(String categories);
    }

}
