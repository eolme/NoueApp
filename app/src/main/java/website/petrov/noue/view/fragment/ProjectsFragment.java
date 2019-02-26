package website.petrov.noue.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

import website.petrov.noue.R;
import website.petrov.noue.view.component.ProjectsAdapter;
import website.petrov.noue.viewmodel.ProjectsViewModel;

public final class ProjectsFragment extends Fragment {
    @NonNull
    private final ProjectsViewModel viewModel;
    private WeakReference<RecyclerView> recyclerView;

    public ProjectsFragment() {
        viewModel = new ProjectsViewModel();
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View self = inflater.inflate(R.layout.fragment_projects, container, false);
        if (self != null) {
            final ProjectsAdapter adapter = new ProjectsAdapter();
            final ContentLoadingProgressBar bar = ViewCompat.requireViewById(self, R.id.projects_progress);

            recyclerView = new WeakReference<>(ViewCompat.requireViewById(self, R.id.projects_list));
            recyclerView.get().setAdapter(adapter);

            viewModel.getData().observe(this, adapter::updateData);

            self.postDelayed(() -> {
                bar.setVisibility(View.GONE);
                recyclerView.get().setVisibility(View.VISIBLE);
            }, 2000);
        }
        return self;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        final RecyclerView recycler = recyclerView.get();
        if (recycler != null) {
            if (recycler.getAdapter() != null) {
                recycler.setAdapter(null);
            }
        }

        recyclerView.enqueue();
    }
}
