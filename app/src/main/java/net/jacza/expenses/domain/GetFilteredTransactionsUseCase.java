package net.jacza.expenses.domain;

import java.util.ArrayList;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;

/**
 * Methods to get the list of transactions filtered by category or account.
 */
public class GetFilteredTransactionsUseCase {

    private static final Repository<Transaction> TRANSACTION_REPOSITORY =
        TransactionsRepository.getINSTANCE();

    // override the constructor to avoid instantiation
    private GetFilteredTransactionsUseCase() {}

    // static methods
    public static ArrayList<Transaction> filterByCategory(Category entry) {
        var transactions = TRANSACTION_REPOSITORY.read();
        transactions.removeIf(item -> !item.getCategory().getID().equals(entry.getID()));
        return transactions;
    }

    public static ArrayList<Transaction> filterByAccount(Account entry) {
        var transactions = TRANSACTION_REPOSITORY.read();
        transactions.removeIf(item -> !item.getAccount().getID().equals(entry.getID()));
        return transactions;
    }
}
