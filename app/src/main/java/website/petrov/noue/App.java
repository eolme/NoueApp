package website.petrov.noue;

import android.app.Application;
import android.net.TrafficStats;

import website.petrov.noue.repository.data.StorageRepository;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.utilities.DebugUtil;

public final class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DebugUtil.initialize(this);
        StorageShared.initialize(this);
        StorageRepository.initialize(this);
    }
}
