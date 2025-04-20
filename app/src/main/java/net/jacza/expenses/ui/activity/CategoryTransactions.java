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
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;
import net.jacza.expenses.ui.adapter.TransactionAdapter;
import net.jacza.expenses.domain.GetFilteredTransactionsUseCase;
import net.jacza.expenses.ui.util.MarginTransactionDecoration;

/**
 * CategoryTransactions Activity
 *
 * This activity displays a list of transactions filtered by a specific category.
 * It receives a Category object as an extra from the previous activity.
 * It utilizes a RecyclerView to display the transactions and a custom adapter (TransactionAdapter).
 * It also handles the case where there are no transactions for the selected category.
 */
public class CategoryTransactions extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private Button backBtn;
    private TextView event;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_transactions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        Repository<Transaction> repository = TransactionsRepository.getINSTANCE();
        adapter = new TransactionAdapter(repository.read(), false,this);

        Category category = (Category) getIntent().getSerializableExtra("CATEGORY");

        recyclerView = findViewById(R.id.transactionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MarginTransactionDecoration(10));

        ArrayList<Transaction> transactions = GetFilteredTransactionsUseCase.filterByCategory(category);
        adapter.setList(transactions);

        event = findViewById(R.id.textViewEvent);
        event.setText(category.getName() + " transactions");

        if (transactions.size() == 0) {
            empty = findViewById(R.id.textViewEmpty);
            empty.setText("No transactions with this category yet");
        }
    }
}
