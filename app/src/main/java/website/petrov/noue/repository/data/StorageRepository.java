package website.petrov.noue.repository.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import website.petrov.noue.common.model.BaseModel;
import website.petrov.noue.common.repository.GenericDao;
import website.petrov.noue.model.FeedModel;
import website.petrov.noue.model.ProjectItemModel;
import website.petrov.noue.repository.gateway.FeedDao;
import website.petrov.noue.repository.gateway.ProjectDao;

public final class StorageRepository {
    @Nullable
    private static StorageRepository mRepo;

    @NonNull
    private FeedDao mFeedDao;
    @NonNull
    private ProjectDao mProjectDao;

    @Nullable
    private LiveData<List<FeedModel>> mFeed;
    @Nullable
    private LiveData<List<ProjectItemModel>> mProjects;

    private StorageRepository(@NonNull Application application) {
        final StorageDatabase db = StorageDatabase.getDatabase(application);
        mFeedDao = db.feedDao();
        mProjectDao = db.projectDao();
    }

    @Contract(pure = true)
    @Nullable
    public static StorageRepository getInstance() {
        return mRepo;
    }

    public static void initialize(@NonNull Application app) {
        mRepo = new StorageRepository(app);
    }

    @NonNull
    public LiveData<List<FeedModel>> getFeed() {
        if (mFeed == null) {
            mFeed = mFeedDao.getAll();
        }
        return mFeed;
    }

    @NonNull
    public LiveData<List<ProjectItemModel>> getProjects() {
        if (mProjects == null) {
            mProjects = mProjectDao.getAll();
        }
        return mProjects;
    }

    public void insertFeedModel(@NonNull FeedModel row) {
        new insertAsyncTask<>(mFeedDao).execute(row);
    }

    public void insertProjectModel(@NonNull ProjectItemModel row) {
        new insertAsyncTask<>(mProjectDao).execute(row);
    }

    private static class insertAsyncTask<M extends BaseModel, D extends GenericDao<M>> extends AsyncTask<M, Void, Void> {
        @NonNull
        private D mAsyncTaskDao;

        insertAsyncTask(@NonNull D dao) {
            mAsyncTaskDao = dao;
        }

        @SafeVarargs
        @Nullable
        @Override
        protected final Void doInBackground(@NotNull M... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
