package net.jacza.expenses.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.jacza.expenses.R;
import net.jacza.expenses.domain.analytics.CategoryWithAmount;
import net.jacza.expenses.ui.util.BalanceFormatter;

import java.util.List;

/**
 * {@code CategoryWithAmountAdapter} is a RecyclerView adapter responsible for displaying a list of
 * {@link CategoryWithAmount} objects. Each item in the RecyclerView represents a category and its
 * associated amount.
 * <p>
 * This adapter utilizes a custom ViewHolder, {@link CategoryWithAmountViewHolder}, to efficiently
 * manage the views for each item in the list.
 *
 * @see CategoryWithAmount
 */
public class CategoryWithAmountAdapter extends RecyclerView.Adapter<CategoryWithAmountAdapter.CategoryWithAmountViewHolder> {
    private List<CategoryWithAmount> categoryWithAmounts;

    public CategoryWithAmountAdapter(List<CategoryWithAmount> categoryWithAmounts) {
        this.categoryWithAmounts = categoryWithAmounts;
    }

    @NonNull
    @Override
    public CategoryWithAmountAdapter.CategoryWithAmountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_category, parent, false);
        return new CategoryWithAmountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryWithAmountAdapter.CategoryWithAmountViewHolder holder, int position) {
        holder.bind(categoryWithAmounts.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryWithAmounts.size();
    }

    public static class CategoryWithAmountViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCategory;
        TextView textViewAmount;
        public CategoryWithAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.statsCategory);
            textViewAmount = itemView.findViewById(R.id.statsAmount);
        }

        public void bind(CategoryWithAmount categoryWithAmount){
            textViewCategory.setText(categoryWithAmount.getCATEGORY().getName());
            double amount = categoryWithAmount.getAMOUNT();
            BalanceFormatter.setFormattedBalance(textViewAmount, amount, itemView.getContext());
        }
    }
}
