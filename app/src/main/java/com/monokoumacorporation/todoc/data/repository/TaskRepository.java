package com.monokoumacorporation.todoc.data.repository;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.monokoumacorporation.todoc.data.dao.ProjectDao;
import com.monokoumacorporation.todoc.data.dao.TaskDao;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class TaskRepository {

    private final ProjectDao projectDao;
    private final TaskDao taskDao;

    public TaskRepository(ProjectDao projectDao, TaskDao taskDao) {
        this.projectDao = projectDao;
        this.taskDao = taskDao;
    }

    @WorkerThread
    public void createTask(long projectId, String taskName) {
        LocalDateTime now = LocalDateTime.now(); // TODO MONO Use "Clock" instead for UTs

        TaskEntity task = new TaskEntity(
            projectId,
            taskName,
            now.toEpochSecond(ZoneOffset.UTC)
        );

        taskDao.insertTask(task);
    }

    @MainThread
    public LiveData<List<TaskEntity>> getTaskListLiveData() {
        return taskDao.getTaskListLiveData();
    }

    @WorkerThread
    public void deleteTask(long taskId) {
        taskDao.delete(taskId);
    }

    @MainThread
    public LiveData<List<ProjectEntity>> getProjectListLiveData() {
        return projectDao.getAll();
    }
}
