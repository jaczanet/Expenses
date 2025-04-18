package net.jacza.expenses.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import net.jacza.expenses.R;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.ui.interfaces.OnCategoryClickListener;

import java.util.List;

public class SelectCategoryAdapter extends RecyclerView.Adapter<SelectCategoryAdapter.SelectCategoryViewHolder> {
    private List<Category> categoryList;
    private OnCategoryClickListener listener;
    private Category selectedCategory;
    private Context context;


    public SelectCategoryAdapter(List<Category> categoryList, OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SelectCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pill_shaped_category_btn, parent, false);
        return new SelectCategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category);


        holder.categoryButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class SelectCategoryViewHolder extends RecyclerView.ViewHolder {
        MaterialButton categoryButton;
        public SelectCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryButton = itemView.findViewById(R.id.pill_shaped_category_btn);
        }

        void bind(Category category) {
            categoryButton.setText(category.getName());
        }

    }
}
