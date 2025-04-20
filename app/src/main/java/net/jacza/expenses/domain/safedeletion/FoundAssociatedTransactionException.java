package net.jacza.expenses.domain.safedeletion;

import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.model.Category;

/**
 * Custom exception for control flow in safe deletion logic.
 */
public class FoundAssociatedTransactionException extends Exception {

    private static final String MESSAGE_FORMAT =
        "%s %s is currently associated with one or more transactions";

    public FoundAssociatedTransactionException(Category category) {
        super(String.format(MESSAGE_FORMAT, "Category", category.getName()));
    }

    public FoundAssociatedTransactionException(Account account) {
        super(String.format(MESSAGE_FORMAT, "Account", account.getName()));
    }
}
