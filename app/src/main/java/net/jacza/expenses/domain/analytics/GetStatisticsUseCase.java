package net.jacza.expenses.domain.analytics;

import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.ZonedDateTime;
import java.time.ZoneId;

public class GetStatisticsUseCase {

    private static ZoneId timezone = ZoneId.of("Europe/Helsinki");

    private static final Repository<Transaction> TRANSACTION_REPOSITORY = TransactionsRepository.getInstance();

    private GetStatisticsUseCase(){}

    public static ArrayList<MonthlyStatistic> getMonthlyStatistics(){
        var monthlyStats = new ArrayList<MonthlyStatistic>();

        var transactions = TRANSACTION_REPOSITORY.read();

        var transGroupedByYear = groupTransactionsByYear(transactions);
        for(int year : transGroupedByYear.keySet()){
            var currentYearTrans = transGroupedByYear.get(year);

            var currentYearTransGroupedByMonth = groupTransactionsByMonth(currentYearTrans);
            for(int month : currentYearTransGroupedByMonth.keySet()){
                var currentYearMonthTrans = currentYearTransGroupedByMonth.get(month);

                var monthStat = new MonthlyStatistic(year, month);

                var amountByCategory = new HashMap<Category, Double>;
                for(var transaction : currentYearMonthTrans){
                    var category = transaction.getCategory();
                    var amount = transaction.getAmount();
                    amountByCategory.merge(category, amount, (a, b) -> a+b);
                }

                for(var category : amountByCategory.keySet()){
                    double amount = amountByCategory.get(category);
                    if(amount != 0){
                       monthStat.addCategoryWithAmount(new CategoryWithAmount(category, amount));
                    }
                }

                monthlyStats.add(monthStat);
            }
        }

        return monthlyStats;
    }

    // helper functions
    private static HashMap<Integer, ArrayList<Transaction>> groupTransactionsByYear(ArrayList<Transaction> transactions){
        var transByYear = new HashMap<Integer, ArrayList<Transaction>>;

        for(var tran : transactions){
            int year = getYearFromTransaction(tran);

            if (!transByYear.containsKey(year)) {
                transByYear.put(year, new ArrayList<>());
            }

            transByYear.get(year).add(tran);
        }

        return transByYear;
    }

    private static int getYearFromTransaction(Transaction transaction){
        Instant instant = Instant.ofEpochMilli(transaction.getTimestamp());
        ZonedDateTime dateTime = instant.atZone(timezone);
        int year = dateTime.getYear();
        return year;
    }

    private static HashMap<Integer, ArrayList<Transaction>> groupTransactionsByMonth(ArrayList<Transaction> transactions){
        var transByYear = new HashMap<Integer, ArrayList<Transaction>>;

        for(var tran : transactions){
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
        ZonedDateTime dateTime = instant.atZone(timezone);
        int month = dateTime.getMonthValue();
        return month;
    }

}
