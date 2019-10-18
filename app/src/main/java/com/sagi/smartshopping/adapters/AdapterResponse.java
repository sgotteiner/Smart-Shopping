package com.sagi.smartshopping.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.entities.Response;
import com.sagi.smartshopping.utilities.DownloadImage;
import com.sagi.smartshopping.utilities.Patch;
import com.sagi.smartshopping.utilities.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterResponse extends ListAdapter<Response, AdapterResponse.PlaceHolder> {
    private Context mContext;


    public AdapterResponse(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    @Override
    public void submitList(@Nullable List<Response> list) {
        super.submitList(list);
//        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView mTxtUsername, mTxtBody, mTxtTime;
        private ImageView mImgUserProfile;

        PlaceHolder(View view) {
            super(view);

            mTxtUsername = view.findViewById(R.id.txtUsername);
            mImgUserProfile = view.findViewById(R.id.imgUserProfile);
            mTxtBody = view.findViewById(R.id.txtbody);
            mTxtTime = view.findViewById(R.id.txtTime);
        }
    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_response, parent, false);
        return new PlaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlaceHolder holder, final int position) {

        final Response response = getItem(position);

        holder.mTxtBody.setText(response.getBody());
        holder.mTxtTime.setText(Utils.getDateAndTimeFromTimeStamp(response.getTimeStamp()));
        holder.mTxtUsername.setText(response.getUsername());
        new DownloadImage(Patch.PROFILES, response.getEmailUser(), new DownloadImage.IDownloadImage() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(mContext).load(uri).fit().into(holder.mImgUserProfile);
            }

            @Override
            public void onFail(String error) {

            }
        }).startLoading();
    }

    private static final DiffUtil.ItemCallback<Response> DIFF_CALLBACK = new DiffUtil.ItemCallback<Response>() {
        @Override
        public boolean areItemsTheSame(@NonNull Response oldItem, @NonNull Response newItem) {
            return oldItem.getKey().equals(newItem.getKey());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Response oldItem, @NonNull Response newItem) {
            return (oldItem.getTimeStamp() == newItem.getTimeStamp()
                    && oldItem.getEmailUser().equals(newItem.getEmailUser()));
        }

    };


}