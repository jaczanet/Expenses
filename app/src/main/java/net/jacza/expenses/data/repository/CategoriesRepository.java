package net.jacza.expenses.data.repository;

import java.util.ArrayList;
import net.jacza.expenses.data.base.DataSource;
import net.jacza.expenses.data.common.IdentifiableRepository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.raw.RawCategory;
import net.jacza.expenses.data.source.RawCategoriesDataSource;

/*
 * Repository class that implements CRUD functionality for Category.
 */
public class CategoriesRepository extends IdentifiableRepository<Category> {

    // Singleton pattern with eager initialization

    private static final CategoriesRepository instance = new CategoriesRepository();

    public static CategoriesRepository getInstance() {
        return instance;
    }

    private CategoriesRepository() {}

    // data sources

    private DataSource<RawCategory> rawCategoriesSource = new RawCategoriesDataSource();

    // repository methods

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
    public void delete(Category entry) {
        var categories = read();
        categories.removeIf(item -> item.getID().equals(entry.getID()));
        write(categories);
    }

    protected void write(ArrayList<Category> categories) {
        var rawCategories = new ArrayList<RawCategory>();
        for (Category category : categories) {
            rawCategories.add(RawCategory.fromCategory(category));
        }
        rawCategories.sort((a, b) -> a.getNAME().compareTo(b.getNAME()));
        rawCategoriesSource.save(rawCategories);
    }
}
