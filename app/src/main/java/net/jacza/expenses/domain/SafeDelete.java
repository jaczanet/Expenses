package net.jacza.expenses.domain;

import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.AccountsRepository;
import net.jacza.expenses.data.repository.CategoriesRepository;
import net.jacza.expenses.data.repository.TransactionsRepository;

/*
 * Class defining logic for safe deletion of objects from the repository.
 */
public class SafeDelete {

    // repositories
    private static final Repository<Transaction> transRepo = TransactionsRepository.getInstance();
    private static final Repository<Category> categsRepo = CategoriesRepository.getInstance();
    private static final Repository<Account> accsRepo = AccountsRepository.getInstance();

    // override the constructor to avoid instantiation
    private SafeDelete() {}

    // static methods
    public static void category(Category entry) throws FoundAssociatedTransactionException {
        var transactions = transRepo.read();
        for (var tran : transactions) {
            var tranCategID = tran.getCategory().getID();
            if (tranCategID.equals(entry.getID())) {
                throw new FoundAssociatedTransactionException(entry);
            }
        }
        categsRepo.delete(entry);
    }

    public static void category(Account entry) throws FoundAssociatedTransactionException {
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
