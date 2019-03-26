package website.petrov.noue.common.viewmodel;

import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import website.petrov.noue.common.model.Model;

public abstract class BaseViewModel extends ViewModel
        implements DynamicViewModel {

    private List<Model> models;

    @Contract("-> !null")
    public List<Model> getData() {
        if (models == null) {
            models = new ArrayList<>();
            load();
        }
        return models;
    }
}
