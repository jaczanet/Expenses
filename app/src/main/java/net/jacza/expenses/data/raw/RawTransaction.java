package net.jacza.expenses.data.raw;

import java.util.UUID;
import net.jacza.expenses.data.model.Transaction;

/**
 * Immutable object exposed by the data source layer, providing a Raw version of Transaction.
 */
public class RawTransaction {

    // attributes

    private final UUID ID;
    private final long TIMESTAMP;
    private final double AMOUNT;
    private final String NOTE;
    private final UUID CATEGORY_ID;
    private final UUID ACCOUNT_ID;

    // constructors

    public RawTransaction(
        UUID ID,
        long TIMESTAMP,
        double AMOUNT,
        String NOTE,
        UUID CATEGORY_ID,
        UUID ACCOUNT_ID
    ) {
        this.ID = ID;
        this.TIMESTAMP = TIMESTAMP;
        this.AMOUNT = AMOUNT;
        this.NOTE = NOTE;
        this.CATEGORY_ID = CATEGORY_ID;
        this.ACCOUNT_ID = ACCOUNT_ID;
    }

    // factory methods

    public static RawTransaction fromTransaction(Transaction transaction) {
        return new RawTransaction(
            transaction.getID(),
            transaction.getTimestamp(),
            transaction.getAmount(),
            transaction.getNote(),
            transaction.getCategory().getID(),
            transaction.getAccount().getID()
        );
    }

    // getters

    public UUID getID() {
        return ID;
    }

    public long getTIMESTAMP() {
        return TIMESTAMP;
    }

    public double getAMOUNT() {
        return AMOUNT;
    }

    public String getNOTE() {
        return NOTE;
    }

    public UUID getCATEGORY_ID() {
        return CATEGORY_ID;
    }

    public UUID getACCOUNT_ID() {
        return ACCOUNT_ID;
    }
}
