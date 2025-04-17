package net.jacza.expenses.data.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import net.jacza.expenses.data.base.DataSource;
import net.jacza.expenses.data.common.IdentifiableRepository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.model.Transaction;
import net.jacza.expenses.data.raw.RawTransaction;
import net.jacza.expenses.data.source.RawTransactionsDataSource;

/*
 * Repository class that implements CRUD functionality for Transaction.
 */
public class TransactionsRepository extends IdentifiableRepository<Transaction> {

    // Singleton pattern with eager initialization

    private static final TransactionsRepository instance = new TransactionsRepository();

    public static TransactionsRepository getInstance() {
        return instance;
    }

    private TransactionsRepository() {}

    // data sources

    private final DataSource<RawTransaction> rawTransactionsSource = new RawTransactionsDataSource();

    // repository methods

    @Override
    public ArrayList<Transaction> read() {
        var transactions = new ArrayList<Transaction>(readMap().values());
        transactions.sort((a, b) -> -1 * Long.compare(a.getTimestamp(), b.getTimestamp())); // default sorting logic
        return transactions;
    }

    HashMap<UUID, Transaction> readMap() {
        var IDmapAccount = new HashMap<UUID, Transaction>();

        // read categories
        var categories = CategoriesRepository.getInstance().readMap();

        // read accounts
        var accounts = AccountsRepository.getInstance().readMap();

        // read from source
        var rawTransactions = rawTransactionsSource.load();

        // convert, inject category and account
        for (RawTransaction rawTransaction : rawTransactions) {
            // category extraction with fallback logic
            var categoryID = rawTransaction.getCATEGORY_ID();
            var category = categories.get(categoryID);
            if (category == null) {
                category = new Category(categoryID, (categoryID.toString()).substring(0, 8));
            }

            // account extraction with fallback logic
            var accountID = rawTransaction.getACCOUNT_ID();
            var account = accounts.get(accountID);
            if (account == null) {
                account = new Account(accountID, (accountID.toString()).substring(0, 8), 0);
            }

            var transaction = new Transaction(
                rawTransaction.getID(),
                rawTransaction.getTIMESTAMP(),
                rawTransaction.getAMOUNT(),
                rawTransaction.getNOTE(),
                category,
                account
            );
            IDmapAccount.put(transaction.getID(), transaction);
        }

        return IDmapAccount;
    }

    @Override
    protected void write(ArrayList<Transaction> transactions) {
        var rawTransactions = new ArrayList<RawTransaction>();

        // convert
        for (Transaction transaction : transactions) {
            rawTransactions.add(RawTransaction.fromTransaction(transaction));
        }

        // write to source
        rawTransactionsSource.save(rawTransactions);
    }
}
