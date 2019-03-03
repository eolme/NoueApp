package website.petrov.noue.common.viewmodel;

import androidx.lifecycle.ViewModel;

public abstract class BaseDynamicViewModel extends ViewModel {
    protected abstract void load();
}
