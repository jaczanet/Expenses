package net.jacza.expenses.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.repository.CategoriesRepository;

import java.util.ArrayList;

public class CategoryViewModel extends AndroidViewModel {

    private final Repository<Category> repo;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repo = new CategoriesRepository(application.getApplicationContext());
    }

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

