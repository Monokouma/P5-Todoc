package com.monokoumacorporation.todoc.ui.list;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class ListTaskViewState {

    @NonNull
    private final List<TaskViewStateItems> listActivityViewStateItemsList;

    public ListTaskViewState(@NonNull List<TaskViewStateItems> listActivityViewStateItemsList) {
        this.listActivityViewStateItemsList = listActivityViewStateItemsList;
    }

    @NonNull
    public List<TaskViewStateItems> getListActivityViewStateItemsList() {
        return listActivityViewStateItemsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTaskViewState that = (ListTaskViewState) o;
        return listActivityViewStateItemsList.equals(that.listActivityViewStateItemsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listActivityViewStateItemsList);
    }

    @NonNull
    @Override
    public String toString() {
        return "ListTaskViewState{" +
            "listActivityViewStateItemsList=" + listActivityViewStateItemsList +
            '}';
    }
}
