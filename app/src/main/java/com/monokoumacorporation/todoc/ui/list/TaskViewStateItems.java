package com.monokoumacorporation.todoc.ui.list;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import java.util.Objects;

public class TaskViewStateItems {

    @NonNull
    private final String taskName;

    @NonNull
    private final String projectName;

    @ColorRes
    private final int projectColor;

    private final long id;

    public TaskViewStateItems(@NonNull String taskName, @NonNull String projectName, int projectColor, long id) {
        this.taskName = taskName;
        this.projectName = projectName;
        this.projectColor = projectColor;
        this.id = id;
    }

    @NonNull
    public String getTaskName() {
        return taskName;
    }

    @NonNull
    public String getProjectName() {
        return projectName;
    }

    public int getProjectColor() {
        return projectColor;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskViewStateItems that = (TaskViewStateItems) o;
        return projectColor == that.projectColor && id == that.id && taskName.equals(that.taskName) && projectName.equals(that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, projectName, projectColor, id);
    }

    @NonNull
    @Override
    public String toString() {
        return "TaskViewStateItems{" +
            "taskName='" + taskName + '\'' +
            ", projectName='" + projectName + '\'' +
            ", projectColor=" + projectColor +
            ", id=" + id +
            '}';
    }
}
