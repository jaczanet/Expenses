package net.jacza.expenses.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.jacza.expenses.R;
import net.jacza.expenses.domain.analytics.MonthlyStatistic;

import java.text.DateFormatSymbols;
import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatsViewHolder> {

    private List<MonthlyStatistic> monthlyStatistics;
    public StatisticsAdapter(List<MonthlyStatistic> monthlyStatistics) {
        this.monthlyStatistics = monthlyStatistics;
    }

    @NonNull
    @Override
    public StatisticsAdapter.StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statistics_card, parent, false);
        return new StatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsAdapter.StatsViewHolder holder, int position) {
        holder.bind(monthlyStatistics.get(position));
    }

    @Override
    public int getItemCount() {
        return monthlyStatistics.size();
    }

    public static class StatsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate;
        RecyclerView recyclerView;
        public StatsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            recyclerView = itemView.findViewById(R.id.statsCategRecyclerView);
        }

        public void bind(MonthlyStatistic monthlyStatistic){
            int monthNumber = monthlyStatistic.getMonth();
            String month = new DateFormatSymbols().getMonths()[monthNumber - 1];

            textViewDate.setText( month + " " + monthlyStatistic.getYear());

            CategoryWithAmountAdapter adapter = new CategoryWithAmountAdapter(monthlyStatistic.getCategoriesWithAmount());
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerView.setAdapter(adapter);
        }

    }

}
