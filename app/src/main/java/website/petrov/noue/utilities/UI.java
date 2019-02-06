package website.petrov.noue.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public final class UI {
    /**
     * @param context the context
     */
    public static void setFullscreenLayout(@NonNull Context context, boolean darkIcon) {
        final Activity activity = Provider.getActivity(context);

        if (activity == null) {
            return;
        }

        final Window window = activity.getWindow();
        final View decor = window.getDecorView();
        int visibility = decor.getSystemUiVisibility();

        final int clear =
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        final int add =
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                        WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;

        window.setNavigationBarColor(Color.TRANSPARENT);
        window.setStatusBarColor(Color.TRANSPARENT);

        window.clearFlags(clear);
        window.addFlags(add);

        if (darkIcon) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                visibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                visibility |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                visibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                visibility &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
        }

        decor.setSystemUiVisibility(visibility);
    }

    private UI() {} // hide constructor
}
