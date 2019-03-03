package website.petrov.noue.view.component;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import website.petrov.noue.R;
import website.petrov.noue.common.component.LiveAdapter;
import website.petrov.noue.common.widget.FullscreenBindingRecyclerView;
import website.petrov.noue.model.FeedModel;

public final class FeedAdapter
        extends FullscreenBindingRecyclerView.SingleLayoutAdapter
        implements LiveAdapter<FeedModel> {
    @NonNull
    private List<FeedModel> data;

    public FeedAdapter() {
        super(R.layout.fragment_card);
        data = new ArrayList<>();
    }

    @Contract(pure = true)
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
    public void updateData(@NonNull List<FeedModel> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
