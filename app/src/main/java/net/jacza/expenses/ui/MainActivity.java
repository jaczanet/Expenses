package net.jacza.expenses.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupEdgeToEdge();
        DynamicColors.applyToActivityIfAvailable(this);
        initViews();
        setupBottomNavigation();
        setDefaultFragment();
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
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.transactions && !(currentFragment instanceof TransactionFragment)) {
                loadFragment(getTransactionFragment());
                return true;
            } else if (itemId == R.id.categories && !(currentFragment instanceof CategoryFragment)) {
                loadFragment(getCategoryFragment());
                return true;
            } else if (itemId == R.id.accounts && !(currentFragment instanceof AccountFragment)) {
                loadFragment(getAccountFragment());
                return true;
            }
            return false;
        });
    }

    private void setDefaultFragment() {
        if (bottomNavigationView.getSelectedItemId() == R.id.transactions) {
            loadFragment(getTransactionFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        if (fragment == null || fragment.isAdded()) return;

        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linearLayout, fragment)
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