package website.petrov.noue.repository.gateway;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import website.petrov.noue.common.repository.GenericDao;
import website.petrov.noue.model.FeedModel;

@Dao
public abstract class FeedDao implements GenericDao<FeedModel> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(@NonNull FeedModel row);

    @Delete
    public abstract void delete(@NonNull FeedModel row);

    @Query("SELECT * FROM feed_table")
    public abstract LiveData<List<FeedModel>> getAll();

    @Query("DELETE FROM feed_table")
    public abstract void deleteAll();
}
