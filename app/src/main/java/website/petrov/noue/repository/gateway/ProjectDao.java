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
import website.petrov.noue.model.ProjectItemModel;

@Dao
public abstract class ProjectDao implements GenericDao<ProjectItemModel> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(@NonNull ProjectItemModel row);

    @Delete
    public abstract void delete(@NonNull ProjectItemModel row);

    @Query("SELECT * FROM project_item_table")
    public abstract LiveData<List<ProjectItemModel>> getAll();

    @Query("DELETE FROM project_item_table")
    public abstract void deleteAll();
}
