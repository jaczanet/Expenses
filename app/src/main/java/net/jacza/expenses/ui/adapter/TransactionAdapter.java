package net.jacza.expenses.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import net.jacza.expenses.R;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.ui.activity.TransactionActivity;
import net.jacza.expenses.ui.util.BalanceFormatter;
import net.jacza.expenses.ui.util.SaveBtnModes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<Transaction> transactions;
    private final int cornerRadiusRegular;
    private final int cornerRadiusLarge;

    public TransactionAdapter(List<Transaction> transactions, Context context) {
        this.transactions = transactions;
        this.cornerRadiusRegular = context.getResources()
                .getDimensionPixelSize(R.dimen.corner_radius_regular);
        this.cornerRadiusLarge = context.getResources()
                .getDimensionPixelSize(R.dimen.corner_radius_large);
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_card, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.bind(transaction);

        if (getItemCount() == 1) {
            // Single item - all large corners
            holder.setAllCornerRadii(holder.cardView, cornerRadiusLarge);
        } else if (position == 0) {
            // First item - large top corners
            holder.setCornerRadii(holder.cardView,
                    cornerRadiusLarge, cornerRadiusLarge,
                    cornerRadiusRegular, cornerRadiusRegular);
        } else if (position == getItemCount() - 1) {
            // Last item - large bottom corners
            holder.setCornerRadii(holder.cardView,
                    cornerRadiusRegular, cornerRadiusRegular,
                    cornerRadiusLarge, cornerRadiusLarge);
        } else {
            // Middle items - all regular corners
            holder.setAllCornerRadii(holder.cardView, cornerRadiusRegular);
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setList(List<Transaction> newTransactions) {
        transactions.clear();
        transactions.addAll(newTransactions);
        notifyDataSetChanged();
    }

    private void setCornerRadii(MaterialCardView card, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        card.setShapeAppearanceModel(card.getShapeAppearanceModel()
                .toBuilder()
                .setTopLeftCornerSize(topLeft)
                .setTopRightCornerSize(topRight)
                .setBottomRightCornerSize(bottomRight)
                .setBottomLeftCornerSize(bottomLeft)
                .build()
        );
    }

    private void setAllCornerRadii(MaterialCardView card, float radius) {
        setCornerRadii(card, radius, radius, radius, radius);
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        public MaterialCardView cardView;
        private final TextView tvDay;
        private final TextView tvMonth;
        private final TextView tvTransactionNote;
        private final TextView tvCategory;
        private final TextView tvAmount;
        private final SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        private final SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.transaction_card);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvTransactionNote = itemView.findViewById(R.id.tvTransactionNote);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }

        public void bind(Transaction transaction) {
            Date date = new Date(transaction.getTimestamp());
            tvDay.setText(dayFormat.format(date));
            tvMonth.setText(monthFormat.format(date).toUpperCase(Locale.getDefault()));
            tvTransactionNote.setText(transaction.getNote());
            if (transaction.getCategory() != null) {
                tvCategory.setText(transaction.getCategory().getName());
            } else {
                tvCategory.setText("");
            }

            double amount = transaction.getAmount();
            BalanceFormatter.setFormattedBalance(tvAmount, amount, itemView.getContext());


            // Set OnClickListener for the entire card
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), TransactionActivity.class);
                intent.putExtra("MODE", SaveBtnModes.EDIT);
                intent.putExtra("TRANSACTION TO EDIT", transaction);
                itemView.getContext().startActivity(intent);
            });
        }

        // Make these methods non-static
        public void setCornerRadii(MaterialCardView card, float topLeft, float topRight, float bottomRight, float bottomLeft) {
            card.setShapeAppearanceModel(card.getShapeAppearanceModel()
                    .toBuilder()
                    .setTopLeftCornerSize(topLeft)
                    .setTopRightCornerSize(topRight)
                    .setBottomRightCornerSize(bottomRight)
                    .setBottomLeftCornerSize(bottomLeft)
                    .build()
            );
        }

        // Make this method non-static
        public void setAllCornerRadii(MaterialCardView card, float radius) {
            setCornerRadii(card, radius, radius, radius, radius);
        }
    }
}