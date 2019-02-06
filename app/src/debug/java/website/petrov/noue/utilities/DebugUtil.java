package website.petrov.noue.utilities;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import androidx.annotation.NonNull;
import android.app.Application;

public final class DebugUtil {
    public static void initialize(@NonNull Application app) {
        if (LeakCanary.isInAnalyzerProcess(app)) {
            return;
        }
        LeakCanary.install(app);
        Stetho.initializeWithDefaults(app);
    }

    private DebugUtil() {} // hide constructor
}