package net.jacza.expenses.domain.analytics;

import java.util.HashMap;
import net.jacza.expenses.data.model.Category;

public class MonthlyStatistics {

    private int year;
    private Month month;
    private HashMap<Double, Category> categoryByAmount;

    public MonthlyStatistics(int year, Month month, HashMap<Double, Category> categoryByAmount) {
        this.year = year;
        this.month = month;
        this.categoryByAmount = categoryByAmount;
    }

    public int getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public HashMap<Double, Category> getCategoryByAmount() {
        return categoryByAmount;
    }
}
