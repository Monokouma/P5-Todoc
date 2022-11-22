package com.monokoumacorporation.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.monokoumacorporation.todoc.data.model.Project;

import java.util.List;

@Dao
public interface ProjectDAO {

    @Insert
    long insert(Project project);

    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> getAll();

}
