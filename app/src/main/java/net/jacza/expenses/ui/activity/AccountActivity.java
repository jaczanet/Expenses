package net.jacza.expenses.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.ui.util.SaveBtnModes;
import net.jacza.expenses.ui.viewmodel.AccountViewModel;

public class AccountActivity extends AppCompatActivity {
    TextView tvActivityEvent;
    private EditText editTextAccountName;
    private EditText editTextAccountBalance;
    private Button saveBtn;
    private Button closeBtn;
    private AccountViewModel viewModel;
    private Account accountToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.accountActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        //Bind UI elements
        tvActivityEvent = findViewById(R.id.tvActivityEvent);
        editTextAccountName= findViewById(R.id.editTextAccountName);
        editTextAccountBalance= findViewById(R.id.editTextInitialBalance);
        closeBtn= findViewById(R.id.closeBtn);
        saveBtn= findViewById(R.id.saveBtn);

        SaveBtnModes mode = (SaveBtnModes) getIntent().getSerializableExtra("MODE");
        setActivityTextFields(mode, getIntent());

        saveBtn.setOnClickListener(v -> saveChanges(mode));

        closeBtn.setOnClickListener(v -> finish());
    }

    private void setActivityTextFields(SaveBtnModes mode, Intent intent){
        if (mode == SaveBtnModes.EDIT) {
            tvActivityEvent.setText("Edit Account");
            accountToEdit = (Account) intent.getSerializableExtra("ACCOUNT TO EDIT");
            if (accountToEdit != null) {
                editTextAccountName.setText(accountToEdit.getName());
                editTextAccountBalance.setText(String.valueOf(accountToEdit.getBalance()));
            }
        } else if(mode == SaveBtnModes.ADD){
            tvActivityEvent.setText("Add New Account");
        }
    }

    private void saveChanges(SaveBtnModes mode) {
        String name = editTextAccountName.getText().toString().trim();
        if (name.isEmpty()) {
            name = "N/A";
        }

        double balance;
        String balanceText = editTextAccountBalance.getText().toString().trim();
        if (balanceText.isEmpty()) {
            balance = 0.0;
        } else {
            try {
                balance = Double.parseDouble(balanceText);
            } catch (NumberFormatException e) {
                Log.e("AccountActivity", "Invalid balance format", e);
                balance = 0.0;
            }
        }

        if (mode == SaveBtnModes.EDIT && accountToEdit != null) {
            accountToEdit.setName(name);
            accountToEdit.setInitialBalance(balance);
            viewModel.update(accountToEdit);
        } else if (mode == SaveBtnModes.ADD) {
            Account newAccount = new Account(name, balance);
            viewModel.create(newAccount);
        }
        finish();
    }
}