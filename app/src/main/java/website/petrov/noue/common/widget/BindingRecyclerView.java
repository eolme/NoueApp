package website.petrov.noue.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.Contract;

import website.petrov.noue.BR;
import website.petrov.noue.R;
import website.petrov.noue.common.model.Model;
import website.petrov.noue.utils.ContextUtils;

public final class BindingRecyclerView extends RecyclerView {
    public BindingRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public BindingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BindingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        fitWindow(context);
    }

    private void fitWindow(@NonNull Context context) {
        if (isInEditMode()) {
            return; // Layout Editor
        }

        super.setFitsSystemWindows(false);
        super.setClipToPadding(false);

        @Px final int bottomPadding =
                ContextUtils.getNavigationBarHeight(context) +
                        (int) getResources().getDimension(R.dimen.margin_scroll_default);
        setPaddingRelative(0, 0, 0, bottomPadding);

//        @Px final int height = ContextUtils.getScreenHeight(context);
//        setMinimumHeight(height);
    }

    @Contract(pure = true)
    @Override
    public boolean getFitsSystemWindows() {
        return false;
    }

    @Override
    public void setFitsSystemWindows(boolean ignored) {
        super.setFitsSystemWindows(false);
    }

    @Contract(pure = true)
    @Override
    public boolean getClipToPadding() {
        return false;
    }

    @Override
    public void setClipToPadding(boolean ignored) {
        super.setClipToPadding(false);
    }

    public abstract static class BindingAdapter extends RecyclerView.Adapter<BindingViewHolder> {

        @Contract("_, _ -> new")
        @NonNull
        @Override
        public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            final ViewDataBinding binding =
                    DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
            return new BindingViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
            final Model value = getValueForPosition(position);
            holder.bind(value);
        }

        @Override
        public final int getItemViewType(int position) {
            return getLayoutForPosition(position);
        }

        @LayoutRes
        protected abstract int getLayoutForPosition(int position);

        @NonNull
        protected abstract Model getValueForPosition(int position);
    }

    static class BindingViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        private final ViewDataBinding binding;

        BindingViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull Model value) {
            binding.setVariable(BR.value, value);
            binding.executePendingBindings();
        }
    }

    public abstract static class SingleLayoutAdapter extends BindingAdapter {
        @LayoutRes
        private final int layoutId;

        public SingleLayoutAdapter(@LayoutRes int layoutId) {
            this.layoutId = layoutId;

            setHasStableIds(true);
        }

        @Override
        protected int getLayoutForPosition(int ignored) {
            return layoutId;
        }
    }
}
