package website.petrov.noue.common.model;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

public abstract class BaseModel {
    @Contract(value = "null -> false", pure = true)
    public abstract boolean equals(@Nullable Object obj);
}
