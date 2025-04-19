package net.jacza.expenses.data.source;

import java.util.UUID;
import net.jacza.expenses.data.common.LocalFileDataSource;
import net.jacza.expenses.data.raw.RawCategory;

/*
 * Data source of RawCategory, used by the repository layer to gather raw data.
 */
public class RawCategoriesDataSource extends LocalFileDataSource<RawCategory> {

    private static final String FILE_NAME = "categories.csv";

    public RawCategoriesDataSource() {
        super(FILE_NAME);
    }

    private String DELIMITER = ";";

    protected String serialize(RawCategory object) {
        return String.format(
            "%s" + DELIMITER + "%s",
            object.getID().toString(),
            object.getNAME().replace(DELIMITER, ",") // for parse safety
        );
    }

    protected RawCategory parse(String line) {
        var fields = line.split(DELIMITER);

        UUID ID = UUID.fromString(fields[0]);
        String NAME = fields[1];

        return new RawCategory(ID, NAME);
    }
}
