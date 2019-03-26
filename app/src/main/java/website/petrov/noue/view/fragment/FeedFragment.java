package website.petrov.noue.view.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import org.jetbrains.annotations.Contract;

import website.petrov.noue.R;
import website.petrov.noue.common.component.RecyclerAdapter;
import website.petrov.noue.common.fragment.RecyclerFragment;
import website.petrov.noue.common.viewmodel.BaseMutableContextViewModel;
import website.petrov.noue.repository.data.StorageRepository;
import website.petrov.noue.viewmodel.FeedViewModel;

public final class FeedFragment extends RecyclerFragment {

    @Contract(value = "-> new", pure = true)
    @NonNull
    @Override
    protected RecyclerAdapter getRecyclerAdapter() {
        return new RecyclerAdapter(R.layout.fragment_feed_item);
    }

    @Contract(value = "-> new", pure = true)
    @NonNull
    @Override
    protected BaseMutableContextViewModel getViewModel() {
        return ViewModelProviders.of(this).get(FeedViewModel.class);
    }

    @SuppressWarnings("unchecked")
    @Contract(pure = true)
    @NonNull
    @Override
    protected LiveData getCachedModels() {
        return StorageRepository.getInstance().getFeed();
    }
}
