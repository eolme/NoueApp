package website.petrov.noue.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import java.util.Timer;
import java.util.TimerTask;

import website.petrov.noue.R;
import website.petrov.noue.common.widget.FullscreenBindingRecyclerView;
import website.petrov.noue.utilities.Provider;
import website.petrov.noue.view.component.ProjectsAdapter;

public final class ProjectsFragment extends Fragment {
    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View self = inflater.inflate(R.layout.fragment_projects, container, false);
        if (self != null) {
            final FullscreenBindingRecyclerView recyclerView = Provider.getView(R.id.projects_list, self);
            final ContentLoadingProgressBar bar = Provider.getView(R.id.projects_progress, self);

            recyclerView.setAdapter(new ProjectsAdapter(R.layout.fragment_project_item));

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    bar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }, 1000);
        }
        return self;
    }
}
