package website.petrov.noue.common.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import website.petrov.noue.common.model.Model;

public abstract class BaseContextViewModel extends AndroidViewModel
        implements DynamicViewModel {

    protected List<Model> models;

    public BaseContextViewModel(@NonNull Application application) {
        super(application);
    }

    @Contract("-> !null")
    public List<Model> getData() {
        if (models == null) {
            models = new ArrayList<>();
            load();
        }
        return models;
    }
}
