package net.jacza.expenses.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;

import net.jacza.expenses.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TransactionActivity extends AppCompatActivity {

    Button closeBtn;
    Button saveBtn;
    Button selectDateBtn;
    TextView event;
    EditText etTransactionAmount;
    TextInputLayout selectAccountMenu;
    LinearLayout NoteDateLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        initUi();

        // Set up the close button
        closeBtn.setOnClickListener(v -> finish());

        // Set up the save button
        saveBtn.setOnClickListener(v -> {
            // Save the changes
            saveChanges();
            finish();
        });

        selectDateBtn.setOnClickListener(v -> showDatePicker());

    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(selection));
        });

        datePicker.show(getSupportFragmentManager(), "date_picker");
    }


    private void initUi(){
        closeBtn = findViewById(R.id.closeBtn);
        saveBtn = findViewById(R.id.saveBtn);
        selectDateBtn = findViewById(R.id.selectDateBtn);
        event = findViewById(R.id.textView);
        etTransactionAmount = findViewById(R.id.etTransactionAmount);
        selectAccountMenu = findViewById(R.id.selectAccountMenu);
        NoteDateLinearLayout = findViewById(R.id.NoteDateLinearLayout);
    }

    private void saveChanges(){
        event.setText("Add New Transaction");
        double amount = Double.parseDouble(etTransactionAmount.getText().toString());
        String account = Objects.requireNonNull(selectAccountMenu.getEditText()).getText().toString();
        String note = Objects.requireNonNull(NoteDateLinearLayout.findViewById(R.id.etNote)).toString();
        String date = Objects.requireNonNull(selectDateBtn.getText()).toString();

    }

}