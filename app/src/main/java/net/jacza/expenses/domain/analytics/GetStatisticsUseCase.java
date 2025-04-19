package net.jacza.expenses.domain.analytics;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;

public class GetStatisticsUseCase {

    private static ZoneId timezone = ZoneId.of("Europe/Helsinki");

    private static final Repository<Transaction> TRANSACTION_REPOSITORY =
        TransactionsRepository.getInstance();

    private GetStatisticsUseCase() {}

    public static ArrayList<MonthlyStatistics> getMonthlyStatistics() {
        var monthlyStats = new ArrayList<MonthlyStatistics>();

        return monthlyStats;
    }
}
