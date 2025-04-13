package net.jacza.expenses.data.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import net.jacza.expenses.data.base.Identifiable;

/*
 * Data class wrapping transaction data.
 */
public class Transaction implements Identifiable, Serializable {

    // attributes

    private final UUID ID;
    private LocalDate date;
    private double amount;
    private String note;
    private Category category;
    private Account account;

    // constructors

    public Transaction(
        LocalDate date,
        double amount,
        String note,
        Category category,
        Account account
    ) {
        this(UUID.randomUUID(), date, amount, note, category, account);
    }

    public Transaction(
        UUID ID,
        LocalDate date,
        double amount,
        String note,
        Category category,
        Account account
    ) {
        this.ID = ID;
        this.date = date != null ? date : LocalDate.now();
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

    public LocalDate getDate() {
        return date;
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

    public void setDate(LocalDate date) {
        this.date = date;
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
