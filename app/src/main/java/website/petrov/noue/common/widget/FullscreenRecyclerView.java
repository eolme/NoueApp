package website.petrov.noue.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.Contract;

import website.petrov.noue.R;
import website.petrov.noue.utilities.Provider;

public class FullscreenRecyclerView extends RecyclerView {
    public FullscreenRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public FullscreenRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FullscreenRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        fitWindow(context);
    }

    private void fitWindow(@NonNull Context context) {
        if (isInEditMode()) {
            return; // Layout Editor
        }

        super.setFitsSystemWindows(false);
        super.setClipToPadding(false);

        @Px final int topPadding = Provider.getStatusBarHeight(context);
        @Px final int bottomPadding =
                Provider.getNavigationBarHeight(context) +
                        (int) getResources().getDimension(R.dimen.margin_scroll_default);
        setPaddingRelative(0, topPadding, 0, bottomPadding);

        @Px final int height = Provider.getScreenHeight(context);
        setMinimumHeight(height);
    }

    @Contract(pure = true)
    @Override
    public boolean getFitsSystemWindows() {
        return false;
    }

    @Override
    public void setFitsSystemWindows(boolean ignored) {
        super.setFitsSystemWindows(false);
    }

    @Contract(pure = true)
    @Override
    public boolean getClipToPadding() {
        return false;
    }

    @Override
    public void setClipToPadding(boolean ignored) {
        super.setClipToPadding(false);
    }
}
