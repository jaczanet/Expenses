package net.jacza.expenses.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.JustifyContent;

import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.model.Account;
import net.jacza.expenses.data.repository.AccountsRepository;
import net.jacza.expenses.ui.activity.AccountActivity;
import net.jacza.expenses.ui.adapter.AccountAdapter;
import net.jacza.expenses.ui.util.SaveBtnModes;

public class AccountFragment extends Fragment {
    private AccountAdapter adapter;

    private Repository<Account> repo;

    public AccountFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }


    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.addAccountBtn).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AccountActivity.class);
            intent.putExtra("MODE", SaveBtnModes.ADD);
            startActivity(intent);
        });

        RecyclerView recyclerView = view.findViewById(R.id.accountRecyclerView);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(requireContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

        repo = AccountsRepository.getInstance();
        adapter = new AccountAdapter(repo.read(),repo);
        recyclerView.setLayoutManager(flexboxLayoutManager);
        recyclerView.setAdapter(adapter);

    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        adapter.setList(repo.read());
        adapter.notifyDataSetChanged();
    }
}
