package website.petrov.noue.utils;

import android.app.Application;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public final class DebugUtils {
    private static final String TAG = "DebugUtils";
    private static RefWatcher refWtcher;

    private DebugUtils() {
    } // hide constructor

    public static void initialize(@NonNull Application app) {
        if (LeakCanary.isInAnalyzerProcess(app)) {
            return;
        }
        LeakCanary.install(app);

        // See: https://issuetracker.google.com/issues/36951662
        new Handler().postAtFrontOfQueue(() -> {
            // OxygenOS: penaltyDialog annoying due to a violation in the construction of view
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        });
    }

    public static void watch(@NonNull Object lifecycle, @NonNull String name) {
        final RefWatcher watcher = LeakCanary.installedRefWatcher();
        watcher.watch(lifecycle, name);
    }

    public static void log(@NonNull String msg) {
        Log.d(TAG, msg);
    }

    public static void log(@NonNull String msg, @Nullable Object obj) {
        Log.d(TAG, msg + ": " + (obj == null ? "null" : obj.toString()));
    }
}