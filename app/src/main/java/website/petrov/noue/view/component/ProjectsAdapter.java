package website.petrov.noue.view.component;

import androidx.annotation.NonNull;

import website.petrov.noue.common.component.LiveAdapter;
import website.petrov.noue.common.widget.FullscreenBindingRecyclerView;

public class ProjectsAdapter extends FullscreenBindingRecyclerView.SingleLayoutAdapter implements LiveAdapter {
    public ProjectsAdapter(int layoutId) {
        super(layoutId);
    }

    @NonNull
    @Override
    protected Object getValueForPosition(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
