package net.jacza.expenses.data.source;

import java.io.File;
import java.util.UUID;
import net.jacza.expenses.App;
import net.jacza.expenses.data.common.LocalFileDataSource;
import net.jacza.expenses.data.raw.RawAccount;

/*
 * Data source of RawAccount, used by the repository layer to gather raw data.
 */
public class RawAccountsDataSource extends LocalFileDataSource<RawAccount> {

    private static final String FILE_NAME = "accounts-initial-balance.csv";

    public RawAccountsDataSource() {
        super(new File(App.getContext().getFilesDir(), FILE_NAME));
    }

    private static final String DELIMITER = ";";

    protected String serialize(RawAccount object) {
        return String.format(
            "%s" + DELIMITER + "%s" + DELIMITER + "%.2f",
            object.getID().toString(),
            object.getNAME().replace(DELIMITER, ","), // for parse safety
            object.getINITIAL_BALANCE()
        );
    }

    protected RawAccount parse(String line) {
        var fields = line.split(DELIMITER);

        UUID ID = UUID.fromString(fields[0]);
        String NAME = fields[1];
        double initialBalance = Double.parseDouble(fields[2]);

        return new RawAccount(ID, NAME, initialBalance);
    }
}
