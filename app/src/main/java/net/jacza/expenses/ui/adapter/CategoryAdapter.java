package net.jacza.expenses.ui.adapter;

import android.content.Intent;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.io.Serializable;
import java.util.List;
import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.repository.CategoriesRepository;
import net.jacza.expenses.domain.safedeletion.FoundAssociatedTransactionException;
import net.jacza.expenses.domain.safedeletion.SafeDeleteUseCase;
import net.jacza.expenses.ui.activity.CategoryActivity;
import net.jacza.expenses.ui.activity.CategoryTransactions;
import net.jacza.expenses.ui.util.SaveBtnModes;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private Repository<Category> repository = CategoriesRepository.getINSTANCE();

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.category_card,
            parent,
            false
        );
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category);

        // Set click listener for the filtered transactions
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), CategoryTransactions.class);
            intent.putExtra("CATEGORY", (Serializable) category);
            view.getContext().startActivity(intent);
        });

        holder.menuButton.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), holder.menuButton);
            popupMenu.inflate(R.menu.category_menu);
            MenuItem deleteItem = popupMenu.getMenu().findItem(R.id.menu_delete);
            SpannableString s = new SpannableString(deleteItem.getTitle());
            s.setSpan(
                new ForegroundColorSpan(view.getContext().getColor(R.color.delete_opt_color)),
                0,
                s.length(),
                0
            );
            deleteItem.setTitle(s);
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    Intent intent = new Intent(view.getContext(), CategoryActivity.class);
                    intent.putExtra("MODE", SaveBtnModes.EDIT);
                    intent.putExtra("CATEGORY TO EDIT", category);
                    view.getContext().startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.menu_delete) {
                    // Delete the account
                    try {
                        SafeDeleteUseCase.category(category);
                        notifyCategoryRemoved(position, view);
                        setList(repository.read());
                    } catch (FoundAssociatedTransactionException e) {
                        Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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

    public void setList(List<Category> newList) {
        this.categoryList = newList;
    }

    public void notifyCategoryRemoved(int position, View view) {
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        Category tempCateg = categoryList.get(position);
        String categName = tempCateg.getName();

        Snackbar.make(view, "Undo Deletion of : " + categName, Snackbar.LENGTH_LONG)
            .setAction("UNDO", v -> {
                categoryList.add(position, tempCateg);
                repository.create(tempCateg);
                notifyItemInserted(position);
                notifyItemRangeChanged(position, getItemCount());
            })
            .show();
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
