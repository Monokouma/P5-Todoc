package com.monokoumacorporation.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.monokoumacorporation.todoc.data.model.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTask(Task task);

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getTaskListLiveData();

    @Query("DELETE FROM task_table WHERE id=:taskId")
    int delete(long taskId);
}
