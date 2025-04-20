package net.jacza.expenses.data.model;

import java.io.Serializable;
import java.util.UUID;
import net.jacza.expenses.data.base.Identifiable;

/**
 * Data class wrapping transaction data.
 */
public class Transaction implements Identifiable, Serializable {

    // attributes

    private final UUID ID;
    private long timestamp;
    private double amount;
    private String note;
    private Category category;
    private Account account;

    // constructors

    public Transaction(
        long timestamp,
        double amount,
        String note,
        Category category,
        Account account
    ) {
        this(UUID.randomUUID(), timestamp, amount, note, category, account);
    }

    public Transaction(
        UUID ID,
        long timestamp,
        double amount,
        String note,
        Category category,
        Account account
    ) {
        this.ID = ID;
        this.timestamp = timestamp;
        this.amount = amount;
        this.note = note != null ? note : "Transaction";
        this.category = category;
        this.account = account;
    }

    // getters

    @Override
    public UUID getID() {
        return ID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public Category getCategory() {
        return category;
    }

    public Account getAccount() {
        return account;
    }

    // setters

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
