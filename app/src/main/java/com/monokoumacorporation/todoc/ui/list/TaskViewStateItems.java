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

    public TaskViewStateItems(@NonNull String taskName, @NonNull String projectName, int projectColor) {
        this.taskName = taskName;
        this.projectName = projectName;
        this.projectColor = projectColor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskViewStateItems that = (TaskViewStateItems) o;
        return projectColor == that.projectColor && taskName.equals(that.taskName) && projectName.equals(that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, projectName, projectColor);
    }

    @NonNull
    @Override
    public String toString() {
        return "ListActivityViewStateItems{" +
            "taskName='" + taskName + '\'' +
            ", projectName='" + projectName + '\'' +
            ", projectColor=" + projectColor +
            '}';
    }
}
