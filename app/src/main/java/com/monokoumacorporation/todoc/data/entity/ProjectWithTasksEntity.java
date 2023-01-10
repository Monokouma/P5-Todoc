package com.monokoumacorporation.todoc.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

@Entity
public class ProjectWithTasksEntity {

    @Embedded
    private final ProjectEntity project;

    @Relation(
        entity = TaskEntity.class,
        parentColumn = "id",
        entityColumn = "projectId"
    )
    private final List<TaskEntity> taskEntities;

    public ProjectWithTasksEntity(ProjectEntity project, List<TaskEntity> taskEntities) {
        this.project = project;
        this.taskEntities = taskEntities;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public List<TaskEntity> getTaskEntities() {
        return taskEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectWithTasksEntity that = (ProjectWithTasksEntity) o;
        return Objects.equals(project, that.project) && Objects.equals(taskEntities, that.taskEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, taskEntities);
    }

    @NonNull
    @Override
    public String toString() {
        return "ProjectWithTasks{" +
            "project=" + project +
            ", taskEntities=" + taskEntities +
            '}';
    }
}
