package website.petrov.noue.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("JavaReflectionMemberAccess")
@SuppressLint("PrivateApi")
public final class OS {
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
    public static void setTransparentStatusBar(@NonNull Context context) {
        final Activity activity = Provider.getActivity(context);

        if (activity != null) {
            final Window window = activity.getWindow();
            final View decor = window.getDecorView();

            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decor.setSystemUiVisibility(decor.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }

            setTransparentStatusBarFlyme(window);
            setTransparentStatusBarMIUI(window);
        }
    }
}
