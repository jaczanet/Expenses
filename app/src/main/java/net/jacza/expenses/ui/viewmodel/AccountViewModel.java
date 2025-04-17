package net.jacza.expenses.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.repository.AccountsRepository;

import java.util.ArrayList;

public class AccountViewModel extends ViewModel {
    private final Repository<Account> repo = AccountsRepository.getInstance();
    public void create(Account account) {repo.create(account);}
    public void delete(Account account) {repo.delete(account);}
    public ArrayList<Account> read() {return repo.read();}
    public void update(Account account) {repo.update(account);}
}
