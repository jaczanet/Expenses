package net.jacza.expenses.data.model;

import java.time.LocalDate;

public class Transaction {

    private LocalDate date;
    private Double amount;
    private String note;
    private Category category;
    private Account account;

    public Transaction(LocalDate date, Double amount, String note, Category category, Account account) {
        this.date = date;
        this.amount = amount;
        this.note = note;
        this.category = category;
        this.account = account;
    }
}
