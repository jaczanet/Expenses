package net.jacza.expenses.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.ui.activity.CategoryActivity;
import net.jacza.expenses.ui.uistate.CategoryActivityUiState;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    List<Category> categoryList;
    private Repository<Category> repository;

    CategoryActivityUiState uiState;

    public CategoryAdapter(List<Category> categoryList, Repository<Category> repository) {
        this.categoryList = categoryList;
        this.repository = repository;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.category_card, null);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category);

        holder.menuButton.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), holder.menuButton);
            popupMenu.inflate(R.menu.category_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    Intent intent = new Intent(view.getContext(), CategoryActivity.class);
                    intent.putExtra("MODE", "EDIT");
                    intent.putExtra("EDITED CATEGORY", category);
                    view.getContext().startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.menu_delete) {
                    // Delete the category
                    repository.delete(category);
                    this.categoryList = repository.read();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryName;
        public ImageButton menuButton;

        CategoryViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.textViewCategoryName);
            menuButton = itemView.findViewById(R.id.iconCategoryMenu);
        }

        void bind(Category category) {
            categoryName.setText(category.getName());
        }
    }
}
