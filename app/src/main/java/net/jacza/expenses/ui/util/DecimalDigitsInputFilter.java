package net.jacza.expenses.ui.util;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

public class DecimalDigitsInputFilter implements InputFilter {

    private final int mDigitsBeforeZero;
    private final int mDigitsAfterZero;
    private final String mPattern;

    public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
        this.mDigitsBeforeZero = digitsBeforeZero;
        this.mDigitsAfterZero = digitsAfterZero;
        mPattern = "^-?[0-9]{0," + mDigitsBeforeZero + "}+((\\.[0-9]{0," + mDigitsAfterZero + "})?)|(\\.)?$";
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        SpannableStringBuilder builder = new SpannableStringBuilder(dest);
        builder.replace(dstart, dend, source, start, end);
        if (!builder.toString().matches(mPattern)) {
            if (source.length() == 0)
                return dest.subSequence(dstart, dend);
            else
                return "";
        }
        return null;
    }
}
