package website.petrov.noue.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import website.petrov.noue.R;
import website.petrov.noue.common.widget.FullscreenBindingRecyclerView;
import website.petrov.noue.utilities.Provider;
import website.petrov.noue.view.component.ProjectsAdapter;
import website.petrov.noue.viewmodel.ProjectsViewModel;

public final class ProjectsFragment extends Fragment {
    @NonNull
    private final ProjectsViewModel viewModel;
    @NonNull
    private final ProjectsAdapter adapter;

    public ProjectsFragment() {
        viewModel = new ProjectsViewModel();
        adapter = new ProjectsAdapter();
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View self = inflater.inflate(R.layout.fragment_projects, container, false);
        if (self != null) {
            final FullscreenBindingRecyclerView recyclerView = Provider.getView(R.id.projects_list, self);
            final ContentLoadingProgressBar bar = Provider.getView(R.id.projects_progress, self);

            recyclerView.setAdapter(adapter);
            viewModel.getData().observe(this, adapter::updateData);

            self.postDelayed(() -> {
                bar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }, 2000);
        }
        return self;
    }
}
