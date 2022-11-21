package com.monokoumacorporation.todoc.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monokoumacorporation.todoc.data.model.Task;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.ui.list.ListTaskViewModel;


public class ViewModelFactory implements ViewModelProvider.Factory {
    private static ViewModelFactory factory;
    private final TaskRepository taskRepository;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                        new TaskRepository()
                    );
                }
            }
        }
        return factory;
    }



    private ViewModelFactory(@NonNull TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListTaskViewModel.class)) {
            return (T) new ListTaskViewModel(
                taskRepository
            );
        }
        //else if (modelClass.isAssignableFrom(MeetingDetailsViewModel.class)) {
        //   return (T) new MeetingDetailsViewModel(
        // );
        // }
        throw new IllegalArgumentException("Unknow ViewModel");
    }

}
