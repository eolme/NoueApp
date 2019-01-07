package website.petrov.noue.common.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import website.petrov.noue.common.model.BaseModel;

public abstract class GenericAndroidViewModel<M extends BaseModel> extends BaseAndroidViewModel {
    protected List<M> models;

    public GenericAndroidViewModel(@NonNull Application application) {
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
