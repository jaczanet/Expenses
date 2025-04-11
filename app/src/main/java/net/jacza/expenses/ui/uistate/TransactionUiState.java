package net.jacza.expenses.ui.uistate;

import net.jacza.expenses.data.model.Transaction;

import java.util.List;

public class TransactionUiState {
    private boolean isLoading;
    private String errorMessage;
    private List<Transaction> transactions;

    public TransactionUiState(boolean isLoading, String errorMessage, List<Transaction> transactions) {
        this.isLoading = isLoading;
        this.errorMessage = errorMessage;
        this.transactions = transactions;
    }

    // Getters and setters

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}

