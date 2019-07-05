package com.sagi.smartshopping.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sagi.smartshopping.R;

import java.util.List;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.PlaceHolder> {

    private List<String> mCategoriesList;
    private LayoutInflater mLayoutInflater;
    private CallbackAdapterCategories mListener;


    public AdapterCategories(List<String> categoriesList, Context context, CallbackAdapterCategories callbackAdapterCategories) {
        this.mCategoriesList = categoriesList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mListener = callbackAdapterCategories;
    }


    public class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView mTxtCategories;

        public PlaceHolder(View view) {
            super(view);
            mTxtCategories = view.findViewById(R.id.txtCategories);
        }
    }


    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_categories, parent, false);

        return new PlaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlaceHolder holder, final int position) {

        final String categories = mCategoriesList.get(position);
        holder.mTxtCategories.setText(categories);
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
        return mCategoriesList.size();
    }

    public interface CallbackAdapterCategories {
        void onClickCategory(String categories);
    }

}
