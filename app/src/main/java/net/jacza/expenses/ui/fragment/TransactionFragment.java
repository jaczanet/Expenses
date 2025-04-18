package net.jacza.expenses.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;
import net.jacza.expenses.ui.activity.TransactionActivity;
import net.jacza.expenses.ui.adapter.TransactionAdapter;
import net.jacza.expenses.ui.util.SaveBtnModes;

public class TransactionFragment extends Fragment {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private Repository<Transaction> repo = TransactionsRepository.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);

        adapter = new TransactionAdapter(repo.read());
        recyclerView = rootView.findViewById(R.id.transactionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Set up the add transaction button
        rootView.findViewById(R.id.addTransactionBtn).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TransactionActivity.class);
            intent.putExtra("MODE", SaveBtnModes.ADD);
            startActivity(intent);
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setList(repo.read());
        adapter.notifyDataSetChanged();
    }
}
