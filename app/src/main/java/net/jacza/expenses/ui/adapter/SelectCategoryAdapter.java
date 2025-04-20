package net.jacza.expenses.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import net.jacza.expenses.R;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.ui.interfaces.OnCategoryClickListener;

import java.util.List;

/**
 * {@code SelectCategoryAdapter} is a RecyclerView adapter responsible for displaying a list of {@link Category} items
 * in a selectable manner. It uses pill-shaped buttons to represent each category, allowing the user to select one
 * category at a time.
 * <p>
 * The adapter highlights the selected category with a filled button style and displays unselected categories with an
 * outlined button style.
 * </p>
 *
 * @see Category
 * @see OnCategoryClickListener
 * @see RecyclerView.Adapter
 */
public class SelectCategoryAdapter extends RecyclerView.Adapter<SelectCategoryAdapter.SelectCategoryViewHolder> {
    private List<Category> categoryList;
    private OnCategoryClickListener listener;
    private Category selectedCategory;

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
        Context context = holder.itemView.getContext();

        holder.bind(category);
        boolean isSelected = category.getID().equals(selectedCategory.getID());
        int colorPrimary = getThemeColor(context, com.google.android.material.R.attr.colorPrimary);
        int colorOnPrimary = getThemeColor(context, com.google.android.material.R.attr.colorOnPrimary);

        if (isSelected) {
            // Filled button style
            holder.categoryButton.setBackgroundTintList(ColorStateList.valueOf(colorPrimary));
            holder.categoryButton.setTextColor(colorOnPrimary);
            holder.categoryButton.setStrokeWidth(0);
        } else {
            // Outlined button style
            holder.categoryButton.setBackgroundTintList(ColorStateList.valueOf(android.graphics.Color.TRANSPARENT));
            holder.categoryButton.setTextColor(colorPrimary);
            holder.categoryButton.setStrokeColor(ColorStateList.valueOf(colorPrimary));
            holder.categoryButton.setStrokeWidth(2);
        }

        holder.categoryButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(category);
            }
            Category previous = selectedCategory;
            selectedCategory = category;

            int previousIndex = getCategoryIndexById(previous.getID());
            if (previousIndex != -1) {
                notifyItemChanged(previousIndex);
            }

            notifyItemChanged(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setSelectedCategory(Category category) {
        this.selectedCategory = category;
    }

    private int getCategoryIndexById(java.util.UUID id) {
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getID().equals(id)) {
                return i;
            }
        }
        return -1;
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

    // Helper to get themed color
    private int getThemeColor(Context context, int attr) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }
}
