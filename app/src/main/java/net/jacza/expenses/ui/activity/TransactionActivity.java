package net.jacza.expenses.ui.activity;

import android.os.Bundle;
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

    private Long selectedDateMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupEdgeToEdge();

        // Initialize UI components
        initUi();

        selectDateBtn.setOnClickListener(v -> showDatePicker());
        long finalDate = getValidDateInMillis();

        // Set up the save and close button
        closeBtn.setOnClickListener(v -> finish());
        saveBtn.setOnClickListener(v -> {
            saveChanges();
            finish();
        });
    }

    private void setupEdgeToEdge() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.transactionActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectedDateMillis = selection;
        });

        datePicker.show(getSupportFragmentManager(), "date_picker");
    }

    private Long getValidDateInMillis(){
        return (selectedDateMillis != null)
                ? selectedDateMillis
                : MaterialDatePicker.todayInUtcMilliseconds();
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