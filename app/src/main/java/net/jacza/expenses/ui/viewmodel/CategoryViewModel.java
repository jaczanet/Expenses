package net.jacza.expenses.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.repository.CategoriesRepository;

public class CategoryViewModel extends ViewModel {

    private final Repository<Category> repo = new CategoriesRepository();

    public void create(Category category) {
        repo.create(category);
    }

    public void delete(Category category) {
        repo.delete(category);
    }

    public ArrayList<Category> read() {
        return repo.read();
    }

    public void update(Category category) {
        repo.update(category);
    }
}
