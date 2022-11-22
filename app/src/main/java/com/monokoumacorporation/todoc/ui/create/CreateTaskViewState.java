package com.monokoumacorporation.todoc.ui.create;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class CreateTaskViewState {

    @Nullable
    private final String taskInputErrorMessage;

    private final int checkboxErrorVisibility;

    @ColorRes
    private final int tartampionButtonBackgroundColor;

    @ColorRes
    private final int lucidiaButtonBackgroundColor;

    @ColorRes
    private final int circusButtonBackgroundColor;

    @ColorRes
    private final int tartampionTextButtonColor;

    @ColorRes
    private final int lucidiaTextButtonColor;

    @ColorRes
    private final int circusTextButtonColor;

    public CreateTaskViewState(@Nullable String taskInputErrorMessage,
                               int checkboxErrorVisibility,
                               int tartampionButtonBackgroundColor,
                               int lucidiaButtonBackgroundColor,
                               int circusButtonBackgroundColor,
                               int tartampionTextButtonColor,
                               int lucidiaTextButtonColor,
                               int circusTextButtonColor) {
        this.taskInputErrorMessage = taskInputErrorMessage;
        this.checkboxErrorVisibility = checkboxErrorVisibility;
        this.tartampionButtonBackgroundColor = tartampionButtonBackgroundColor;
        this.lucidiaButtonBackgroundColor = lucidiaButtonBackgroundColor;
        this.circusButtonBackgroundColor = circusButtonBackgroundColor;
        this.tartampionTextButtonColor = tartampionTextButtonColor;
        this.lucidiaTextButtonColor = lucidiaTextButtonColor;
        this.circusTextButtonColor = circusTextButtonColor;
    }

    @Nullable
    public String getTaskInputErrorMessage() {
        return taskInputErrorMessage;
    }

    public int getCheckboxErrorVisibility() {
        return checkboxErrorVisibility;
    }

    public int getTartampionButtonBackgroundColor() {
        return tartampionButtonBackgroundColor;
    }

    public int getLucidiaButtonBackgroundColor() {
        return lucidiaButtonBackgroundColor;
    }

    public int getCircusButtonBackgroundColor() {
        return circusButtonBackgroundColor;
    }

    public int getTartampionTextButtonColor() {
        return tartampionTextButtonColor;
    }

    public int getLucidiaTextButtonColor() {
        return lucidiaTextButtonColor;
    }

    public int getCircusTextButtonColor() {
        return circusTextButtonColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateTaskViewState that = (CreateTaskViewState) o;
        return checkboxErrorVisibility == that.checkboxErrorVisibility && tartampionButtonBackgroundColor == that.tartampionButtonBackgroundColor && lucidiaButtonBackgroundColor == that.lucidiaButtonBackgroundColor && circusButtonBackgroundColor == that.circusButtonBackgroundColor && tartampionTextButtonColor == that.tartampionTextButtonColor && lucidiaTextButtonColor == that.lucidiaTextButtonColor && circusTextButtonColor == that.circusTextButtonColor && Objects.equals(taskInputErrorMessage, that.taskInputErrorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskInputErrorMessage, checkboxErrorVisibility, tartampionButtonBackgroundColor, lucidiaButtonBackgroundColor, circusButtonBackgroundColor, tartampionTextButtonColor, lucidiaTextButtonColor, circusTextButtonColor);
    }

    @NonNull
    @Override
    public String toString() {
        return "CreateTaskViewState{" +
            "taskInputErrorMessage='" + taskInputErrorMessage + '\'' +
            ", checkboxErrorVisibility=" + checkboxErrorVisibility +
            ", tartampionButtonBackgroundColor=" + tartampionButtonBackgroundColor +
            ", lucidiaButtonBackgroundColor=" + lucidiaButtonBackgroundColor +
            ", circusButtonBackgroundColor=" + circusButtonBackgroundColor +
            ", tartampionTextButtonColor=" + tartampionTextButtonColor +
            ", lucidiaTextButtonColor=" + lucidiaTextButtonColor +
            ", circusTextButtonColor=" + circusTextButtonColor +
            '}';
    }
}
