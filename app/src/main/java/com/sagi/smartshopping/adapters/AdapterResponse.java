package com.sagi.smartshopping.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.sagi.smartshopping.R;
import com.sagi.smartshopping.entities.Response;

import java.util.List;

public class AdapterResponse extends RecyclerView.Adapter<AdapterResponse.PlaceHolder> {
    private List<Response> mResponses;
    private LayoutInflater mLayoutInflater;
    private Context mContext;


    public AdapterResponse(List<Response> responses, Context context) {
        this.mResponses = responses;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    public class PlaceHolder extends RecyclerView.ViewHolder {

        public PlaceHolder(View view) {
            super(view);

        }
    }


    @Override
    public  PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_response, parent, false);

        return new  PlaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlaceHolder holder, final int position) {

        final Response response = mResponses.get(position);

    }

    @Override
    public int getItemCount() {
        return mResponses.size();
    }

}
