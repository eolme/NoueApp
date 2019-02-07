package website.petrov.noue.common.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import website.petrov.noue.common.model.Model;

public interface GenericDao<M extends Model> {
    void insert(@NonNull M row);

    void delete(@NonNull M row);

    LiveData<List<M>> getAll();

    void deleteAll();
}
