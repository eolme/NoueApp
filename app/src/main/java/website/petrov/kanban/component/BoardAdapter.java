package website.petrov.kanban.component;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import website.petrov.kanban.model.ColumnModel;
import website.petrov.noue.R;
import website.petrov.noue.common.model.Model;
import website.petrov.noue.common.widget.BindingRecyclerView;

public class BoardAdapter
        extends BindingRecyclerView.SingleLayoutAdapter {

    private List<ColumnModel> columns;

    public BoardAdapter() {
        super(R.layout.view_column);

        columns = new ArrayList<>();
    }

    @NonNull
    @Override
    protected Model getValueForPosition(int position) {
        return columns.get(position);
    }

    @Override
    public int getItemCount() {
        return columns.size();
    }

    public void updateData(@NonNull List<Model> newData) {
        columns.clear();

        // final StorageRepository repo = StorageRepository.getInstance();
        for (Model model : newData) {
            if (model instanceof ColumnModel) {
                columns.add((ColumnModel) model);
                // repo.insertFeedModel((ColumnModel) model);
            }
        }

        notifyDataSetChanged();
    }
}
