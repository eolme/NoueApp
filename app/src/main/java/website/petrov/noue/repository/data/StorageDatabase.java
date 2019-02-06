package website.petrov.noue.repository.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.jetbrains.annotations.Contract;

import website.petrov.noue.BuildConfig;
import website.petrov.noue.model.FeedModel;
import website.petrov.noue.model.ProjectItemModel;
import website.petrov.noue.repository.gateway.FeedDao;
import website.petrov.noue.repository.gateway.ProjectDao;
import website.petrov.noue.utilities.Converters;

@Database(entities = {FeedModel.class, ProjectItemModel.class}, version = BuildConfig.VERSION_CODE)
@TypeConverters({Converters.class})
public abstract class StorageDatabase extends RoomDatabase {
    @Nullable
    private static volatile StorageDatabase mInstance;

    @Contract("_ -> !null")
    static StorageDatabase getDatabase(@NonNull Context context) {
        if (mInstance == null) {
            synchronized (StorageDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            StorageDatabase.class, "storage_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return mInstance;
    }

    public abstract FeedDao feedDao();

    public abstract ProjectDao projectDao();
}
