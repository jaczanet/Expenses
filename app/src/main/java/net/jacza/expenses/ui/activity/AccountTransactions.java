package net.jacza.expenses.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;
import net.jacza.expenses.ui.adapter.TransactionAdapter;
import net.jacza.expenses.domain.GetFilteredTransactionsUseCase;
import net.jacza.expenses.ui.util.MarginTransactionDecoration;

/**
 * AccountTransactions Activity
 *
 * This activity displays a list of transactions associated with a specific account.
 * It retrieves the account details from the intent, filters transactions related to that
 * account, and displays them in a RecyclerView. It also provides a back button to return
 * to the previous screen.
 * It also handles the case where there are no transactions for the selected account.
 */
public class AccountTransactions extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private Button backBtn;
    private TextView event;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_transactions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        Repository<Transaction> repository = TransactionsRepository.getINSTANCE();
        adapter = new TransactionAdapter(repository.read(), false,this);

        Account account = (Account) getIntent().getSerializableExtra("Account");

        recyclerView = findViewById(R.id.transactionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MarginTransactionDecoration(10));

        ArrayList<Transaction> transactions = GetFilteredTransactionsUseCase.filterByAccount(account);
        adapter.setList(transactions);

        event = findViewById(R.id.textViewEvent);
        event.setText(account.getName() + " transactions");

        if (transactions.size() == 0) {
            empty = findViewById(R.id.textViewEmpty);
            empty.setText("No transactions in this account yet");
        }
    }
}
