package com.monokoumacorporation.todoc.ui.list;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class ListTaskViewState {

    @NonNull
    private final List<TaskViewStateItems> listActivityViewStateItemsList;

    private final int emptyListMessageVisibility;

    public ListTaskViewState(@NonNull List<TaskViewStateItems> listActivityViewStateItemsList, int emptyListMessageVisibility) {
        this.listActivityViewStateItemsList = listActivityViewStateItemsList;
        this.emptyListMessageVisibility = emptyListMessageVisibility;
    }

    @NonNull
    public List<TaskViewStateItems> getListActivityViewStateItemsList() {
        return listActivityViewStateItemsList;
    }

    public int getEmptyListMessageVisibility() {
        return emptyListMessageVisibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTaskViewState that = (ListTaskViewState) o;
        return emptyListMessageVisibility == that.emptyListMessageVisibility && listActivityViewStateItemsList.equals(that.listActivityViewStateItemsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listActivityViewStateItemsList, emptyListMessageVisibility);
    }

    @NonNull
    @Override
    public String toString() {
        return "ListTaskViewState{" +
            "listActivityViewStateItemsList=" + listActivityViewStateItemsList +
            ", emptyListMessageVisibility=" + emptyListMessageVisibility +
            '}';
    }
}
