package net.jacza.expenses.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.repository.CategoriesRepository;
import net.jacza.expenses.ui.util.SaveBtnModes;

/**
 * CategoryActivity is responsible for handling the creation and editing of Category objects.
 * It allows users to add a new category or modify an existing one.
 * It uses an EditText for category name input, and buttons for saving or closing.
 * The activity supports two modes: ADD and EDIT, determined by an intent extra.
 * Also supports deleting a category.
 */
public class CategoryActivity extends AppCompatActivity {

    Button saveBtn, closeBtn;
    private EditText editTextCategoryName;
    private TextView tvActivityEvent;

    private Category categoryToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.categoryActivity),
            (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            }
        );

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

    private void setActivityTextFields(SaveBtnModes mode, Intent intent) {
        if (mode == SaveBtnModes.EDIT) {
            tvActivityEvent.setText("Edit Category");
            categoryToEdit = (Category) intent.getSerializableExtra("CATEGORY TO EDIT");
            if (categoryToEdit != null) {
                editTextCategoryName.setText(categoryToEdit.getName());
            }
        } else if (mode == SaveBtnModes.ADD) {
            tvActivityEvent.setText("Add New Category");
        }
    }

    void saveChanges(SaveBtnModes mode) {
        String name = editTextCategoryName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Category name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }
        Repository<Category> repo = CategoriesRepository.getINSTANCE();
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
