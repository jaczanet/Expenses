package net.jacza.expenses.data.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import net.jacza.expenses.data.base.DataSource;
import net.jacza.expenses.data.common.IdentifiableRepository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.raw.RawAccount;
import net.jacza.expenses.data.raw.RawTransaction;
import net.jacza.expenses.data.source.RawAccountsDataSource;
import net.jacza.expenses.data.source.RawTransactionsDataSource;

/*
 * Repository class that implements CRUD functionality for Account.
 */
public class AccountsRepository extends IdentifiableRepository<Account> {

    // Singleton pattern with eager initialization

    private static final AccountsRepository INSTANCE = new AccountsRepository();

    public static AccountsRepository getINSTANCE() {
        return INSTANCE;
    }

    private AccountsRepository() {}

    // data sources

    private final DataSource<RawAccount> RAW_ACCOUNTS_SOURCE = new RawAccountsDataSource();
    private final DataSource<RawTransaction> RAW_TRANSACTIONS_SOURCE =
        new RawTransactionsDataSource();

    // repository methods

    @Override
    public ArrayList<Account> read() {
        var accounts = new ArrayList<Account>(readMap().values());
        accounts.sort((a, b) -> a.getName().compareTo(b.getName())); // default sorting logic
        return accounts;
    }

    HashMap<UUID, Account> readMap() {
        var IDmapAccount = new HashMap<UUID, Account>();

        // read from source
        var rawAccounts = RAW_ACCOUNTS_SOURCE.load();

        // convert
        for (var rawAccount : rawAccounts) {
            var account = new Account(
                rawAccount.getID(),
                rawAccount.getNAME(),
                rawAccount.getINITIAL_BALANCE()
            );
            IDmapAccount.put(account.getID(), account);
        }

        // update accounts balance
        var rawTransactions = rawTransactionsSource.load();
        for (var rawTransaction : rawTransactions) {
            var account = IDmapAccount.get(rawTransaction.getACCOUNT_ID());
            account.updateBalance(rawTransaction.getAMOUNT());
        }

        return IDmapAccount;
    }

    @Override
    protected void write(ArrayList<Account> accounts) {
        var rawAccounts = new ArrayList<RawAccount>();

        // convert
        for (var account : accounts) {
            rawAccounts.add(RawAccount.fromAccount(account));
        }

        // write to source
        RAW_ACCOUNTS_SOURCE.save(rawAccounts);
    }
}
