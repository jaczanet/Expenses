package net.jacza.expenses.domain.analytics;

import java.util.ArrayList;

public class MonthlyStatistic {

    private int year;
    private int month;
    private ArrayList<CategoryWithAmount> categoriesWithAmount;

    public MonthlyStatistic(int year, int month) {
        this.year = year;
        this.month = month;
        this.categoriesWithAmount = new ArrayList<>();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public ArrayList<CategoryWithAmount> getCategoriesWithAmount() {
        return categoriesWithAmount;
    }

    void addCategoryWithAmount(CategoryWithAmount categoryWithAmount){
        categoriesWithAmount.add(categoryWithAmount);
    }
}
