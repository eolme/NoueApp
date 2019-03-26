package website.petrov.noue.repository.gateway;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import website.petrov.noue.common.repository.BaseDao;
import website.petrov.noue.model.FeedModel;

@Dao
public abstract class FeedDao implements BaseDao<FeedModel> {
    @Query("SELECT * FROM feed_table")
    public abstract LiveData<List<FeedModel>> getAll();

    @Query("DELETE FROM feed_table")
    public abstract void deleteAll();
}
