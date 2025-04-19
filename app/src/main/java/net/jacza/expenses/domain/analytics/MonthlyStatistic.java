package net.jacza.expenses.domain.analytics;

import java.util.ArrayList;

public class MonthlyStatistic {

    private int year;
    private Month month;
    private ArrayList<CategoryWithAmount> categoriesWithAmount;

    public MonthlyStatistic(int year, Month month) {
        this.year = year;
        this.month = month;
        this.categoriesWithAmount = new ArrayList<>();
    }

    public int getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public ArrayList<CategoryWithAmount> getCategoriesWithAmount() {
        return categoriesWithAmount;
    }
}
