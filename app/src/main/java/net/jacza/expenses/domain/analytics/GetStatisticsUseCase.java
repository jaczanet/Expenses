package net.jacza.expenses.domain.analytics;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;

/**
 * Class exposing methods to compute statistics.
 */
public class GetStatisticsUseCase {

    // repositories
    private static final Repository<Transaction> TRANSACTIONS_REPOSITORY =
        TransactionsRepository.getINSTANCE();

    // override the constructor to avoid instantiation
    private GetStatisticsUseCase() {}

    // exposed methods
    public static ArrayList<MonthlyStatistic> getMonthlyStatistics() {
        // initialize list of monthly statistics
        var monthlyStats = new ArrayList<MonthlyStatistic>();

        // load transactions from repository
        var transactions = TRANSACTIONS_REPOSITORY.read();

        // iterate over transactions for every year
        var transGroupedByYear = groupTransactionsByYear(transactions);
        for (int year : transGroupedByYear.keySet()) {
            var currentYearTrans = transGroupedByYear.get(year);

            // iterate over transactions of every month of the current year
            var currentYearTransGroupedByMonth = groupTransactionsByMonth(currentYearTrans);
            for (int month : currentYearTransGroupedByMonth.keySet()) {
                var currentYearMonthTrans = currentYearTransGroupedByMonth.get(month);

                // initialize monthly statistic
                var monthStat = new MonthlyStatistic(year, month);

                // compute transaction delta by category
                var amountByCategory = new HashMap<Category, Double>();
                for (var transaction : currentYearMonthTrans) {
                    var category = transaction.getCategory();
                    var amount = transaction.getAmount();
                    amountByCategory.merge(category, amount, (a, b) -> a + b);
                }

                // store all the non-empty deltas
                for (var category : amountByCategory.keySet()) {
                    double amount = amountByCategory.get(category);
                    if (amount != 0) {
                        monthStat.addCategoryWithAmount(new CategoryWithAmount(category, amount));
                    }
                }

                // store the monthly statistic only if it is not empty
                if (!monthStat.getCategoriesWithAmount().isEmpty()) {
                    monthlyStats.add(monthStat);
                }
            }
        }

        // sort from most recent to least recent
        monthlyStats.sort(
            (a, b) ->
                -1 *
                Integer.compare(a.getYEAR() * 100 + a.getMONTH(), b.getYEAR() * 100 + b.getMONTH())
        );

        return monthlyStats;
    }

    // helper functions
    private static final ZoneId TIMEZONE = ZoneId.systemDefault();

    private static HashMap<Integer, ArrayList<Transaction>> groupTransactionsByYear(
        ArrayList<Transaction> transactions
    ) {
        var transByYear = new HashMap<Integer, ArrayList<Transaction>>();

        for (var tran : transactions) {
            int year = getYearFromTransaction(tran);

            if (!transByYear.containsKey(year)) {
                transByYear.put(year, new ArrayList<>());
            }

            transByYear.get(year).add(tran);
        }

        return transByYear;
    }

    private static int getYearFromTransaction(Transaction transaction) {
        Instant instant = Instant.ofEpochMilli(transaction.getTimestamp());
        ZonedDateTime dateTime = instant.atZone(TIMEZONE);
        int year = dateTime.getYear();
        return year;
    }

    private static HashMap<Integer, ArrayList<Transaction>> groupTransactionsByMonth(
        ArrayList<Transaction> transactions
    ) {
        var transByYear = new HashMap<Integer, ArrayList<Transaction>>();

        for (var tran : transactions) {
            int month = getMonthFromTransaction(tran);

            if (!transByYear.containsKey(month)) {
                transByYear.put(month, new ArrayList<>());
            }

            transByYear.get(month).add(tran);
        }

        return transByYear;
    }

    private static int getMonthFromTransaction(Transaction transaction) {
        Instant instant = Instant.ofEpochMilli(transaction.getTimestamp());
        ZonedDateTime dateTime = instant.atZone(TIMEZONE);
        int month = dateTime.getMonthValue();
        return month;
    }
}
