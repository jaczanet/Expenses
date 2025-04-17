package net.jacza.expenses.data.repository;

import java.util.ArrayList;
import net.jacza.expenses.App;
import net.jacza.expenses.data.base.DataSource;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.raw.RawCategory;
import net.jacza.expenses.data.source.RawCategoriesDataSource;

/*
 * Repository class that implements CRUD functionality for Category.
 */
public class CategoriesRepository implements Repository<Category> {

    // Singleton pattern with eager initialization

    private static final CategoriesRepository instance = new CategoriesRepository();

    public static CategoriesRepository getInstance() {
        return instance;
    }

    // Attributes

    private DataSource<RawCategory> rawCategoriesSource;

    // Constructors

    private CategoriesRepository() {
        this.rawCategoriesSource = new RawCategoriesDataSource(App.getContext());
    }

    // Methods

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
