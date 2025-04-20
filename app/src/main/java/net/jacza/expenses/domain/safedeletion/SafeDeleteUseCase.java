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
    private static final Repository<Transaction> transRepo = TransactionsRepository.getINSTANCE();
    private static final Repository<Category> categsRepo = CategoriesRepository.getINSTANCE();
    private static final Repository<Account> accsRepo = AccountsRepository.getINSTANCE();

    // override the constructor to avoid instantiation
    private SafeDeleteUseCase() {}

    // exposed methods

    public static void deleteCategory(Category entry) throws FoundAssociatedTransactionException {
        var transactions = transRepo.read();
        for (var tran : transactions) {
            var tranCategID = tran.getCategory().getID();
            if (tranCategID.equals(entry.getID())) {
                throw new FoundAssociatedTransactionException(entry);
            }
        }
        categsRepo.delete(entry);
    }

    public static void deleteAccount(Account entry) throws FoundAssociatedTransactionException {
        var transactions = transRepo.read();
        for (var tran : transactions) {
            var tranAccID = tran.getAccount().getID();
            if (tranAccID.equals(entry.getID())) {
                throw new FoundAssociatedTransactionException(entry);
            }
        }
        accsRepo.delete(entry);
    }
}
