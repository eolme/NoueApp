package website.petrov.noue.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.app.ActivityCompat;

import org.jetbrains.annotations.Contract;

public final class Provider {
    /**
     * @param context the context
     * @return StatusBar's height in px
     */
    @Px
    public static int getStatusBarHeight(@Nullable Context context) {
        if (context == null) {
            return 0;
        }

        @NonNull final Resources contextResources = context.getResources();
        @DimenRes final int resourceId =
                contextResources.getIdentifier("status_bar_height", "dimen", "android");

        return contextResources.getDimensionPixelSize(resourceId);
    }

    @Px
    public static int getNavigationBarHeight(@Nullable Context context) {
        final Activity activity = getActivity(context);
        if (activity != null && hasSoftKeys(activity.getWindowManager())) {
            @NonNull final Resources contextResources = context.getResources();
            @DimenRes final int resourceId =
                    contextResources.getIdentifier("navigation_bar_height", "dimen", "android");

            return contextResources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    @Px
    public static int getScreenHeight(@Nullable Context context) {
        final Activity activity = getActivity(context);
        if (activity == null) {
            return 0;
        }
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private static boolean hasSoftKeys(@NonNull WindowManager windowManager) {
        final Display d = windowManager.getDefaultDisplay();

        final DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);

        @Px final int realHeight = realDisplayMetrics.heightPixels;
        @Px final int realWidth = realDisplayMetrics.widthPixels;

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        @Px final int displayHeight = displayMetrics.heightPixels;
        @Px final int displayWidth = displayMetrics.widthPixels;

        return (realWidth > displayWidth) || (realHeight > displayHeight);
    }

    @Px
    public static int convertDpToPixel(@Dimension int dp, @NonNull Context context) {
        final Resources resources = context.getResources();
        final DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Dimension
    public static int convertPixelsToDp(@Px int px, @NonNull Context context) {
        return px / (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void clearFocus(@NonNull View view) {
        final Activity context = getActivity(view.getContext());

        if (context == null) {
            throw new IllegalArgumentException("Context of View must be a Activity");
        }

        view.clearFocus();

        final InputMethodManager manager =
                ActivityCompat.getSystemService(context, InputMethodManager.class);
        if (manager == null) {
            return; // Hardware keyboard
        }

        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Return an activity if the context is an activity or it is possible to obtain
     * an activity from it. Otherwise, null.
     *
     * @param context The context
     * @return An activity or null
     */
    @Contract("null -> null")
    @Nullable
    public static Activity getActivity(@Nullable Context context) {
        if (context == null) return null;
        if (context instanceof Activity) return (Activity) context;
        if (context instanceof ContextWrapper)
            return getActivity(((ContextWrapper) context).getBaseContext());
        return null;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(@NonNull Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private Provider() {} // hide constructor
}
