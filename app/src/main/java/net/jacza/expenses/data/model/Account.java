package net.jacza.expenses.data.model;

import java.util.ArrayList;
import java.util.UUID;
import net.jacza.expenses.data.base.Identifiable;

/*
 * Data class wrapping account data.
 */
public class Account implements Identifiable {

    private final UUID ID;

    private String name;
    private Double initialBalance;
    private ArrayList<Transaction> transactions;

    public Account(String name, Double initialBalance) {
        this.ID = UUID.randomUUID();
        this.name = name;
        this.initialBalance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    @Override
    public UUID getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Double getBalance() {
        Double sum = initialBalance;
        for (Transaction transaction : transactions) {
            sum += transaction.getAmount();
        }
        return sum;
    }
}
