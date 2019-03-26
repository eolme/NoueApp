package website.petrov.noue.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public final class CastUtils {
    private CastUtils() {
    } // hide constructor

    @Contract("null -> null")
    public static Integer toInteger(@Nullable Double num) {
        return num == null ? null : num.intValue();
    }

    @NonNull
    public static <N, T extends N> T[] toArray(@NonNull List<N> list, @NonNull Class<T[]> type) {
        int size = list.size();
        final T[] result =
                Objects.requireNonNull(type.cast(Array.newInstance(type.getComponentType(), size)));
        for (int i = 0; i < size; ++i) {
            //noinspection unchecked
            result[i] = (T) list.get(i);
        }
        return result;
    }
}
