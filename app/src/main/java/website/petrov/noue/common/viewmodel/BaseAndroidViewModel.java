package website.petrov.noue.common.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class BaseAndroidViewModel extends AndroidViewModel {
    BaseAndroidViewModel(@NonNull Application application) {
        super(application);
    }

    protected abstract void load();
}