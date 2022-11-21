package com.monokoumacorporation.todoc.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(foreignKeys = @ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "projectId"))

public class Task {

    @PrimaryKey(autoGenerate = true)

    private long id;

    private String projectId;

    @NonNull
    private String name;


    private long creationTimestamp;

    public Task(long id,
                String projectId,
                @NonNull String name,
                long creationTimestamp) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    public long getId() {
        return id;
    }

    public String getProjectId() {
        return projectId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
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
