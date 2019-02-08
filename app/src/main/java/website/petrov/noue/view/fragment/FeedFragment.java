package website.petrov.noue.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import website.petrov.noue.R;
import website.petrov.noue.common.widget.FullscreenBindingRecyclerView;
import website.petrov.noue.view.component.FeedAdapter;
import website.petrov.noue.viewmodel.FeedViewModel;

public final class FeedFragment extends Fragment {
    @NonNull
    private final FeedViewModel viewModel;
    @NonNull
    private final FeedAdapter adapter;

    public FeedFragment() {
        viewModel = new FeedViewModel();
        adapter = new FeedAdapter();
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View self = inflater.inflate(R.layout.fragment_feed, container, false);
        if (self != null) {
            final FullscreenBindingRecyclerView recyclerView = ViewCompat.requireViewById(self, R.id.feed_list);
            final ContentLoadingProgressBar bar = ViewCompat.requireViewById(self, R.id.feed_progress);

            recyclerView.setAdapter(adapter);
            viewModel.getData().observe(this, adapter::updateData);

            self.postDelayed(() -> {
                bar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }, 2000);
        }
        return self;
    }
}
