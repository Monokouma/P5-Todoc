package com.monokoumacorporation.todoc.data.repository;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.monokoumacorporation.todoc.data.dao.ProjectDao;
import com.monokoumacorporation.todoc.data.dao.TaskDao;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;
    private final Clock clock;

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


}
