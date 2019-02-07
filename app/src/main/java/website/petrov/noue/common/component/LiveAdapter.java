package website.petrov.noue.common.component;

import androidx.annotation.NonNull;

import java.util.List;

import website.petrov.noue.common.model.Model;

public interface LiveAdapter<M extends Model> {
    void updateData(@NonNull List<M> data);
}
