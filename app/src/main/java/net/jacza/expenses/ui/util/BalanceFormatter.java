package net.jacza.expenses.ui.util;

import android.content.Context;
import android.widget.TextView;

import net.jacza.expenses.R;

import java.util.Locale;

/**
 * The `BalanceFormatter` class provides utility methods for formatting and displaying financial balances in a `TextView`
 * with a specified format (2 decimal places) and color (Expenses-Red and Incomes-Green).
 */
public class BalanceFormatter {
    public static void setFormattedBalance(TextView textView, double balance, Context context) {
        if (balance >= 0) {
            textView.setText(String.format(Locale.getDefault(), "+ %.2f€", balance));
            textView.setTextColor(context.getColor(R.color.income_color));
        } else {
            textView.setText(String.format(Locale.getDefault(), "- %.2f€", Math.abs(balance)));
            textView.setTextColor(context.getColor(R.color.expense_color));
        }
    }
}
