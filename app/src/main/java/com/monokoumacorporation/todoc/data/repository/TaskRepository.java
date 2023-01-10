package com.monokoumacorporation.todoc.data.repository;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.monokoumacorporation.todoc.data.dao.TaskDao;
import com.monokoumacorporation.todoc.data.entity.ProjectWithTasksEntity;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;

import java.time.Clock;
import java.util.List;

import javax.inject.Inject;

public class TaskRepository {

    private final TaskDao taskDao;
    private final Clock clock;

    @Inject
    public TaskRepository(TaskDao taskDao, Clock clock) {
        this.taskDao = taskDao;
        this.clock = clock;
    }

    @WorkerThread
    public void createTask(long projectId, String taskName) {
        TaskEntity task = new TaskEntity(
            projectId,
            taskName,
            clock.millis()
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
    public LiveData<List<ProjectWithTasksEntity>> getAllProjectWithTaskLiveData() {
        return taskDao.getAllProjectWithTasks();
    }
}
