package website.petrov.noue.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.Contract;

import website.petrov.noue.BR;

public final class FullscreenBindingRecyclerView extends FullscreenRecyclerView {
    public FullscreenBindingRecyclerView(@NonNull Context context) {
        super(context);
    }

    public FullscreenBindingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FullscreenBindingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public abstract static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Contract("_, _ -> new")
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            final ViewDataBinding binding =
                    DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Object value = getValueForPosition(position);
            holder.bind(value);
        }

        @Override
        public final int getItemViewType(int position) {
            return getLayoutForPosition(position);
        }

        @IdRes
        protected abstract int getLayoutForPosition(int position);

        @NonNull
        protected abstract Object getValueForPosition(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        private final ViewDataBinding binding;

        ViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull Object value) {
            binding.setVariable(BR.value, value);
            binding.executePendingBindings();
        }
    }

    public abstract static class SingleLayoutAdapter extends Adapter {
        @IdRes
        private final int layoutId;

        public SingleLayoutAdapter(@IdRes int layoutId) {
            this.layoutId = layoutId;
        }

        @Override
        protected int getLayoutForPosition(int ignored) {
            return layoutId;
        }
    }
}
