package website.petrov.noue.common.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.Contract;

import java.util.List;

import website.petrov.noue.common.model.IdentifiableModel;

public abstract class BaseMutableContextViewModel extends AndroidViewModel
        implements DynamicViewModel {
    protected MutableLiveData<List<IdentifiableModel>> models;

    public BaseMutableContextViewModel(@NonNull Application application) {
        super(application);
    }

    @Contract("-> !null")
    public LiveData<List<IdentifiableModel>> getData() {
        if (models == null) {
            models = new MutableLiveData<>();
            load();
        }
        return models;
    }

    public abstract void setData(@NonNull List<IdentifiableModel> newData);
}
