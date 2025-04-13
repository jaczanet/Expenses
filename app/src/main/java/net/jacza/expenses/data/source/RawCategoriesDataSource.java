package net.jacza.expenses.data.source;

import android.content.Context;
import java.io.File;
import java.util.UUID;
import net.jacza.expenses.data.common.LocalFileDataSource;
import net.jacza.expenses.data.raw.RawCategory;

/*
 * Data source of RawCategory, used by the repository layer to gather raw data.
 */
public class RawCategoriesDataSource extends LocalFileDataSource<RawCategory> {

    private static final String FILE_NAME = "categories.csv";

    private static final String DELIMITER = ";";

    public RawCategoriesDataSource(Context context) {
        super(new File(context.getFilesDir(), FILE_NAME));
    }

    protected String serialize(RawCategory object) {
        return String.format("%s" + DELIMITER + "%s", object.getID().toString(), object.getNAME());
    }

    protected RawCategory parse(String line) {
        var fields = line.split(DELIMITER);
        UUID ID = UUID.fromString(fields[0]);
        String NAME = fields[1];
        return new RawCategory(ID, NAME);
    }
}
