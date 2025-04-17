package net.jacza.expenses.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.color.DynamicColors;

import net.jacza.expenses.R;
import net.jacza.expenses.ui.fragment.AccountFragment;
import net.jacza.expenses.ui.fragment.CategoryFragment;
import net.jacza.expenses.ui.fragment.TransactionFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    // Fragment instances
    private TransactionFragment transactionFragment;
    private CategoryFragment categoryFragment;
    private AccountFragment accountFragment;
    private Fragment currentFragment;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupEdgeToEdge();
        DynamicColors.applyToActivityIfAvailable(this);
        initViews();
        setupBottomNavigation();

        if (savedInstanceState != null) {
            int selectedItem = savedInstanceState.getInt("selected_nav_item", R.id.transactions);
            bottomNavigationView.setSelectedItemId(selectedItem); // This triggers the right fragment to load
        } else {
            bottomNavigationView.setSelectedItemId(R.id.transactions);
        }

    }

    private void setupEdgeToEdge() {
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setTitle("Transactions");
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.transactions && !(currentFragment instanceof TransactionFragment)) {
                topAppBar.setTitle("Transactions");
                loadFragment(getTransactionFragment());
                return true;
            } else if (itemId == R.id.categories && !(currentFragment instanceof CategoryFragment)) {
                topAppBar.setTitle("Categories");
                loadFragment(getCategoryFragment());
                return true;
            } else if (itemId == R.id.accounts && !(currentFragment instanceof AccountFragment)) {
                topAppBar.setTitle("Accounts");
                loadFragment(getAccountFragment());
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selected_nav_item", bottomNavigationView.getSelectedItemId());
    }

    private void loadFragment(Fragment fragment) {
        if (fragment == null || fragment.isAdded()) return;

        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentsContainer, fragment)
                .commit();
    }

    // Fragment getters with lazy initialization
    private TransactionFragment getTransactionFragment() {
        if (transactionFragment == null) {
            transactionFragment = new TransactionFragment();
        }
        return transactionFragment;
    }

    private CategoryFragment getCategoryFragment() {
        if (categoryFragment == null) {
            categoryFragment = new CategoryFragment();
        }
        return categoryFragment;
    }

    private AccountFragment getAccountFragment() {
        if (accountFragment == null) {
            accountFragment = new AccountFragment();
        }
        return accountFragment;
    }
}