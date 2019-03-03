package website.petrov.noue.common.viewmodel;

import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import website.petrov.noue.common.model.Model;

public abstract class BaseGenericViewModel<M extends Model> extends ViewModel
        implements DynamicViewModel {

    private List<M> models;

    @Contract("-> !null")
    public List<M> getData() {
        if (models == null) {
            models = new ArrayList<>();
            load();
        }
        return models;
    }
}
