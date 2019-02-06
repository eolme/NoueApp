package website.petrov.noue.utilities;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Contract;

import java.lang.reflect.Type;
import java.util.ArrayList;

public final class Converters {
    private static Gson gson = new Gson();

    @TypeConverter
    @Contract("null -> fail")
    public static ArrayList<String> fromString(@Nullable String value) {
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    @Contract("null -> fail")
    public static String fromArrayList(@Nullable ArrayList<String> list) {
        return gson.toJson(list);
    }

    private Converters() {} // hide constructor
}