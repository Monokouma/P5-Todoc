package com.monokoumacorporation.todoc.data.repository;

import android.util.Log;

import com.monokoumacorporation.todoc.data.model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskRepository {

    private long id;

    public TaskRepository() {
    }

    public void createTask(String projectName, String taskName) {
        LocalDateTime now = LocalDateTime.now();

        Task task = new Task(
            id++,
            taskName,
            projectName,
            now.toEpochSecond(ZoneOffset.UTC)
        );

        Log.i("Monokouma", task.toString());
    }
}
