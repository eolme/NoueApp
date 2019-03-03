package website.petrov.noue.common.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.Contract;

import java.util.List;

import website.petrov.noue.common.model.Model;

public abstract class BaseMutableGenericViewModel<M extends Model> extends ViewModel
        implements DynamicViewModel {

    private MutableLiveData<List<M>> models;

    @Contract("-> !null")
    public LiveData<List<M>> getData() {
        if (models == null) {
            models = new MutableLiveData<>();
            load();
        }
        return models;
    }
}
