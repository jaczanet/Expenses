package net.jacza.expenses.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.datepicker.MaterialDatePicker;
import java.util.List;
import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.AccountsRepository;
import net.jacza.expenses.data.repository.CategoriesRepository;
import net.jacza.expenses.data.repository.TransactionsRepository;
import net.jacza.expenses.ui.adapter.SelectCategoryAdapter;
import net.jacza.expenses.ui.interfaces.OnCategoryClickListener;
import net.jacza.expenses.ui.util.DecimalDigitsInputFilter;
import net.jacza.expenses.ui.util.SaveBtnModes;

/**
 * TransactionActivity allows users to add new transactions or edit existing ones.
 * It provides functionality to select the transaction's amount, date, associated account,
 * category, and a note.
 *
 * <p>
 * This activity handles both adding a new transaction and editing an existing one
 * based on the {@link SaveBtnModes} passed in the intent.
 * </p>
 *
 * <p>
 * <b>Key Features:</b>
 * <ul>
 *     <li><b>Add/Edit Transactions:</b> Supports adding new transactions and editing existing ones.</li>
 *     <li><b>Amount Input:</b> Allows entering the transaction amount with decimal precision.</li>
 *     <li><b>Date Selection:</b> Provides a date picker to select the transaction date.</li>
 *     <li><b>Account Selection:</b> Uses an AutoCompleteTextView to select the associated account.</li>
 *     <li><b>Category Selection:</b> Displays categories in a RecyclerView with a Flexbox layout for easy selection.</li>
 *     <li><b>Note:</b> Enables adding a note to the transaction.</li>
 *     <li><b>Validation:</b> Checks for the existence of at least one account and category when adding a new transaction.</li>
 * </ul>
 */
public class TransactionActivity extends AppCompatActivity implements OnCategoryClickListener {

    private Button closeBtn;
    private Button saveBtn;
    private Button selectDateBtn;
    private TextView tvActivityEvent;
    private EditText etTransactionAmount;
    private EditText note;
    private AutoCompleteTextView autoCompleteAccount;
    private RecyclerView categoriesRecyclerView;

    private Long selectedDateMillis;
    private Transaction transactionToEdit;
    private Account selectedAccount;
    private Category selectedCategory;
    private SaveBtnModes mode;
    private List<Category> categories;
    private SelectCategoryAdapter adapter;
    private Repository<Category> categoryRepo = CategoriesRepository.getINSTANCE();
    private Repository<Account> accountRepo = AccountsRepository.getINSTANCE();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupEdgeToEdge();

        mode = (SaveBtnModes) getIntent().getSerializableExtra("MODE");

        // Initialize UI components
        initUi();
        setAmountInputFilter();
        setAutoCompAccount();
        setActivityTextFields(mode, getIntent());
        setFlexBoxLayout();
        selectDateBtn.setOnClickListener(v -> showDatePicker());

        // Initialize selectedDateMillis
        if (mode == SaveBtnModes.EDIT && transactionToEdit != null) {
            selectedDateMillis = transactionToEdit.getTimestamp();
        } else {
            selectedDateMillis = MaterialDatePicker.todayInUtcMilliseconds();
        }

