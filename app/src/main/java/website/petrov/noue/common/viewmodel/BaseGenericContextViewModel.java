package website.petrov.noue.common.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import website.petrov.noue.common.model.Model;

public abstract class BaseGenericContextViewModel<M extends Model> extends AndroidViewModel
        implements DynamicViewModel {

    protected List<M> models;

    public BaseGenericContextViewModel(@NonNull Application application) {
        super(application);
    }

    @Contract("-> !null")
    public List<M> getData() {
        if (models == null) {
            models = new ArrayList<>();
            load();
        }
        return models;
    }
}
