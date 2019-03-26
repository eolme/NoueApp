package website.petrov.noue.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import website.petrov.noue.R;
import website.petrov.noue.utils.ContextUtils;
import website.petrov.noue.utils.UIUtils;

public class FullscreenLayout extends FrameLayout {
    public FullscreenLayout(@NonNull Context context) {
        this(context, null);
    }

    public FullscreenLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FullscreenLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FullscreenLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FullscreenLayout, defStyleAttr, 0);
        init(attributes.getBoolean(R.styleable.FullscreenLayout_darkIcon, false));
        attributes.recycle();
    }

    private void init(boolean darkIcon) {
        final Activity activity = ContextUtils.getActivity(getContext());
        if (activity != null) {
            UIUtils.setFullscreenLayout(activity, darkIcon);
        }
        if (getFitsSystemWindows()) {
            final int statusBarHeight = ContextUtils.getStatusBarHeight(activity);
            final int navigationBarHeight = ContextUtils.getNavigationBarHeight(activity);
            this.setPaddingRelative(0, statusBarHeight, 0, navigationBarHeight);
        }
    }
}
