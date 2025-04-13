package net.jacza.expenses.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.repository.AccountsRepository;

import java.util.ArrayList;

public class AccountViewModel extends AndroidViewModel {
    // private final Repository<Account> repo; TODO uncomment after implementing AccountsRepository
    public AccountViewModel(@NonNull Application application) {
        super(application);
        // repo = new AccountsRepository(application.getApplicationContext());
    }
//    public void create(Account account) {repo.create(account);}
//    public void delete(Account account) {repo.delete(account);}
//    public ArrayList<Account> read() {return repo.read();}
//    public void update(Account account) {repo.update(account);}
}
