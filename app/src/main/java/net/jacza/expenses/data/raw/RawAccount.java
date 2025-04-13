package net.jacza.expenses.data.raw;

import java.util.UUID;
import net.jacza.expenses.data.model.Account;

/*
 * Immutable object exposed by the data source layer, providing a Raw version of Account.
 */
public class RawAccount {

    // attributes

    private final UUID ID;
    private final String NAME;
    private final double INITIAL_BALANCE;

    // constructors

    public RawAccount(UUID ID, String NAME, double INITIAL_BALANCE) {
        this.ID = ID;
        this.NAME = NAME;
        this.INITIAL_BALANCE = INITIAL_BALANCE;
    }

    // factory methods

    public static RawAccount fromAccount(Account account) {
        return new RawAccount(account.getID(), account.getName(), account.getInitialBalance());
    }

    // getters

    public UUID getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public double getINITIAL_BALANCE() {
        return INITIAL_BALANCE;
    }
}
