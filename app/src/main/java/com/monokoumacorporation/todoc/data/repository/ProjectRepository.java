package com.monokoumacorporation.todoc.data.repository;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;

import com.monokoumacorporation.todoc.data.dao.ProjectDao;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;

import java.util.List;

public class ProjectRepository {

    private ProjectDao projectDao;

    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @MainThread
    public LiveData<List<ProjectEntity>> getProjectEntityList() {
        return projectDao.getAll();
    }
}
