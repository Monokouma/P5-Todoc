package com.monokoumacorporation.todoc.data.entity;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "task_table")
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    private final long projectId;

    @NonNull
    private final String name;

    private final long creationTimestamp;

    @Ignore
    public TaskEntity(long projectId, @NonNull String name, long creationTimestamp) {
        this(0, projectId, name, creationTimestamp);
    }

    @VisibleForTesting
    public TaskEntity(
        long id,
        long projectId,
        @NonNull String name,
        long creationTimestamp
    ) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity task = (TaskEntity) o;
        return id == task.id && Objects.equals(projectId, task.projectId) && creationTimestamp == task.creationTimestamp && name.equals(task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, name, creationTimestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", projectId=" + projectId +
            ", name='" + name + '\'' +
            ", creationTimestamp=" + creationTimestamp +
            '}';
    }
}
