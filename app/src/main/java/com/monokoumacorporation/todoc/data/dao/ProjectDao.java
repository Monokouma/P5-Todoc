package com.monokoumacorporation.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.entity.ProjectWithTasks;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void insert(ProjectEntity project);

    @Query("SELECT * FROM project_table")
    LiveData<List<ProjectEntity>> getAll();

    @Query("SELECT * FROM project_table")
    LiveData<List<ProjectWithTasks>> getAllProjectWithTasks();

}
