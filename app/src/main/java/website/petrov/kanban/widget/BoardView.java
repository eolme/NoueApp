package website.petrov.kanban.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import website.petrov.kanban.component.BoardAdapter;
import website.petrov.noue.R;
import website.petrov.noue.common.model.Model;
import website.petrov.noue.common.widget.BindingRecyclerView;
import website.petrov.noue.common.widget.FullscreenLayout;
import website.petrov.noue.utils.ContextUtils;

public final class BoardView extends FullscreenLayout {
    private static final int LOADER_ID = 913483;
    final Subject<List<Model>> columns;
    final Disposable subscription;
    BindingRecyclerView board;

    public BoardView(@NonNull Context context) {
        this(context, null);
    }

    public BoardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BoardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final AppCompatActivity activity = (AppCompatActivity) ContextUtils.requireActivity(context);
        final AsyncLayoutInflater inflater = new AsyncLayoutInflater(activity);

        columns = PublishSubject.create();
        final BoardAdapter adapter = new BoardAdapter();
        subscription = columns.subscribe(adapter::updateData);

        LoaderManager.getInstance(activity).initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<List<Model>>() {
            @NonNull
            @Override
            public Loader<List<Model>> onCreateLoader(int id, @Nullable Bundle args) {
                return new DataLoader(activity);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<List<Model>> loader, @NonNull List<Model> data) {
                if (data.isEmpty()) {
                    return;
                }

                columns.onNext(data);
                columns.onComplete();

                subscription.dispose();
            }

            @Override
            public void onLoaderReset(@NonNull Loader<List<Model>> loader) {
                if (loader.isReset()) {
                    loader.forceLoad();
                }
            }
        });

        inflater.inflate(R.layout.view_board, this, (view, resid, parent) -> {
            if (parent != null) {
                board = (BindingRecyclerView) view;
                board.setAdapter(adapter);
                parent.addView(view);
            }
        });
    }

    private static class DataLoader extends AsyncTaskLoader<List<Model>> {
        final AtomicBoolean loaded = new AtomicBoolean(false);
        final AtomicReference<List<Model>> mData = new AtomicReference<>(new ArrayList<>());

        DataLoader(@NonNull Context context) {
            super(context);
            onContentChanged();
        }

        @Override
        protected void onStartLoading() {
            if (takeContentChanged()) {
                forceLoad();
            } else if (loaded.get()) {
                deliverResult(mData.get());
            }
        }

        @Override
        public void deliverResult(@Nullable List<Model> data) {
            mData.set(data);
            loaded.set(true);
            super.deliverResult(data);
        }

        @NonNull
        @Override
        public List<Model> loadInBackground() {
            return new ArrayList<>();
        }

        @Override
        protected void onReset() {
            super.onReset();
            onStopLoading();
            if (loaded.get()) {
                mData.get().clear();
                loaded.lazySet(false);
            }
        }
    }
}
