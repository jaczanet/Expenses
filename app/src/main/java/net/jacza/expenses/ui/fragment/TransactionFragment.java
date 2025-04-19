package net.jacza.expenses.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;
import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;
import net.jacza.expenses.ui.activity.TransactionActivity;
import net.jacza.expenses.ui.adapter.TransactionAdapter;
import net.jacza.expenses.ui.util.MarginTransactionDecoration;
import net.jacza.expenses.ui.util.SaveBtnModes;

public class TransactionFragment extends Fragment {

    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private Repository<Transaction> repo = TransactionsRepository.getINSTANCE();
    private List<Transaction> transactions;

    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);

        transactions = repo.read(); // Initialize the transactions list here
        adapter = new TransactionAdapter(transactions, requireContext()); // Pass the initialized list to the adapter
        recyclerView = rootView.findViewById(R.id.transactionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MarginTransactionDecoration(10));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target
                ) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    if (position < 0 || position >= transactions.size()) {
                        return; // Avoid index out of bounds
                    }
                    Transaction deletedTransaction = transactions.get(position);
                    transactions.remove(position);
                    adapter.notifyItemRemoved(position); // Notify about the removed item

                    // Update corners after removal
                    if (transactions.size() > 0) {
                        if (position == 0) {
                            adapter.notifyItemChanged(0); // Update the new first item
                        } else if (position == transactions.size()) { // The previous last item is now the new last
                            adapter.notifyItemChanged(transactions.size() - 1);
                        } else if (transactions.size() == 1) {
                            adapter.notifyItemChanged(0); // If only one item remains
                        } else {
                            if (position == 1) adapter.notifyItemChanged(0); // Update the new first if the second was deleted
                            if (position == transactions.size()) adapter.notifyItemChanged(
                                transactions.size() - 1
                            ); // Update the new last if the previous last was deleted
                        }
                    }

                    // Show Snackbar for undo
                    Snackbar.make(recyclerView, "Transaction deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> {
                            transactions.add(position, deletedTransaction);
                            adapter.notifyItemInserted(position); // Notify about the inserted item

                            // Update corners after insertion (UNDO)
                            if (position == 0) {
                                adapter.notifyItemChanged(0); // Update the new first item
                                if (transactions.size() > 1) {
                                    adapter.notifyItemChanged(1); // Update the item that was previously first
                                }
                            } else if (position == transactions.size() - 1) { // The inserted item is now the last
                                adapter.notifyItemChanged(transactions.size() - 1);
                                if (transactions.size() > 1) {
                                    adapter.notifyItemChanged(transactions.size() - 2); // Update the item that was previously last
                                }
                            } else if (transactions.size() == 1) {
                                adapter.notifyItemChanged(0); // If it's the only item
                            } else {
                                adapter.notifyItemChanged(position - 1); // Update the item before the inserted one
                                adapter.notifyItemChanged(position); // Update the inserted one
                                adapter.notifyItemChanged(position + 1); // Update the item after the inserted one
                                if (position == 1) adapter.notifyItemChanged(0); // Update the new first if inserted at the second position
                                if (position == transactions.size() - 2) adapter.notifyItemChanged(
                                    transactions.size() - 1
                                ); // Update the new last if inserted before the last
                            }
                        })
                        .addCallback(
                            new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                                        repo.delete(deletedTransaction);
                                        Toast.makeText(
                                            requireContext(),
                                            "Deleted Transaction: " + deletedTransaction.getNote(),
                                            Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                }
                            }
                        )
                        .show();
                }
            }
        );
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Set up the add transaction button
        rootView
            .findViewById(R.id.addTransactionBtn)
            .setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), TransactionActivity.class);
                intent.putExtra("MODE", SaveBtnModes.ADD);
                startActivity(intent);
            });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Transaction> updatedTransactions = repo.read();
        transactions.clear();
        transactions.addAll(updatedTransactions);
        adapter.notifyDataSetChanged();
    }
}
