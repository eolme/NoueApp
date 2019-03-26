package website.petrov.noue.repository.gateway;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import website.petrov.noue.common.repository.BaseDao;
import website.petrov.noue.model.ProjectItemModel;

@Dao
public abstract class ProjectDao implements BaseDao<ProjectItemModel> {
    @Query("SELECT * FROM project_item_table")
    public abstract LiveData<List<ProjectItemModel>> getAll();

    @Query("DELETE FROM project_item_table")
    public abstract void deleteAll();
}
