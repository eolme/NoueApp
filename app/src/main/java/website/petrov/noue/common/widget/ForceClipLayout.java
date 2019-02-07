package website.petrov.noue.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.GravityCompat;

import org.jetbrains.annotations.Contract;

/**
 * A LinearLayout, which ignores any padding around children.
 * To fix glitch, gravity is additionally reset.
 */
public final class ForceClipLayout extends LinearLayoutCompat {
    public ForceClipLayout(@NonNull Context context) {
        this(context, null);
    }

    public ForceClipLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ForceClipLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        forceClip();
    }

    private void forceClip() {
        super.setClipChildren(true);
        super.setClipToPadding(true);

        switch (getOrientation()) {
            case VERTICAL:
                setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                break;
            case HORIZONTAL:
            default:
                setGravity(GravityCompat.START | Gravity.CENTER_VERTICAL);
                break;
        }
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(0, getPaddingTop(), 0, 0);
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(0, 0, 0, 0);
    }

    @Contract(pure = true)
    @Override
    public int getPaddingBottom() {
        return 0;
    }

    @Override
    public int getPaddingTop() {
        return getFitsSystemWindows() ? super.getPaddingTop() : 0;
    }

    @Contract(pure = true)
    @Override
    public int getPaddingLeft() {
        return 0;
    }

    @Contract(pure = true)
    @Override
    public int getPaddingRight() {
        return 0;
    }

    @Contract(pure = true)
    @Override
    public int getPaddingStart() {
        return 0;
    }

    @Contract(pure = true)
    @Override
    public int getPaddingEnd() {
        return 0;
    }
}
