package website.petrov.noue.common.component;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;

import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Objects;

import website.petrov.noue.common.model.IdentifiableModel;
import website.petrov.noue.common.model.Model;
import website.petrov.noue.common.widget.BindingRecyclerView;

public final class RecyclerAdapter
        extends BindingRecyclerView.SingleLayoutAdapter {

    private final AsyncListDiffer<IdentifiableModel> mDiffer;

    public RecyclerAdapter(@LayoutRes int layout) {
        super(layout);
        mDiffer = new AsyncListDiffer<>(this, new DiffCallback());
    }

    @Contract(pure = true)
    @NonNull
    @Override
    protected Model getValueForPosition(int position) {
        return mDiffer.getCurrentList().get(position);
    }

    @Contract(pure = true)
    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    public void submitList(@NonNull List<IdentifiableModel> newData) {
        mDiffer.submitList(newData);
    }

    private final class DiffCallback extends DiffUtil.ItemCallback<IdentifiableModel> {
        @Contract(pure = true)
        @Override
        public boolean areItemsTheSame(@NonNull IdentifiableModel oldModel,
                                       @NonNull IdentifiableModel newModel) {
            return Objects.equals(oldModel.getId(), newModel.getId());
        }

        @Contract(pure = true)
        @Override
        public boolean areContentsTheSame(@NonNull IdentifiableModel oldModel,
                                          @NonNull IdentifiableModel newModel) {
            return Objects.equals(oldModel, newModel);
        }
    }
}
