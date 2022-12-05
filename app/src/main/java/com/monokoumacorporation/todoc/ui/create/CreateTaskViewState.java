package com.monokoumacorporation.todoc.ui.create;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.monokoumacorporation.todoc.ui.create.button.ProjectButtonViewStateItem;

import java.util.List;
import java.util.Objects;

public class CreateTaskViewState {

    @Nullable
    private final String taskInputErrorMessage;

    private final int checkboxErrorVisibility;

    @NonNull
    private final List<ProjectButtonViewStateItem> projectButtonViewStateItemList;

    public CreateTaskViewState(@Nullable String taskInputErrorMessage, int checkboxErrorVisibility, @NonNull List<ProjectButtonViewStateItem> projectButtonViewStateItemList) {
        this.taskInputErrorMessage = taskInputErrorMessage;
        this.checkboxErrorVisibility = checkboxErrorVisibility;
        this.projectButtonViewStateItemList = projectButtonViewStateItemList;
    }

    @Nullable
    public String getTaskInputErrorMessage() {
        return taskInputErrorMessage;
    }

    public int getCheckboxErrorVisibility() {
        return checkboxErrorVisibility;
    }

    @NonNull
    public List<ProjectButtonViewStateItem> getProjectButtonViewStateItemList() {
        return projectButtonViewStateItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateTaskViewState that = (CreateTaskViewState) o;
        return checkboxErrorVisibility == that.checkboxErrorVisibility && Objects.equals(taskInputErrorMessage, that.taskInputErrorMessage) && projectButtonViewStateItemList.equals(that.projectButtonViewStateItemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskInputErrorMessage, checkboxErrorVisibility, projectButtonViewStateItemList);
    }

    @NonNull
    @Override
    public String toString() {
        return "CreateTaskViewState{" +
            "taskInputErrorMessage='" + taskInputErrorMessage + '\'' +
            ", checkboxErrorVisibility=" + checkboxErrorVisibility +
            ", projectButtonViewStateItemList=" + projectButtonViewStateItemList +
            '}';
    }
}
