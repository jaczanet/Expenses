package net.jacza.expenses.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.jacza.expenses.R;
import net.jacza.expenses.domain.analytics.GetStatisticsUseCase;
import net.jacza.expenses.ui.adapter.StatisticsAdapter;

public class StatisticsActivity extends AppCompatActivity {
    private Button  backBtn;
    private RecyclerView recyclerView;
    private StatisticsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpEdgeToEdge();

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.statsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StatisticsAdapter(GetStatisticsUseCase.getMonthlyStatistics());
        recyclerView.setAdapter(adapter);



    }

    private void setUpEdgeToEdge(){
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}