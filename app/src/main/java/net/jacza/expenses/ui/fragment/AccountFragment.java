package net.jacza.expenses.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.repository.AccountsRepository;
import net.jacza.expenses.ui.activity.AccountActivity;
import net.jacza.expenses.ui.adapter.AccountAdapter;
import net.jacza.expenses.ui.util.SaveBtnModes;

/**
 * AccountFragment is a Fragment responsible for displaying a list of accounts.
 * It utilizes a RecyclerView with a FlexboxLayoutManager to present accounts in a flexible grid.
 * Users can add new accounts via a button, which navigates to the AccountActivity.
 * The fragment also handles updating the account list when the view resumes.
 */
public class AccountFragment extends Fragment {

    private RecyclerView recyclerView;
    private AccountAdapter adapter;
    private Repository<Account> repo;

    public AccountFragment() {}

    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view
            .findViewById(R.id.addAccountBtn)
            .setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                intent.putExtra("MODE", SaveBtnModes.ADD);
                startActivity(intent);
            });

        recyclerView = view.findViewById(R.id.accountRecyclerView);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

        repo = AccountsRepository.getINSTANCE();
        adapter = new AccountAdapter(repo.read(), repo);
        recyclerView.setLayoutManager(flexboxLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setList(repo.read());
        adapter.notifyDataSetChanged();
    }
}
