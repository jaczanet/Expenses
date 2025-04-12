package net.jacza.expenses.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import net.jacza.expenses.R;
import net.jacza.expenses.data.base.Repository;
import net.jacza.expenses.data.repository.CategoriesRepository;
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.ui.activity.CategoryActivity;
import net.jacza.expenses.ui.adapter.CategoryAdapter;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    Repository<Category> repo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        // Set up the add category button
        rootView.findViewById(R.id.addCategoryBtn).setOnClickListener(v -> {
            // Create an Intent to open another activity
            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            intent.putExtra("MODE", "ADD");
            startActivity(intent);
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.categoryFragRecyclerView);

        // Create FlexboxLayoutManager and configure it
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);

        recyclerView.setLayoutManager(layoutManager);

        // Set adapter
        repo = new CategoriesRepository(getActivity());
        adapter = new CategoryAdapter(repo.read(), repo);
        recyclerView.setAdapter(adapter);
    }

}