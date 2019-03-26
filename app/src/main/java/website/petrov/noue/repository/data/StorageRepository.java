package website.petrov.noue.repository.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.Contract;

import java.util.List;

import website.petrov.noue.common.model.Model;
import website.petrov.noue.common.repository.BaseDao;
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

    private LiveData<List<FeedModel>> mFeedLive;
    private LiveData<List<ProjectItemModel>> mProjectsLive;

    private StorageRepository(@NonNull Application application) {
        final StorageDatabase db = StorageDatabase.getDatabase(application);
        mFeedDao = db.feedDao();
        mProjectDao = db.projectDao();
    }

    @Contract(value = "-> !null", pure = true)
    public static StorageRepository getInstance() {
        return mRepo;
    }

    public static void initialize(@NonNull Application app) {
        mRepo = new StorageRepository(app);
    }

    @NonNull
    public LiveData<List<FeedModel>> getFeed() {
        if (mFeedLive == null) {
            mFeedLive = mFeedDao.getAll();
        }
        return mFeedLive;
    }

    @NonNull
    public LiveData<List<ProjectItemModel>> getProjects() {
        if (mProjectsLive == null) {
            mProjectsLive = mProjectDao.getAll();
        }
        return mProjectsLive;
    }

    public void insertFeed(@NonNull FeedModel... row) {
        new InsertAsyncTask<>(mFeedDao).execute(row);
    }

    public void insertProject(@NonNull ProjectItemModel... row) {
        new InsertAsyncTask<>(mProjectDao).execute(row);
    }

    private static class InsertAsyncTask<M extends Model> extends AsyncTask<M, Void, Void> {
        @NonNull
        private BaseDao<M> mAsyncTaskDao;

        InsertAsyncTask(@NonNull BaseDao<M> dao) {
            mAsyncTaskDao = dao;
        }

        @SafeVarargs
        @Nullable
        @Override
        protected final Void doInBackground(@NonNull M... params) {
            mAsyncTaskDao.insertAll(params);
            return null;
        }
    }
}
