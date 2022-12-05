package com.monokoumacorporation.todoc.ui.create.button;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import java.util.Objects;

public class ProjectButtonViewStateItem {

    private final int id;

    @ColorRes
    private final int backgroundColor;

    @StringRes
    private final int buttonName;


    public ProjectButtonViewStateItem(int id, int backgroundColor, int buttonName) {
        this.id = id;
        this.backgroundColor = backgroundColor;
        this.buttonName = buttonName;
    }

    public int getId() {
        return id;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getButtonName() {
        return buttonName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectButtonViewStateItem that = (ProjectButtonViewStateItem) o;
        return id == that.id && backgroundColor == that.backgroundColor && buttonName == that.buttonName;
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

