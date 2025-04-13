package net.jacza.expenses.data.repository;

import android.content.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import net.jacza.expenses.data.base.DataSource;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.base.TextFileHandler;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.raw.RawCategory;

/*
 * // TODO JavaDOC
 */
public class CategoriesRepository implements Repository<Category> {

    private DataSource<RawCategory> rawCategoriesSource;

    public CategoriesRepository(Context context) {
        this.rawCategoriesSource = new RawCategoriesSource(context);
    }

    @Override
    public void create(Category entry) {
        var categories = read();
        categories.add(entry);
        write(categories);
    }

    @Override
    public ArrayList<Category> read() {
        var categories = new ArrayList<Category>();
        var rawCategories = rawCategoriesSource.load();
        for (RawCategory rawCategory : rawCategories) {
            categories.add(new Category(rawCategory.getID(), rawCategory.getNAME()));
        }
        return categories;
    }

    @Override
    public void update(Category entry) {
        var categories = read();
        categories.replaceAll(item -> item.getID().equals(entry.getID()) ? entry : item);
        write(categories);
    }

    @Override
    public void delete(Category entry) {
        var categories = read();
        categories.removeIf(item -> item.getID().equals(entry.getID()));
        write(categories);
    }

    private void write(ArrayList<Category> categories) {
        var rawCategories = new ArrayList<RawCategory>();
        for (Category category : categories) {
            rawCategories.add(new RawCategory(category.getID(), category.getName()));
        }
        rawCategoriesSource.save(rawCategories);
    }
}

/*
 * // TODO JavaDOC
 */
class RawCategoriesSource extends TextFileHandler implements DataSource<RawCategory> {

    private static final String FILE_NAME = "categories.csv";

    private static final String DELIMITER = ";";

    public RawCategoriesSource(Context context) {
        super(new File(context.getFilesDir(), FILE_NAME));
    }

    @Override
    public ArrayList<RawCategory> load() {
        var rawCategories = new ArrayList<RawCategory>();
        var lines = super.readLines();
        for (String line : lines) {
            rawCategories.add(parse(line));
        }
        return rawCategories;
    }

    @Override
    public void save(ArrayList<RawCategory> rawCategories) {
        var lines = new ArrayList<String>();
        for (RawCategory rawCategory : rawCategories) {
            lines.add(serialize(rawCategory));
        }
        super.writeLines(lines);
    }

    private String serialize(RawCategory object) {
        return String.format("%s" + DELIMITER + "%s", object.getID().toString(), object.getNAME());
    }

    private RawCategory parse(String line) {
        var fields = line.split(DELIMITER);
        UUID ID = UUID.fromString(fields[0]);
        String NAME = fields[1];
        return new RawCategory(ID, NAME);
    }
}
