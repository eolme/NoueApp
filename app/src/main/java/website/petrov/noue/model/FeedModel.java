package website.petrov.noue.model;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

import website.petrov.noue.common.model.BaseModel;

@Fts4
@Entity(tableName = "feed_table")
public final class FeedModel extends BaseModel {
    @ColumnInfo(name = "feed_title")
    private String title;

    @ColumnInfo(name = "feed_description")
    private String description;

    @ColumnInfo(name = "feed_avatars")
    private ArrayList<String> avatars = new ArrayList<>();

    @ColumnInfo(name = "rowid")
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    public FeedModel(String title, String description, ArrayList<String> avatars, Integer id) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.avatars.addAll(avatars);
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FeedModel)) {
            return false;
        }
        final FeedModel comp = (FeedModel) obj;
        return this.title.contentEquals(comp.title) &&
                this.description.contentEquals(comp.description) &&
                this.avatars.equals(comp.avatars);
    }

    @Contract(pure = true)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Contract(pure = true)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Contract(pure = true)
    public ArrayList<String> getAvatars() {
        return this.avatars;
    }

    public void setAvatars(ArrayList<String> avatars) {
        this.avatars.clear();
        this.avatars.addAll(avatars);
    }

    @Contract(pure = true)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}