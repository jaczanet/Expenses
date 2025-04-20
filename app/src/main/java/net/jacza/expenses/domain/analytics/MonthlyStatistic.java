package net.jacza.expenses.domain.analytics;

import java.util.ArrayList;

/*
 * Data class wrapping monthly statistics data.
 */
public class MonthlyStatistic {

    // attributes

    private final int YEAR;
    private final int MONTH;
    private ArrayList<CategoryWithAmount> categoriesWithAmount;

    // constructor

    public MonthlyStatistic(int year, int month) {
        this.YEAR = year;
        this.MONTH = month;
        this.categoriesWithAmount = new ArrayList<>();
    }

    // getters

    public int getYEAR() {
        return YEAR;
    }

    public int getMONTH() {
        return MONTH;
    }

    public ArrayList<CategoryWithAmount> getCategoriesWithAmount() {
        categoriesWithAmount.sort((a, b) -> -1 * Double.compare(a.getAMOUNT(), b.getAMOUNT()));
        return categoriesWithAmount;
    }

    // methods

    public void addCategoryWithAmount(CategoryWithAmount categoryWithAmount) {
        categoriesWithAmount.add(categoryWithAmount);
    }
}
