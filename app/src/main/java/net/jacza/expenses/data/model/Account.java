package net.jacza.expenses.data.model;

import java.io.Serializable;
import java.util.UUID;
import net.jacza.expenses.data.base.Identifiable;

/*
 * Data class wrapping account data.
 */
public class Account implements Identifiable, Serializable {

    // attributes

    private final UUID ID;
    private String name;
    private double initialBalance;
    private double balance;

    // constructors

    public Account(String name, double initialBalance) {
        this(UUID.randomUUID(), name, initialBalance);
    }

    public Account(UUID ID, String name, double initialBalance) {
        this.ID = ID;
        this.name = name != null ? name : "Account";
        this.initialBalance = initialBalance;
        this.balance = initialBalance;
    }

    // getters

    @Override
    public UUID getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    // setters

    public void setName(String name) {
        this.name = name;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    // methods

    public void updateBalance(double amount) {
        balance += amount;
    }
}
