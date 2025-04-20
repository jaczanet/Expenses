package net.jacza.expenses.ui.adapter;

import android.content.Intent;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.domain.safedeletion.FoundAssociatedTransactionException;
import net.jacza.expenses.domain.safedeletion.SafeDeleteUseCase;
import net.jacza.expenses.ui.activity.AccountActivity;
import net.jacza.expenses.ui.activity.AccountTransactions;
import net.jacza.expenses.ui.util.SaveBtnModes;
import net.jacza.expenses.ui.util.BalanceFormatter;

import java.io.Serializable;
import java.util.List;

/**
 * {@code AccountAdapter} is a RecyclerView.Adapter that manages a list of {@link Account} objects.
 * It is responsible for creating and binding {@link AccountViewHolder}s to display account information in a RecyclerView.
 * It also handles user interactions like clicking on an account to view its transactions and accessing the account menu for edit/delete options.
 */
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<Account> accounts;
    private Repository<Account> repo;
    public AccountAdapter(List<Account> accounts, Repository<Account> repo) {
        this.accounts = accounts;
        this.repo= repo;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_card, parent, false);
        return new AccountViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.bind(account);

        // Set click listener for the filtered transactions
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AccountTransactions.class);
            intent.putExtra("Account", (Serializable) account);
            view.getContext().startActivity(intent);
        });

        holder.menuButton.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), holder.menuButton);
            popupMenu.inflate(R.menu.edit_delete_menu);
            MenuItem deleteItem = popupMenu.getMenu().findItem(R.id.menu_delete);
            SpannableString s = new SpannableString(deleteItem.getTitle());
            s.setSpan(new ForegroundColorSpan(view.getContext().getColor(R.color.delete_opt_color)), 0, s.length(), 0);
            deleteItem.setTitle(s);

            popupMenu.setOnMenuItemClickListener(item -> {
                // Handle menu item clicks here
                if (item.getItemId() == R.id.menu_edit) {
                    Intent intent = new Intent(view.getContext(), AccountActivity.class);
                    intent.putExtra("MODE", SaveBtnModes.EDIT);
                    intent.putExtra("ACCOUNT TO EDIT", account);
                    view.getContext().startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.menu_delete) {
                    try{
                        SafeDeleteUseCase.deleteAccount(account);
                        notifyAccountRemoved(position, view);
                        setList(repo.read());
                    }catch (FoundAssociatedTransactionException e){
                        Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public void setList(List<Account> newList) {
        this.accounts = newList;
    }

    public void notifyAccountRemoved(int position, View view){
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        Account tempAcc = accounts.get(position);
        String accName = tempAcc.getName();

        Snackbar.make(view, "Undo Deletion of: " + accName, Snackbar.LENGTH_LONG)
                .setAction("UNDO", v -> {
                    accounts.add(position, tempAcc);
                    repo.create(tempAcc);
                    notifyItemInserted(position);
                    notifyItemRangeChanged(position, getItemCount());
                })
                .show();
    }


    static class AccountViewHolder extends RecyclerView.ViewHolder {
        private TextView accountName;
        private TextView accountBalance;
        public ImageButton menuButton;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            accountName = itemView.findViewById(R.id.textViewAccountName);
            accountBalance = itemView.findViewById(R.id.textViewBalance);
            menuButton = itemView.findViewById(R.id.iconAccountMenu);
        }

        public void bind(Account account) {
            accountName.setText(account.getName());

            double balance = account.getBalance();
            BalanceFormatter.setFormattedBalance(accountBalance, balance, itemView.getContext());
        }
    }
}
