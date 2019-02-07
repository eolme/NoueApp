package website.petrov.noue.common.model;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

public interface Model {
    @Contract(value = "null -> false", pure = true)
    boolean equals(@Nullable Object obj);
    int hashCode();
}
