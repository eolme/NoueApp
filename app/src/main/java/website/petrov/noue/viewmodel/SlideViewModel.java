package website.petrov.noue.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import website.petrov.noue.R;
import website.petrov.noue.common.viewmodel.GenericAndroidViewModel;
import website.petrov.noue.model.SlideModel;

public final class SlideViewModel extends GenericAndroidViewModel<SlideModel> {
    public SlideViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void load() {
        final String[] titles = getApplication().getResources().getStringArray(R.array.intro_titles);
        for (String title : titles) {
            this.models.add(new SlideModel(title));
        }
    }
}
