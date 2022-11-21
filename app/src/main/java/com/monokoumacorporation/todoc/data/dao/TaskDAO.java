package com.monokoumacorporation.todoc.data.dao;

import androidx.room.Insert;

import com.monokoumacorporation.todoc.data.model.Task;

public interface TaskDAO {

    @Insert
    long insertTask(Task task);
}
