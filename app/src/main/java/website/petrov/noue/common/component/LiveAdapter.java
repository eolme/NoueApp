package website.petrov.noue.common.component;

import androidx.annotation.NonNull;

import java.util.List;

import website.petrov.noue.common.model.BaseModel;

public interface LiveAdapter<M extends BaseModel> {
    void updateData(@NonNull List<M> data);
}
