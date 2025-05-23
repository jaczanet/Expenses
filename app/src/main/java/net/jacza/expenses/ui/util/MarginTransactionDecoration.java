package net.jacza.expenses.ui.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * {@link RecyclerView.ItemDecoration} that adds margins (space) around each item in a RecyclerView.
 * <p>
 * This decoration applies the specified margin to the left, right, and bottom of each item.
 * Additionally, it adds the same margin to the top of the first item in the list.
 * </p>
 */
public class MarginTransactionDecoration extends RecyclerView.ItemDecoration {

    private final float spaceHeight;

    public MarginTransactionDecoration(float spaceHeight) {
        this.spaceHeight = spaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        int spaceHeightPx = (int) spaceHeight;

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = spaceHeightPx;
        }

        outRect.left = spaceHeightPx;
        outRect.right = spaceHeightPx;
        outRect.bottom = spaceHeightPx;

    }

}
