package com.sagi.smartshopping.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.sagi.smartshopping.R;
import com.sagi.smartshopping.entities.Post;
import java.util.ArrayList;
import java.util.List;

public class AdapterPostCategories extends RecyclerView.Adapter<AdapterPostCategories.PlaceHolder> {

    private List<String> postCategories;
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Bitmap> allBitmapPosts=new ArrayList<>();
    private AdapterPostsInRecyclerCategories adapterPostsInRecyclerCategories;


    public AdapterPostCategories(List<String> postCategories, Context context) {
        this.postCategories = postCategories;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }


    public class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView txtCategoryName;
        private RecyclerView recyclerPosts;


        public PlaceHolder(View view) {
            super(view);
            txtCategoryName = view.findViewById(R.id.txtCategoryName);
            recyclerPosts = view.findViewById(R.id.recyclerPosts);
        }
    }


    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_post_category, parent, false);

        return new PlaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlaceHolder holder, final int position) {

        final String category = postCategories.get(position);
        holder.txtCategoryName.setText(category);

        addImages();
        adapterPostsInRecyclerCategories = new AdapterPostsInRecyclerCategories(allBitmapPosts, context);
        holder.recyclerPosts.setHasFixedSize(true);
        holder.recyclerPosts.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerPosts.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        holder.recyclerPosts.setAdapter(adapterPostsInRecyclerCategories);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, category, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addImages() {

    }

    @Override
    public int getItemCount() {
        return postCategories.size();
    }

    public interface CallBackAdapterPostCategories{
        void showChatScreen(Post post);
    }
}
