package com.monokoumacorporation.todoc.ui.create.button;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import java.util.Objects;

public class ProjectButtonViewStateItem {

    private final long id;

    @ColorInt
    private final int backgroundColor;

    private final String buttonName;

    public ProjectButtonViewStateItem(long id, @ColorInt int backgroundColor, String buttonName) {
        this.id = id;
        this.backgroundColor = backgroundColor;
        this.buttonName = buttonName;
    }

    public long getId() {
        return id;
    }

    @ColorInt
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public String getButtonName() {
        return buttonName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectButtonViewStateItem that = (ProjectButtonViewStateItem) o;
        return id == that.id && backgroundColor == that.backgroundColor && Objects.equals(buttonName, that.buttonName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, backgroundColor, buttonName);
    }

    @NonNull
    @Override
    public String toString() {
        return "ProjectButtonViewStateItem{" +
            "id=" + id +
            ", backgroundColor=" + backgroundColor +
            ", buttonName=" + buttonName +
            '}';
    }
}

