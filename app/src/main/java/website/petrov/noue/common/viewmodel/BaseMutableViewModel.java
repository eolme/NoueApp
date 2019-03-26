package website.petrov.noue.common.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.Contract;

import java.util.List;

import website.petrov.noue.common.model.IdentifiableModel;

public abstract class BaseMutableViewModel extends ViewModel
        implements DynamicViewModel {

    private MutableLiveData<List<IdentifiableModel>> models;

    @Contract("-> !null")
    public LiveData<List<IdentifiableModel>> getData() {
        if (models == null) {
            models = new MutableLiveData<>();
            load();
        }
        return models;
    }
}
