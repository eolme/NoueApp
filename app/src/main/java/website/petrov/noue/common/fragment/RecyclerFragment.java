package website.petrov.noue.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.Contract;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import website.petrov.noue.R;
import website.petrov.noue.common.component.RecyclerAdapter;
import website.petrov.noue.common.model.IdentifiableModel;
import website.petrov.noue.common.viewmodel.BaseMutableContextViewModel;

public abstract class RecyclerFragment extends Fragment {
    private WeakReference<RecyclerView> recyclerViewReference;
    private boolean isCacheLoaded = false;

    @Contract(value = "-> new", pure = true)
    @NonNull
    protected abstract RecyclerAdapter getRecyclerAdapter();

    @Contract(value = "-> new", pure = true)
    @NonNull
    protected abstract BaseMutableContextViewModel getViewModel();

    @Contract(pure = true)
    @NonNull
    protected abstract LiveData<List<IdentifiableModel>> getCachedModels();

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View self = inflater.inflate(R.layout.fragment_recycler, container, false);
        if (self != null) {
            final RecyclerView recyclerView = ViewCompat.requireViewById(self, R.id.recycler_list);
            recyclerView.setAdapter(getRecyclerAdapter());
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(20);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

            final BaseMutableContextViewModel viewModel = getViewModel();
            getCachedModels().observe(this, cachedModels -> {
                if (isCacheLoaded) {
                    return;
                }

                isCacheLoaded = true;

                updateData(cachedModels);

                viewModel.getData().observe(this, liveModels -> {
                    List<IdentifiableModel> data;

                    if (cachedModels.isEmpty()) {
                        data = liveModels;
                    } else {
                        data = merge(cachedModels, liveModels);
                    }

                    updateData(data);

                    viewModel.setData(data);
                });
            });

            recyclerViewReference = new WeakReference<>(recyclerView);
        }
        return self;
    }

    @Nullable
    private RecyclerAdapter getAdapter() {
        final RecyclerView recycler = recyclerViewReference.get();
        return recycler != null ? (RecyclerAdapter) recycler.getAdapter() : null;
    }

    private void updateData(@NonNull List<IdentifiableModel> newData) {
        final RecyclerAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.submitList(newData);
        }
    }

    @NonNull
    private List<IdentifiableModel> merge(@NonNull List<IdentifiableModel> cachedModels,
                                          @NonNull List<IdentifiableModel> liveModels) {
        final List<IdentifiableModel> result =
                new ArrayList<>(cachedModels.size() + liveModels.size());

        int i;
        int size = liveModels.size();

        for (IdentifiableModel cachedModel : cachedModels) {
            i = 0;

            while (i < size &&
                    !Objects.equals(liveModels.get(i).getId(), cachedModel.getId())) {
                ++i;
            }

            result.add(i != size ? liveModels.get(i) : cachedModel);
        }

        for (IdentifiableModel liveModel : liveModels) {
            if (!result.contains(liveModel)) {
                result.add(liveModel);
            }
        }

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        final RecyclerView recycler = recyclerViewReference.get();
        if (recycler != null && recycler.getAdapter() != null) {
            recycler.setAdapter(null);
        }

        recyclerViewReference.enqueue();
    }
}
