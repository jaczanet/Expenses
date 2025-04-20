package net.jacza.expenses.ui.util;

import java.util.ArrayList;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.repository.TransactionsRepository;

/*
 * Methods to get the list of transactions filtered by category or account.
 */
public class GetTransactionsUseCase {

    private static final Repository<Transaction> transRepo = TransactionsRepository.getINSTANCE();

    // override the constructor to avoid instantiation
    private GetTransactionsUseCase() {}

    // static methods
    public static ArrayList<Transaction> byCategory(Category entry) {
        var transactions = transRepo.read();
        transactions.removeIf(item -> !item.getCategory().getID().equals(entry.getID()));
        return transactions;
    }

    public static ArrayList<Transaction> byAccount(Account entry) {
        var transactions = transRepo.read();
        transactions.removeIf(item -> !item.getAccount().getID().equals(entry.getID()));
        return transactions;
    }
}
