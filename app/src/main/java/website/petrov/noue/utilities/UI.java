package website.petrov.noue.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("JavaReflectionMemberAccess")
@SuppressLint("PrivateApi")
public final class UI {
    private static void setTransparentStatusBarFlyme(@NonNull Window window) {
        try {
            final WindowManager.LayoutParams lp = window.getAttributes();
            final Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            darkFlag.setAccessible(true);
            final Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            meizuFlags.setAccessible(true);
            final int value = meizuFlags.getInt(lp) | darkFlag.getInt(null);
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Throwable ignored) {
        }
    }

    private static void setTransparentStatusBarMIUI(@NonNull Window window) {
        try {
            final Class layoutParamsClass = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            final int tranceFlag = layoutParamsClass.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT").getInt(null);
            final Method extraFlagsField = Window.class.getMethod("setExtraFlags", int.class, int.class);
            extraFlagsField.invoke(window, tranceFlag, tranceFlag);
        } catch (Throwable ignored) {
        }
    }

    /**
     * @param context the context
     */
    public static void setFullscreenLayout(@NonNull Context context, boolean darkIcon) {
        final Activity activity = Provider.getActivity(context);

        if (activity != null) {
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
    }
}
