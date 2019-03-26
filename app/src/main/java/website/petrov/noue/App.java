package website.petrov.noue;

import android.app.Application;

import website.petrov.noue.repository.data.StorageRepository;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.utils.DebugUtils;

public final class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DebugUtils.initialize(this);
        StorageShared.initialize(this);
        StorageRepository.initialize(this);
    }
}
