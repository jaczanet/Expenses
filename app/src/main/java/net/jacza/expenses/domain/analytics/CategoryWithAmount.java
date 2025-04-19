package net.jacza.expenses.domain.analytics;

import net.jacza.expenses.data.model.Category;

public class CategoryWithAmount {

    private final Category CATEGORY;
    private final double AMOUNT;

    public CategoryWithAmount(Category CATEGORY, double AMOUNT) {
        this.CATEGORY = CATEGORY;
        this.AMOUNT = AMOUNT;
    }

    public Category getCATEGORY() {
        return CATEGORY;
    }

    public double getAMOUNT() {
        return AMOUNT;
    }
}
