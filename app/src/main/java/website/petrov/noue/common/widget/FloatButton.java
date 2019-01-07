package website.petrov.noue.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import website.petrov.noue.utilities.Provider;

public final class FloatButton extends FloatingActionButton {
    public FloatButton(Context context) {
        super(context);
    }

    public FloatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (isInEditMode()) {
            return; // Layout Editor
        }

        final Context context = getContext();
        final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin,
                layoutParams.bottomMargin +
                        Provider.getNavigationBarHeight(context));
        setLayoutParams(layoutParams);
    }
}
