package website.petrov.noue.viewmodel;

import website.petrov.noue.common.viewmodel.GenericViewModel;
import website.petrov.noue.model.ProjectModel;
import website.petrov.noue.repository.data.StorageRepository;

public final class ProjectViewModel extends GenericViewModel<ProjectModel> {
    @Override
    protected void load() {
        final StorageRepository repo = StorageRepository.getInstance();
        if (repo == null) {
            return;
        }
        this.models.setValue(repo.getProjects().getValue());
    }
}
