package website.petrov.noue.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import website.petrov.noue.common.model.BaseModel;

public final class SlideModel extends BaseModel {
    private final String title;

    public SlideModel(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    @Contract(pure = true)
    @Override
    public String toString() {
        return title;
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SlideModel)) {
            return false;
        }
        final SlideModel comp = (SlideModel) obj;
        return title.contentEquals(comp.title);
    }
}
