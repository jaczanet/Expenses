package net.jacza.expenses.ui.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionsRepository repo;
    private MutableLiveData<ArrayList<Transaction>> transactionsLiveData = new MutableLiveData<>();

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repo = new TransactionsRepository();
        loadTransactions(); // load on init
    }

    public LiveData<ArrayList<Transaction>> getAllTransactions() {
        return transactionsLiveData;
    }

    private void loadTransactions() {
        Executors.newSingleThreadExecutor().execute(() -> {
            ArrayList<Transaction> data = repo.read();
            new Handler(Looper.getMainLooper()).post(() -> transactionsLiveData.setValue(data));
        });
    }

    public void createTransaction(Transaction t) {
        Executors.newSingleThreadExecutor().execute(() -> {
            repo.create(t);
            loadTransactions(); // refresh
        });
    }

    public void updateTransaction(Transaction t) {
        Executors.newSingleThreadExecutor().execute(() -> {
            repo.update(t);
            loadTransactions(); // refresh
        });
    }

    public void deleteTransaction(Transaction t) {
        Executors.newSingleThreadExecutor().execute(() -> {
            repo.delete(t);
            loadTransactions(); // refresh
        });
    }
}

