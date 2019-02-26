package website.petrov.noue.viewmodel;

import java.util.List;

import website.petrov.noue.common.viewmodel.GenericViewModel;
import website.petrov.noue.model.FeedModel;
import website.petrov.noue.repository.data.StorageRepository;

public final class FeedViewModel extends GenericViewModel<FeedModel> {
    @Override
    protected void load() {
        final StorageRepository repo = StorageRepository.getInstance();
        final List<FeedModel> list = repo.getFeed().getValue();
        if (list != null) {
            this.models.setValue(list);
        }
    }
}
