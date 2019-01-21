package website.petrov.noue.view.component;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import website.petrov.noue.R;
import website.petrov.noue.common.component.LiveAdapter;
import website.petrov.noue.common.widget.FullscreenBindingRecyclerView;
import website.petrov.noue.model.ProjectItemModel;

public class ProjectsAdapter
        extends FullscreenBindingRecyclerView.SingleLayoutAdapter
        implements LiveAdapter<ProjectItemModel> {
    @NonNull
    private List<ProjectItemModel> data;

    public ProjectsAdapter() {
        super(R.layout.fragment_project_item);
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    protected Object getValueForPosition(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void updateData(@NonNull List<ProjectItemModel> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
