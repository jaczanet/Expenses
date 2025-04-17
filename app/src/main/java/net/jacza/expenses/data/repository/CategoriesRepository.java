package net.jacza.expenses.data.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
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

    private final DataSource<RawCategory> rawCategoriesSource = new RawCategoriesDataSource();

    // repository methods

    @Override
    public ArrayList<Category> read() {
        return new ArrayList<Category>(readMap().values());
    }

    HashMap<UUID, Category> readMap() {
        var IDmapCategory = new HashMap<UUID, Category>();

        // read from source
        var rawCategories = rawCategoriesSource.load();

        // convert
        for (RawCategory rawCategory : rawCategories) {
            var category = new Category(rawCategory.getID(), rawCategory.getNAME());
            IDmapCategory.put(category.getID(), category);
        }

        return IDmapCategory;
    }

    @Override
    public void delete(Category entry) {
        // TODO implement safe deletion
        super.delete(entry);
    }

    @Override
    protected void write(ArrayList<Category> categories) {
        var rawCategories = new ArrayList<RawCategory>();

        // convert
        for (Category category : categories) {
            rawCategories.add(RawCategory.fromCategory(category));
        }

        // sort
        rawCategories.sort((a, b) -> a.getNAME().compareTo(b.getNAME()));

        // write to source
        rawCategoriesSource.save(rawCategories);
    }
}
