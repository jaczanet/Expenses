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

    private static final AccountsRepository instance = new AccountsRepository();

    public static AccountsRepository getInstance() {
        return instance;
    }

    private AccountsRepository() {}

    // data sources

    private DataSource<RawAccount> rawAccountsSource = new RawAccountsDataSource();
    private DataSource<RawTransaction> rawTransactionsSource = new RawTransactionsDataSource();

    // repository methods

    @Override
    public ArrayList<Account> read() {
        var IDmapAccount = new HashMap<UUID, Account>();
        var rawAccounts = rawAccountsSource.load();
        for (RawAccount rawAccount : rawAccounts) {
            var account = new Account(
                rawAccount.getID(),
                rawAccount.getNAME(),
                rawAccount.getINITIAL_BALANCE()
            );
            IDmapAccount.put(account.getID(), account);
        }

        // update accounts balance
        var rawTransactions = rawTransactionsSource.load();
        for (RawTransaction rawTransaction : rawTransactions) {
            var account = IDmapAccount.get(rawTransaction.getACCOUNT_ID());
            account.updateBalance(rawTransaction.getAMOUNT());
        }

        var accounts = new ArrayList<Account>(IDmapAccount.values());
        return accounts;
    }

    @Override
    public void delete(Account entry) {
        var accounts = read();
        accounts.removeIf(item -> item.getID().equals(entry.getID()));
        write(accounts);
    }

    protected void write(ArrayList<Account> accounts) {
        var rawAccounts = new ArrayList<RawAccount>();
        for (Account account : accounts) {
            rawAccounts.add(RawAccount.fromAccount(account));
        }
        rawAccounts.sort((a, b) -> a.getNAME().compareTo(b.getNAME()));
        rawAccountsSource.save(rawAccounts);
    }
}
