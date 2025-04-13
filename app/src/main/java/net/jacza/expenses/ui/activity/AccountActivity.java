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
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.ui.viewmodel.AccountViewModel;

public class AccountActivity extends AppCompatActivity {
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
        editTextAccountName= findViewById(R.id.editTextAccountName);
        editTextAccountBalance= findViewById(R.id.editTextInitialBalance);
        closeBtn= findViewById(R.id.closeBtn);
        saveBtn= findViewById(R.id.saveBtn);

        //Get intent data
        Intent intent = getIntent();
        String mode = intent.getStringExtra("MODE");

        if ("EDIT".equals(mode)) {
            accountToEdit = (Account) intent.getSerializableExtra("ACCOUNT TO EDIT");
            if (accountToEdit != null) {
                editTextAccountName.setText(accountToEdit.getName());
                editTextAccountBalance.setText(String.valueOf(accountToEdit.getBalance()));
            }
        }
        saveBtn.setOnClickListener(v -> {
            String name = editTextAccountName.getText().toString().trim();
            if (name.isEmpty()) {
                name = "N/A";
            }

            double balance = 0.0;
            try {
                balance = Double.parseDouble(editTextAccountBalance.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                balance = 0.0;
            }


//            if ("EDIT".equals(mode) && accountToEdit != null) {
//                accountToEdit.setName(name);
//                accountToEdit.setInitialBalance(balance);
//                viewModel.update(accountToEdit);
//            } else if ("ADD".equals(mode) && accountToEdit != null) {
//                Account newAccount = new Account(name, balance);
//                viewModel.create(newAccount);
//            }
//            finish(); TODO uncomment after implementing AccountsRepository
        });

        closeBtn.setOnClickListener(v -> finish());
    }
}