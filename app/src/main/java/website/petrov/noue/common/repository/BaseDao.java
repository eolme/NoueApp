package website.petrov.noue.common.repository;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import website.petrov.noue.common.model.Model;

public interface BaseDao<M extends Model> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(M model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(M[] models);

    @Delete
    void delete(M model);
}
