package website.petrov.noue;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import website.petrov.noue.repository.data.StorageRepository;
import website.petrov.noue.repository.data.StorageShared;

public final class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        LeakCanary.install(this);
        Stetho.initializeWithDefaults(this);
        StorageShared.initialize(this);
        StorageRepository.initialize(this);
    }
}
