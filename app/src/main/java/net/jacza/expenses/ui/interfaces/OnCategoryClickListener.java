package net.jacza.expenses.ui.interfaces;

import net.jacza.expenses.data.model.Category;

/**
 * Interface definition for a callback to be invoked when a category is clicked.
 */
public interface OnCategoryClickListener {
    void onCategoryClick(Category category);
}
