package com.monokoumacorporation.todoc.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.monokoumacorporation.todoc.data.dao.ProjectDAO;
import com.monokoumacorporation.todoc.data.dao.TaskDAO;
import com.monokoumacorporation.todoc.data.database.TodocDatabase;
import com.monokoumacorporation.todoc.data.model.Task;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class TaskRepository {

    private int id;
    private TaskDAO taskDAO;
    private LiveData<List<Task>> taskListLiveData;


    public TaskRepository(Application application, ProjectDAO projectDAO, TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
        this.taskListLiveData = taskDAO.getTaskListLiveData();

    }

    public void createTask(int projectId, String taskName) {
        LocalDateTime now = LocalDateTime.now();

        Task task = new Task(
            id++,
            projectId,
            taskName,
            now.toEpochSecond(ZoneOffset.UTC)
        );

        AsyncTask.execute(() -> {
            taskDAO.insertTask(task);
        });

    }

    public LiveData<List<Task>> getTaskListLiveData() {
        return taskListLiveData;
    }
}
