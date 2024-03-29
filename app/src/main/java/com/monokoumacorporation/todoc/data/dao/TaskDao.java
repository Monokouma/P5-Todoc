package com.monokoumacorporation.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.monokoumacorporation.todoc.data.entity.ProjectWithTasksEntity;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTask(TaskEntity task);

    @Query("SELECT * FROM task_table")
    LiveData<List<TaskEntity>> getTaskListLiveData();

    @Query("DELETE FROM task_table WHERE id=:taskId")
    void delete(long taskId);

    @Transaction
    @Query("SELECT * FROM project_table")
    LiveData<List<ProjectWithTasksEntity>> getAllProjectWithTasks();
}
