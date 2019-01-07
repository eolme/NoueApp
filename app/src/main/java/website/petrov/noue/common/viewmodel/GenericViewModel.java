package website.petrov.noue.common.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.Contract;

import java.util.List;

import website.petrov.noue.common.model.BaseModel;

public abstract class GenericViewModel<M extends BaseModel> extends BaseDynamicViewModel {
    protected MutableLiveData<List<M>> models;

    @Contract("-> !null")
    public LiveData<List<M>> getData() {
        if (models == null) {
            models = new MutableLiveData<>();
            load();
        }
        return models;
    }
}
