package com.monokoumacorporation.todoc.ui.create;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class CreateTaskViewState {

    @Nullable
    private final String taskInputErrorMessage;

    private final int checkboxErrorVisibility;

    @NonNull
    private final Boolean isTartampionCheckboxChecked;

    @NonNull
    private final Boolean isLucidiaCheckboxChecked;

    @NonNull
    private final Boolean isCircusCheckboxChecked;

    public CreateTaskViewState(@Nullable String taskInputErrorMessage,
                               int checkboxErrorVisibility,
                               @NonNull Boolean isTartampionCheckboxChecked,
                               @NonNull Boolean isLucidiaCheckboxChecked,
                               @NonNull Boolean isCircusCheckboxChecked) {

        this.taskInputErrorMessage = taskInputErrorMessage;
        this.checkboxErrorVisibility = checkboxErrorVisibility;
        this.isTartampionCheckboxChecked = isTartampionCheckboxChecked;
        this.isLucidiaCheckboxChecked = isLucidiaCheckboxChecked;
        this.isCircusCheckboxChecked = isCircusCheckboxChecked;
    }

    @Nullable
    public String getTaskInputErrorMessage() {
        return taskInputErrorMessage;
    }

    public int getCheckboxErrorVisibility() {
        return checkboxErrorVisibility;
    }

    @NonNull
    public Boolean getTartampionCheckboxChecked() {
        return isTartampionCheckboxChecked;
    }

    @NonNull
    public Boolean getLucidiaCheckboxChecked() {
        return isLucidiaCheckboxChecked;
    }

    @NonNull
    public Boolean getCircusCheckboxChecked() {
        return isCircusCheckboxChecked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateTaskViewState that = (CreateTaskViewState) o;
        return checkboxErrorVisibility == that.checkboxErrorVisibility && Objects.equals(taskInputErrorMessage, that.taskInputErrorMessage) && isTartampionCheckboxChecked.equals(that.isTartampionCheckboxChecked) && isLucidiaCheckboxChecked.equals(that.isLucidiaCheckboxChecked) && isCircusCheckboxChecked.equals(that.isCircusCheckboxChecked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskInputErrorMessage, checkboxErrorVisibility, isTartampionCheckboxChecked, isLucidiaCheckboxChecked, isCircusCheckboxChecked);
    }

    @NonNull
    @Override
    public String toString() {
        return "CreateTaskViewState{" +
            "taskInputErrorMessage='" + taskInputErrorMessage + '\'' +
            ", checkboxErrorVisibility=" + checkboxErrorVisibility +
            ", isTartampionCheckboxChecked=" + isTartampionCheckboxChecked +
            ", isLucidiaCheckboxChecked=" + isLucidiaCheckboxChecked +
            ", isCircusCheckboxChecked=" + isCircusCheckboxChecked +
            '}';
    }
}
