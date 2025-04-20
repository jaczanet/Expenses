package net.jacza.expenses.domain.analytics;

import net.jacza.expenses.data.model.Category;

/**
 * Data class associating a category with a double.
 */
public class CategoryWithAmount {

    // attributes

    private final Category CATEGORY;
    private final double AMOUNT;

    // constructor

    public CategoryWithAmount(Category CATEGORY, double AMOUNT) {
        this.CATEGORY = CATEGORY;
        this.AMOUNT = AMOUNT;
    }

    // getters

    public Category getCATEGORY() {
        return CATEGORY;
    }

    public double getAMOUNT() {
        return AMOUNT;
    }
}
