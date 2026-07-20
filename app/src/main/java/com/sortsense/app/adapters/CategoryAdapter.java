package com.sortsense.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sortsense.app.R;
import com.sortsense.app.activities.BacktrackingActivity;
import com.sortsense.app.activities.BacktrackingDetailActivity;
import com.sortsense.app.activities.PathFindingTheoryActivity;
import com.sortsense.app.activities.SearchingActivity;
import com.sortsense.app.activities.SortingActivity;
import com.sortsense.app.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
    Context context;
    ArrayList<Category> categoryArrayList;

    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.categoryImage.setImageResource(categoryArrayList.get(position).getImageUrl());
        holder.txtCategoryName.setText(categoryArrayList.get(position).getCategoryName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = holder.txtCategoryName.getText().toString();
                if (name.equals("Searching")) {
                    context.startActivity(new Intent(context, SearchingActivity.class));
                } else if (name.equals("Sorting")) {
                    context.startActivity(new Intent(context, SortingActivity.class));
                } else if (name.equals("Backtracking")) {
                    context.startActivity(new Intent(context, BacktrackingActivity.class));
                } else if (name.equals("Path Finding")) {
                    context.startActivity(new Intent(context, PathFindingTheoryActivity.class));
                } else if (context instanceof BacktrackingActivity) {
                    Intent intent = new Intent(context, BacktrackingDetailActivity.class);
                    intent.putExtra("ALGO_NAME", name);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "coming soon...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

}

class CategoryHolder extends RecyclerView.ViewHolder {
    ImageView categoryImage;
    TextView txtCategoryName;

    public CategoryHolder(@NonNull View itemView) {
        super(itemView);
        categoryImage = itemView.findViewById(R.id.imgCategory);
        txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
    }
}