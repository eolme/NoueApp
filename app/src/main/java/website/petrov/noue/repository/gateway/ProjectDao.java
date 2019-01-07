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
import website.petrov.noue.model.ProjectModel;

@Dao
public abstract class ProjectDao implements GenericDao<ProjectModel> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(@NonNull ProjectModel row);

    @Delete
    public abstract void delete(@NonNull ProjectModel row);

    @Query("SELECT * FROM project_table")
    public abstract LiveData<List<ProjectModel>> getAll();

    @Query("DELETE FROM project_table")
    public abstract void deleteAll();
}
