package com.monokoumacorporation.todoc.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monokoumacorporation.todoc.data.database.TodocDatabase;
import com.monokoumacorporation.todoc.data.repository.ProjectRepository;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.ui.create.CreateTaskViewModel;
import com.monokoumacorporation.todoc.ui.list.ListTaskViewModel;

import java.time.Clock;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private static volatile ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    private final TodocDatabase todocDatabase;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final Executor mainExecutor = new MainThreadExecutor();
    private final Executor ioExecutor = Executors.newFixedThreadPool(4);

    private ViewModelFactory() {
        todocDatabase = TodocDatabase.getDatabase(MainApplication.getInstance().getApplicationContext(), ioExecutor);
        taskRepository = new TaskRepository(todocDatabase.getTaskDao(), Clock.systemDefaultZone());
        projectRepository = new ProjectRepository(todocDatabase.getProjectDao());
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListTaskViewModel.class)) {
            return (T) new ListTaskViewModel(
                taskRepository,
                MainApplication.getInstance().getResources(),
                ioExecutor,
                projectRepository
            );
        } else if (modelClass.isAssignableFrom(CreateTaskViewModel.class)) {
            return (T) new CreateTaskViewModel(
                taskRepository,
                MainApplication.getInstance(),
                mainExecutor,
                ioExecutor,
                projectRepository
            );
        }
        throw new IllegalArgumentException("Unknow ViewModel");
    }

}
