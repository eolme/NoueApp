package website.petrov.noue.common.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.Contract;

import java.util.List;

import website.petrov.noue.common.model.Model;

public abstract class BaseMutableGenericContextViewModel<M extends Model> extends AndroidViewModel
        implements DynamicViewModel {
    protected MutableLiveData<List<M>> models;

    public BaseMutableGenericContextViewModel(@NonNull Application application) {
        super(application);
    }

    @Contract("-> !null")
    public LiveData<List<M>> getData() {
        if (models == null) {
            models = new MutableLiveData<>();
            load();
        }
        return models;
    }
}
