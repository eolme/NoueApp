package website.petrov.noue.model;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.Contract;

import website.petrov.noue.common.model.BaseModel;

@Fts4
@Entity(tableName = "project_table")
public final class ProjectModel extends BaseModel {
    @ColumnInfo(name = "project_title")
    private String title;

    @ColumnInfo(name = "project_description")
    private String description;

    @ColumnInfo(name = "project_backgroud")
    private Integer background;

    @ColumnInfo(name = "project_badge")
    private Boolean badge;

    @ColumnInfo(name = "rowid")
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    public ProjectModel(String title, String description, boolean badge, Integer background, Integer id) {
        this.title = title;
        this.description = description;
        this.background = background;
        this.badge = badge;
        this.id = id;
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ProjectModel)) {
            return false;
        }
        final ProjectModel comp = (ProjectModel) obj;
        return this.title.contentEquals(comp.title) &&
                this.description.contentEquals(comp.description) &&
                this.background.equals(comp.background);
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
    public Integer getBackground() {
        return this.background;
    }

    public void setBackground(Integer background) {
        this.background = background;
    }

    @Contract(pure = true)
    public Boolean getBadge() {
        return this.badge;
    }

    public void setBadge(Boolean badge) {
        this.badge = badge;
    }

    @Contract(pure = true)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
