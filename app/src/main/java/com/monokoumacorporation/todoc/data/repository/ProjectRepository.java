package com.monokoumacorporation.todoc.data.repository;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;

import com.monokoumacorporation.todoc.data.dao.ProjectDao;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class ProjectRepository {

    private ProjectDao projectDao;

    @Inject
    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @MainThread
    public LiveData<List<ProjectEntity>> getProjectEntityList() {
        return projectDao.getAll();
    }
}
