package net.jacza.expenses.ui.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;
import net.jacza.expenses.ui.uistate.TransactionUiState;

import java.util.List;
import java.util.concurrent.Executors;

public class TransactionViewModel extends AndroidViewModel {
    private final MutableLiveData<TransactionUiState> _uiState = new MutableLiveData<>();
    private final LiveData<TransactionUiState> uiState = _uiState;

    private TransactionsRepository repo;
    private Context context;

    public TransactionViewModel(@NonNull Application application, Context context) {
        super(application);
        repo = new TransactionsRepository(context);
        loadTransactions(); // Load on init
    }

    public LiveData<TransactionUiState> getUiState() {
        return uiState;
    }

    private void loadTransactions() {
        _uiState.setValue(new TransactionUiState(
                true, null, null)); // Show loading

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<Transaction> transactions = repo.read();
                // Update UI State with fetched transactions
                _uiState.postValue(new TransactionUiState(
                        false, null, transactions));
            } catch (Exception e) {
                // Handle errors
                _uiState.postValue(new TransactionUiState(
                        false, "Failed to load data", null));
            }
        });
    }

    public void createTransaction(Transaction t) {
        _uiState.setValue(new TransactionUiState(
                true, null, null)); // Show loading

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                repo.create(t);
                loadTransactions(); // Reload after create
            } catch (Exception e) {
                _uiState.postValue(new TransactionUiState(
                        false, "Failed to create transaction", null));
            }
        });
    }

    public void updateTransaction(Transaction t) {
        _uiState.setValue(new TransactionUiState(
                true, null, null)); // Show loading

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                repo.update(t);
                loadTransactions(); // Reload after update
            } catch (Exception e) {
                _uiState.postValue(new TransactionUiState(
                        false, "Failed to update transaction", null));
            }
        });
    }

    public void deleteTransaction(Transaction t) {
        _uiState.setValue(new TransactionUiState(
                true, null, null)); // Show loading

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                repo.delete(t);
                loadTransactions(); // Reload after delete
            } catch (Exception e) {
                _uiState.postValue(new TransactionUiState(
                        false, "Failed to delete transaction", null));
            }
        });
    }
}


