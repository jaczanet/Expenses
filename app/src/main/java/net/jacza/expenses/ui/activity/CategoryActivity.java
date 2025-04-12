package net.jacza.expenses.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import net.jacza.expenses.R;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.ui.viewmodel.CategoryViewModel;

public class CategoryActivity extends AppCompatActivity {

    private EditText editTextCategoryName;
    private Button saveBtn, closeBtn;
    private CategoryViewModel viewModel;

    private Category categoryToEdit = null;

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
        editTextCategoryName = findViewById(R.id.editTextCategoryName);
        closeBtn = findViewById(R.id.closeBtn);
        saveBtn = findViewById(R.id.saveBtn);

        // Get intent data
        Intent intent = getIntent();
        String mode = intent.getStringExtra("MODE");

        if ("EDIT".equals(mode)) {
            categoryToEdit = (Category) intent.getSerializableExtra("CATEGORY TO EDIT");
            if (categoryToEdit != null) {
                editTextCategoryName.setText(categoryToEdit.getName());
            }
        }

        saveBtn.setOnClickListener(v -> {
            String name = editTextCategoryName.getText().toString().trim();
            if (name.isEmpty()) {
                name = "Unnamed Category";
            }

            if ("EDIT".equals(mode) && categoryToEdit != null) {
                categoryToEdit.setName(name);
                viewModel.update(categoryToEdit);
            } else if ("ADD".equals(mode)) {
                Category newCategory = new Category(name);
                viewModel.create(newCategory);
            }
            finish();
        });

        closeBtn.setOnClickListener(v -> finish());
    }
}
