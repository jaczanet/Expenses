package net.jacza.expenses.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;
import net.jacza.expenses.ui.activity.TransactionActivity;
import net.jacza.expenses.ui.util.SaveBtnModes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
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

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDay;
        private final TextView tvMonth;
        private final TextView tvTransactionNote;
        private final TextView tvCategory;
        private final TextView tvAmount;
        private final SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        private final SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvTransactionNote = itemView.findViewById(R.id.tvTransactionNote);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }

        public void bind(Transaction transaction) {
            Date date = new Date(transaction.getTimestamp());
            tvDay.setText(dayFormat.format(date));
            tvMonth.setText(monthFormat.format(date).toUpperCase(Locale.getDefault())); // Already abbreviates to 3 letters
            tvTransactionNote.setText(transaction.getNote());
            if (transaction.getCategory() != null) {
                tvCategory.setText(transaction.getCategory().getName());
            } else {
                tvCategory.setText("");
            }

            double amount = transaction.getAmount();
            if (amount >= 0) {
                tvAmount.setText(String.format(Locale.getDefault(), "+ €%.2f", amount));
                tvAmount.setTextColor(itemView.getContext().getColor(R.color.income_color));
            } else {
                tvAmount.setText(String.format(Locale.getDefault(), "- €%.2f", Math.abs(amount)));
                tvAmount.setTextColor(itemView.getContext().getColor(R.color.expense_color));
            }

            // Set OnClickListener for the entire card
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), TransactionActivity.class);
                intent.putExtra("MODE", SaveBtnModes.EDIT);
                intent.putExtra("TRANSACTION TO EDIT", transaction);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}