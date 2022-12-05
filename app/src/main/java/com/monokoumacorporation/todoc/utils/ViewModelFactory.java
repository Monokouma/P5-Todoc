package com.monokoumacorporation.todoc.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monokoumacorporation.todoc.data.database.TodocDatabase;
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
    private final Executor mainExecutor = new MainThreadExecutor();
    private final Executor ioExecutor = Executors.newFixedThreadPool(4);

    private ViewModelFactory() {
        todocDatabase = TodocDatabase.getDatabase(MainApplication.getInstance().getApplicationContext(), ioExecutor);
        taskRepository = new TaskRepository(todocDatabase.getProjectDao(), todocDatabase.getTaskDao(), Clock.systemDefaultZone());
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListTaskViewModel.class)) {
            return (T) new ListTaskViewModel(
                taskRepository,
                MainApplication.getInstance().getResources(),
                ioExecutor
            );
        } else if (modelClass.isAssignableFrom(CreateTaskViewModel.class)) {
            return (T) new CreateTaskViewModel(
                todocDatabase.getProjectDao(),
                taskRepository,
                MainApplication.getInstance(),
                mainExecutor,
                ioExecutor
            );
        }
        throw new IllegalArgumentException("Unknow ViewModel");
    }

}
