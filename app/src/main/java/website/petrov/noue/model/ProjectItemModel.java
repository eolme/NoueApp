package website.petrov.noue.model;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import org.jetbrains.annotations.Contract;

import website.petrov.noue.common.model.IdentifiableModel;
import website.petrov.noue.common.model.Model;

@Entity(tableName = "project_item_table", primaryKeys = {"id"}, indices = {@Index(value = {"id"},
        unique = true)})
public final class ProjectItemModel extends IdentifiableModel implements Model {
    @ColumnInfo(name = "project_item_title")
    private String title;

    @ColumnInfo(name = "project_item_description")
    private String description;

    @ColumnInfo(name = "project_item_background")
    private Integer background;

    @ColumnInfo(name = "project_item_badge")
    private Boolean badge;

    public ProjectItemModel(String title, String description, Boolean badge, Integer background, Integer id) {
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
        if (!(obj instanceof ProjectItemModel)) {
            return false;
        }
        final ProjectItemModel comp = (ProjectItemModel) obj;
        return this.title.contentEquals(comp.title) &&
                this.description.contentEquals(comp.description) &&
                this.background.equals(comp.background);
    }

    @Override
    public int hashCode() {
        return 17 ^
                title.hashCode() ^
                description.hashCode() ^
                background.hashCode() ^
                badge.hashCode();
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
}
