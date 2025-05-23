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
import net.jacza.expenses.data.model.Category;
import net.jacza.expenses.data.repository.CategoriesRepository;
import net.jacza.expenses.ui.activity.CategoryActivity;
import net.jacza.expenses.ui.adapter.CategoryAdapter;
import net.jacza.expenses.ui.util.SaveBtnModes;

import java.util.List;

/**
 * CategoryFragment is a Fragment responsible for displaying a list of categories.
 * It utilizes a RecyclerView with a FlexboxLayoutManager to present categories in a flexible grid.
 * Users can add new categories via a button, which navigates to the CategoryActivity.
 * The fragment also handles updating the category list when the view resumes.
 */

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private Repository<Category> repo;
    private List<Category> categories;

    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view
            .findViewById(R.id.addCategoryBtn)
            .setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("MODE", SaveBtnModes.ADD);
                startActivity(intent);
            });

        recyclerView = view.findViewById(R.id.categoryFragRecyclerView);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);

        repo = CategoriesRepository.getINSTANCE();
        categories = repo.read();
        adapter = new CategoryAdapter(categories);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setList(repo.read());
        adapter.notifyDataSetChanged();
    }
}
