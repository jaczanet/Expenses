package net.jacza.expenses.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
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
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.repository.AccountsRepository;
import net.jacza.expenses.ui.util.DecimalDigitsInputFilter;
import net.jacza.expenses.ui.util.SaveBtnModes;

/**
 * AccountActivity: This class is responsible for handling the creation and editing of Account objects.
 * It provides a user interface to input or modify account details such as the account name and initial balance.
 * It supports two modes of operation:
 *  - ADD: Allows the creation of a new Account.
 *  - EDIT: Allows the modification of an existing Account.
 * The activity uses the AccountsRepository to persist the changes made to the accounts.
 */
public class AccountActivity extends AppCompatActivity {

    TextView tvActivityEvent;
    private EditText editTextAccountName;
    private EditText editTextAccountBalance;
    private Button saveBtn;
    private Button closeBtn;
    private Account accountToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupEdgeToEdge();

        initUi();
        setAmountInputFilter();

        SaveBtnModes mode = (SaveBtnModes) getIntent().getSerializableExtra("MODE");
        setActivityTextFields(mode, getIntent());

        saveBtn.setOnClickListener(v -> saveChanges(mode));
        closeBtn.setOnClickListener(v -> finish());
    }

    private void setupEdgeToEdge() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.accountActivity),
            (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            }
        );
    }

    private void initUi() {
        tvActivityEvent = findViewById(R.id.tvActivityEvent);
        editTextAccountName = findViewById(R.id.editTextAccountName);
        editTextAccountBalance = findViewById(R.id.editTextInitialBalance);
        closeBtn = findViewById(R.id.closeBtn);
        saveBtn = findViewById(R.id.saveBtn);
    }

    private void setActivityTextFields(SaveBtnModes mode, Intent intent) {
        if (mode == SaveBtnModes.EDIT) {
            tvActivityEvent.setText("Edit Account");
            accountToEdit = (Account) intent.getSerializableExtra("ACCOUNT TO EDIT");
            if (accountToEdit != null) {
                editTextAccountName.setText(accountToEdit.getName());
                editTextAccountBalance.setText(String.valueOf(accountToEdit.getInitialBalance()));
            }
        } else if (mode == SaveBtnModes.ADD) {
            tvActivityEvent.setText("Add New Account");
        }
    }

    private void setAmountInputFilter() {
        editTextAccountBalance.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(10, 2) });
    }

    private void saveChanges(SaveBtnModes mode) {
        String name = editTextAccountName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Account name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        double balance;
        String balanceText = editTextAccountBalance.getText().toString().trim();
        if (balanceText.isEmpty()) {
            balance = 0.0;
        } else {
            balance = Double.parseDouble(balanceText);
        }

        Repository<Account> repo = AccountsRepository.getINSTANCE();
        if (mode == SaveBtnModes.EDIT && accountToEdit != null) {
            accountToEdit.setName(name);
            accountToEdit.setInitialBalance(balance);
            repo.update(accountToEdit);
        } else if (mode == SaveBtnModes.ADD) {
            Account newAccount = new Account(name, balance);
            repo.create(newAccount);
        }
        finish();
    }
}
