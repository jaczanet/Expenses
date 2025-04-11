package net.jacza.expenses.data.repository;

import android.content.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import net.jacza.expenses.data.interfaces.DataSource;
import net.jacza.expenses.data.interfaces.Repository;
import net.jacza.expenses.data.model.Category;

/*
 * // TODO JacaDOC
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
            rawCategories.add(new RawCategory(category));
        }
        rawCategoriesSource.save(rawCategories);
    }
}

/*
 * // TODO JacaDOC
 */
class RawCategoriesSource extends TextFileHandler implements DataSource<RawCategory> {

    private static final String FILE_NAME = "categories.csv";

    public RawCategoriesSource(Context context) {
        super(new File(context.getFilesDir(), FILE_NAME));
    }

    @Override
    public ArrayList<RawCategory> load() {
        var rawCategories = new ArrayList<RawCategory>();
        var lines = super.readLines();
        for (String line : lines) {
            rawCategories.add(new RawCategory(line));
        }
        return rawCategories;
    }

    @Override
    public void save(ArrayList<RawCategory> rawCategories) {
        var lines = new ArrayList<String>();
        for (RawCategory rawCategory : rawCategories) {
            lines.add(rawCategories.toString());
        }
        super.writeLines(lines);
    }
}

/*
 * // TODO JacaDOC
 */
class RawCategory {

    private final UUID ID;
    private final String NAME;

    public RawCategory(Category category) {
        this.ID = category.getID();
        this.NAME = category.getName();
    }

    public RawCategory(String string) {
        var fields = string.split(";");
        this.ID = UUID.fromString(fields[0]);
        this.NAME = fields[1];
    }

    @Override
    public String toString() {
        return String.format("%s;%s", ID.toString(), NAME);
    }

    public UUID getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }
}
