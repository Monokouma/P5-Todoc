package com.monokoumacorporation.todoc.data.entity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "project_table")
public class ProjectEntity {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    @NonNull
    private final String name;

    @ColorInt
    private final int colorInt;

    @Ignore
    public ProjectEntity(
        @NonNull String name,
        @ColorInt int colorInt
    ) {
        this(0, name, colorInt);
    }

    @VisibleForTesting
    public ProjectEntity(
        long id,
        @NonNull String name,
        @ColorInt int colorInt
    ) {
        this.id = id;
        this.name = name;
        this.colorInt = colorInt;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @ColorInt
    public int getColorInt() {
        return colorInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity project = (ProjectEntity) o;
        return id == project.id && colorInt == project.colorInt && name.equals(project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, colorInt);
    }

    @NonNull
    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", color=" + colorInt +
            '}';
    }
}
