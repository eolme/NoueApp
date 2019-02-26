package website.petrov.noue.viewmodel;

import java.util.List;

import website.petrov.noue.common.viewmodel.GenericViewModel;
import website.petrov.noue.model.ProjectItemModel;
import website.petrov.noue.repository.data.StorageRepository;

public final class ProjectsViewModel extends GenericViewModel<ProjectItemModel> {
    @Override
    protected void load() {
        final StorageRepository repo = StorageRepository.getInstance();
        final List<ProjectItemModel> list = repo.getProjects().getValue();
        if (list != null) {
            this.models.setValue(list);
        }
    }
}
