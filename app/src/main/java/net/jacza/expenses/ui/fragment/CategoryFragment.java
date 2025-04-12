package net.jacza.expenses.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.jacza.expenses.R;
import net.jacza.expenses.ui.activity.TransactionActivity;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        // TODO: set up recycler view and flexbox layout

        // Set up the add category button
        rootView.findViewById(R.id.addCategoryBtn).setOnClickListener(v -> {
            // Create an Intent to open another activity
            Intent intent = new Intent(getActivity(), TransactionActivity.class);
            startActivity(intent);
        });

        return rootView;
    }

}