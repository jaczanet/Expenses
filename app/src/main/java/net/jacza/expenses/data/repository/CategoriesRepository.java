package net.jacza.expenses.data.repository;

import android.content.Context;
import java.util.ArrayList;
import net.jacza.expenses.data.base.DataSource;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.raw.RawCategory;
import net.jacza.expenses.data.source.RawCategoriesDataSource;

/*
 * // TODO JavaDOC
 */
public class CategoriesRepository implements Repository<Category> {

    private DataSource<RawCategory> rawCategoriesSource;

    public CategoriesRepository(Context context) {
        this.rawCategoriesSource = new RawCategoriesDataSource(context);
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
            rawCategories.add(RawCategory.fromCategory(category));
        }
        rawCategoriesSource.save(rawCategories);
    }
}
