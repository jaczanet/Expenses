package net.jacza.expenses.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.repository.CategoriesRepository;
import net.jacza.expenses.ui.viewmodel.CategoryViewModel;
import net.jacza.expenses.ui.util.SaveBtnModes;


public class CategoryActivity extends AppCompatActivity {
    Button saveBtn, closeBtn;
    private EditText editTextCategoryName;
    private TextView tvActivityEvent;
    private CategoryViewModel viewModel;

    private Category categoryToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categoryActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        // Bind UI elements
        tvActivityEvent = findViewById(R.id.tvActivityEvent);
        editTextCategoryName = findViewById(R.id.editTextCategoryName);
        closeBtn = findViewById(R.id.closeBtn);
        saveBtn = findViewById(R.id.saveBtn);

        SaveBtnModes mode = (SaveBtnModes) getIntent().getSerializableExtra("MODE");
        setActivityTextFields(mode, getIntent());

        // Set up save and close button click listeners
        saveBtn.setOnClickListener(v -> saveChanges(mode));
        closeBtn.setOnClickListener(v -> finish());
    }

    private void setActivityTextFields(SaveBtnModes mode, Intent intent){
        if (mode == SaveBtnModes.EDIT) {
            tvActivityEvent.setText("Edit Category");
            categoryToEdit = (Category) intent.getSerializableExtra("CATEGORY TO EDIT");
            if (categoryToEdit != null) {
                editTextCategoryName.setText(categoryToEdit.getName());
            }
        } else if (mode == SaveBtnModes.ADD){
            tvActivityEvent.setText("Add New Category");
        }
    }

    void saveChanges(SaveBtnModes mode) {
        String name = editTextCategoryName.getText().toString().trim();
        if (name.isEmpty()) {
            name = "N/A";
        }
        Repository<Category> repo = CategoriesRepository.getInstance();
        if (mode == SaveBtnModes.EDIT && categoryToEdit != null) {
            categoryToEdit.setName(name);
            repo.update(categoryToEdit);
        } else if (mode == SaveBtnModes.ADD) {
            Category newCategory = new Category(name);
            repo.create(newCategory);
        }
        finish();
    }
}
