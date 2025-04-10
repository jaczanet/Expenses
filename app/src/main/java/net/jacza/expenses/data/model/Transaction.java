package net.jacza.expenses.data.model;

import java.time.LocalDate;
import java.util.UUID;
import net.jacza.expenses.data.interfaces.Identifiable;

/*
 * Data class wrapping transaction data.
 */
public class Transaction implements Identifiable {

    private final UUID ID;

    private LocalDate date;
    private Double amount;
    private String note;
    private Category category;
    private Account account;

    public Transaction(LocalDate date, Double amount, String note, Category category, Account account) {
        this.ID = UUID.randomUUID();
        this.date = date;
        this.amount = amount;
        this.note = note;
        this.category = category;
        this.account = account;
    }

    @Override
    public UUID getID() {
        return ID;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getAmount() {
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
}
