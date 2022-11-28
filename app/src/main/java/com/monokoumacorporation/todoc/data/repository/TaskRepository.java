package com.monokoumacorporation.todoc.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.monokoumacorporation.todoc.data.dao.ProjectDAO;
import com.monokoumacorporation.todoc.data.dao.TaskDAO;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class TaskRepository {

    private int id;
    private TaskDAO taskDAO;
    private ProjectDAO projectDAO;
    private LiveData<List<TaskEntity>> taskListLiveData;
    private LiveData<List<ProjectEntity>> projectListLiveData;

    public TaskRepository(Application application, ProjectDAO projectDAO, TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
        this.projectDAO = projectDAO;
        this.taskListLiveData = taskDAO.getTaskListLiveData();
        this.projectListLiveData = projectDAO.getAll();
    }

    public void createTask(int projectId, String taskName) {
        LocalDateTime now = LocalDateTime.now();

        TaskEntity task = new TaskEntity(
            id++,
            projectId,
            taskName,
            now.toEpochSecond(ZoneOffset.UTC)
        );

        AsyncTask.execute(() -> {
            taskDAO.insertTask(task);
        });

    }

    public LiveData<List<TaskEntity>> getTaskListLiveData() {
        return taskListLiveData;
    }

    public void deleteTask(long taskId) {
        AsyncTask.execute(() -> {
            taskDAO.delete(taskId);
        });
    }

    public LiveData<List<ProjectEntity>> getProjectistLiveData() {
        return projectListLiveData;
    }
}