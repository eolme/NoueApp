package website.petrov.noue.utilities;

import com.squareup.leakcanary.LeakCanary;
import androidx.annotation.NonNull;
import android.app.Application;
import android.os.Handler;
import android.os.StrictMode;

public final class DebugUtil {
    public static void initialize(@NonNull Application app) {
        if (LeakCanary.isInAnalyzerProcess(app)) {
            return;
        }
        LeakCanary.install(app);

        // See: https://issuetracker.google.com/issues/36951662
        new Handler().postAtFrontOfQueue(() -> {
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

    private DebugUtil() {} // hide constructor
}