        // Set up the save and close button
        closeBtn.setOnClickListener(v -> finish());
        saveBtn.setOnClickListener(v -> prepareTransactionAndSave());
    }

    private void setupEdgeToEdge() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.transactionActivity),
            (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            }
        );
    }

    private void initUi() {
        tvActivityEvent = findViewById(R.id.tvActivityEvent);
        closeBtn = findViewById(R.id.closeBtn);
        saveBtn = findViewById(R.id.saveBtn);
        selectDateBtn = findViewById(R.id.selectDateBtn);
        etTransactionAmount = findViewById(R.id.etTransactionAmount);
        autoCompleteAccount = findViewById(R.id.autoCompleteAccount);
        note = findViewById(R.id.etNote);
        categoriesRecyclerView = findViewById(R.id.displayingCategoriesRecyclerView);
    }

    private void setActivityTextFields(SaveBtnModes mode, Intent intent) {
        if (mode == SaveBtnModes.EDIT) {
            tvActivityEvent.setText("Edit Transaction");
            transactionToEdit = (Transaction) intent.getSerializableExtra("TRANSACTION TO EDIT");
            if (transactionToEdit != null) {
                etTransactionAmount.setText(String.valueOf(transactionToEdit.getAmount()));
                if (transactionToEdit.getAccount() != null) {
                    autoCompleteAccount.setText(transactionToEdit.getAccount().getName(), false);
                    selectedAccount = transactionToEdit.getAccount();
                }
                note.setText(transactionToEdit.getNote());
                selectedCategory = transactionToEdit.getCategory();
            }
        } else if (mode == SaveBtnModes.ADD) {
            tvActivityEvent.setText("Add New Transaction");
            List<Account> accounts = accountRepo.read();
            if (!accounts.isEmpty()) {
                selectedAccount = accounts.get(0);
                autoCompleteAccount.setText(selectedAccount.getName(), false);
            } else {}
            List<Category> categories = categoryRepo.read();
            if (!categories.isEmpty()) {
                selectedCategory = categories.get(0);
            }
        }
    }

    private void setAmountInputFilter() {
        etTransactionAmount.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(10, 2) });
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(getValidDateInMillis())
            .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectedDateMillis = selection;
        });

        datePicker.show(getSupportFragmentManager(), "date_picker");
    }

    private Long getValidDateInMillis() {
        return (selectedDateMillis != null)
            ? selectedDateMillis
            : (transactionToEdit != null
                    ? transactionToEdit.getTimestamp()
                    : MaterialDatePicker.todayInUtcMilliseconds());
    }

    private void setAutoCompAccount() {
        List<Account> accountList = AccountsRepository.getINSTANCE().read();
        List<String> accountNames = accountList
            .stream()
            .map(Account::getName)
            .collect(java.util.stream.Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            accountNames
        );
        autoCompleteAccount.setAdapter(adapter);
        autoCompleteAccount.setThreshold(1);

        autoCompleteAccount.setOnItemClickListener((parent, view, position, id) -> {
            selectedAccount = accountList.get(position);
        });
    }

    private void setFlexBoxLayout() {
        categories = CategoriesRepository.getINSTANCE().read();
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        categoriesRecyclerView.setLayoutManager(layoutManager);

        adapter = new SelectCategoryAdapter(categories, this);
        if (!categories.isEmpty()) {
            if(mode == SaveBtnModes.ADD) {
                adapter.setSelectedCategory(categories.get(0));
            } else {
                adapter.setSelectedCategory(transactionToEdit.getCategory());
            }
        }
        categoriesRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(Category category) {
        selectedCategory = category;
    }

    private void prepareTransactionAndSave() {
        if (mode == SaveBtnModes.EDIT && transactionToEdit != null) {
            updateTransaction();
            saveTransaction(transactionToEdit);
        } else if (mode == SaveBtnModes.ADD) {
            List<Account> accounts = accountRepo.read();
            List<Category> categories = categoryRepo.read();

            if (accounts.isEmpty() || categories.isEmpty()) {
                String message = "";
                if (accounts.isEmpty() && categories.isEmpty()) {
                    message =
                        "Please create at least one Account and one Category to add a transaction.";
                } else if (accounts.isEmpty()) {
                    message = "Please create at least one Account to add a transaction.";
                } else if (categories.isEmpty()) {
                    message = "Please create at least one Category to add a transaction.";
                }
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                return;
            }

            Transaction newTransaction = createNewTransaction();
            if (newTransaction != null) {
                saveTransaction(newTransaction);
            }
        }
    }

    private void updateTransaction() {
        if (selectedAccount != null && !selectedAccount.equals(transactionToEdit.getAccount())) {
            transactionToEdit.setAccount(selectedAccount);
        }
        String currentAmountText = etTransactionAmount.getText().toString();
        if (!String.valueOf(transactionToEdit.getAmount()).equals(currentAmountText)) {
            transactionToEdit.setAmount(Double.parseDouble(currentAmountText));
        }
        String currentNoteText = note.getText().toString();
        if (!transactionToEdit.getNote().equals(currentNoteText)) {
            transactionToEdit.setNote(currentNoteText);
        }
        if (selectedCategory != null && !selectedCategory.equals(transactionToEdit.getCategory())) {
            transactionToEdit.setCategory(selectedCategory);
        }
        if (
            selectedDateMillis != null &&
            !selectedDateMillis.equals(transactionToEdit.getTimestamp())
        ) {
            transactionToEdit.setTimestamp(selectedDateMillis);
        }
    }

    private Long generateTimestampForNewTransaction() {
        Long timestamp;
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(selectedDateMillis);

        java.util.Calendar now = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.HOUR_OF_DAY, now.get(java.util.Calendar.HOUR_OF_DAY));
        calendar.set(java.util.Calendar.MINUTE, now.get(java.util.Calendar.MINUTE));
        calendar.set(java.util.Calendar.SECOND, now.get(java.util.Calendar.SECOND));
        calendar.set(java.util.Calendar.MILLISECOND, now.get(java.util.Calendar.MILLISECOND));

        return timestamp = calendar.getTimeInMillis();
    }

    private Transaction createNewTransaction() {
        String noteText = note.getText().toString();
        double amount = 0.0;
        if (!etTransactionAmount.getText().toString().isEmpty()) {
            amount = Double.parseDouble(etTransactionAmount.getText().toString());
        }

        Account account = selectedAccount;
        Category category = selectedCategory;
        Long timestamp = generateTimestampForNewTransaction();

        return new Transaction(timestamp, amount, noteText, category, account);
    }

    private void saveTransaction(Transaction transactionToSave) {
        Repository<Transaction> repo = TransactionsRepository.getINSTANCE();
        if (mode == SaveBtnModes.EDIT) {
            repo.update(transactionToSave);
        } else if (mode == SaveBtnModes.ADD) {
            System.out.println(transactionToSave);
            repo.create(transactionToSave);
        }
        finish();
    }
}
