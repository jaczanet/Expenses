package net.jacza.expenses.domain.safedeletion;

import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.AccountsRepository;
import net.jacza.expenses.data.repository.CategoriesRepository;
import net.jacza.expenses.data.repository.TransactionsRepository;

/**
 * Class exposing methods for safe deletion of objects from the repository.
 */
public class SafeDeleteUseCase {

    // repositories
    private static final Repository<Transaction> TRANSACTIONS_REPOSITORY =
        TransactionsRepository.getINSTANCE();
    private static final Repository<Category> CATEGORIES_REPOSITORY =
        CategoriesRepository.getINSTANCE();
    private static final Repository<Account> ACCOUNTS_REPOSITORY = AccountsRepository.getINSTANCE();

    // override the constructor to avoid instantiation
    private SafeDeleteUseCase() {}

    // exposed methods

    public static void deleteCategory(Category entry) throws FoundAssociatedTransactionException {
        var transactions = TRANSACTIONS_REPOSITORY.read();
        for (var tran : transactions) {
            var tranCategID = tran.getCategory().getID();
            if (tranCategID.equals(entry.getID())) {
                throw new FoundAssociatedTransactionException(entry);
            }
        }
        CATEGORIES_REPOSITORY.delete(entry);
    }

    public static void deleteAccount(Account entry) throws FoundAssociatedTransactionException {
        var transactions = TRANSACTIONS_REPOSITORY.read();
        for (var tran : transactions) {
            var tranAccID = tran.getAccount().getID();
            if (tranAccID.equals(entry.getID())) {
                throw new FoundAssociatedTransactionException(entry);
            }
        }
        ACCOUNTS_REPOSITORY.delete(entry);
    }
}
