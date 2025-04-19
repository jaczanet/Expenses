package net.jacza.expenses.data.source;

import java.util.UUID;
import net.jacza.expenses.data.common.LocalFileDataSource;
import net.jacza.expenses.data.raw.RawTransaction;

/*
 * Data source of RawTransaction, used by the repository layer to gather raw data.
 */
public class RawTransactionsDataSource extends LocalFileDataSource<RawTransaction> {

    private static final String FILE_NAME = "transaction-history.csv";

    public RawTransactionsDataSource() {
        super(FILE_NAME);
    }

    private String DELIMITER = ";";

    protected String serialize(RawTransaction object) {
        return String.format(
            "%s" +
            DELIMITER +
            "%d" +
            DELIMITER +
            "%.2f" +
            DELIMITER +
            "%s" +
            DELIMITER +
            "%s" +
            DELIMITER +
            "%s",
            object.getID().toString(),
            object.getTIMESTAMP(),
            object.getAMOUNT(),
            object.getNOTE().replace(DELIMITER, ","), // for parse safety
            object.getCATEGORY_ID().toString(),
            object.getACCOUNT_ID().toString()
        );
    }

    protected RawTransaction parse(String line) {
        var fields = line.split(DELIMITER);

        UUID ID = UUID.fromString(fields[0]);
        long timestamp = Long.parseLong(fields[1]);
        double amount = Double.parseDouble(fields[2]);
        String note = fields[3];
        UUID categoryID = UUID.fromString(fields[4]);
        UUID accountID = UUID.fromString(fields[5]);

        return new RawTransaction(ID, timestamp, amount, note, categoryID, accountID);
    }
}
