package com.monokoumacorporation.todoc.ui.list;

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.monokoumacorporation.todoc.data.di.IoExecutor;
import com.monokoumacorporation.todoc.data.entity.ProjectWithTasksEntity;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;
import com.monokoumacorporation.todoc.data.repository.ProjectRepository;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
@Module
@InstallIn(ViewModelComponent.class)
public class ListTaskViewModel extends ViewModel {

    private final MediatorLiveData<ListTaskViewState> listTaskViewStateMediatorLiveData = new MediatorLiveData<>();

    private final MutableLiveData<Ordering> orderingMutableLiveData = new MutableLiveData<>(Ordering.DEFAULT);

    private final Resources resources;
    private final TaskRepository taskRepository;
    private final Executor ioExecutor;

    @Inject
    public ListTaskViewModel(
        @NonNull Resources resources,
        @NonNull ProjectRepository projectRepository,
        @NonNull TaskRepository taskRepository,
        @NonNull @IoExecutor Executor ioExecutor
    ) {
        this.resources = resources;
        this.taskRepository = taskRepository;
        this.ioExecutor = ioExecutor;

        LiveData<List<ProjectWithTasksEntity>> projectWithTaskLiveData = taskRepository.getAllProjectWithTaskLiveData();

        listTaskViewStateMediatorLiveData.addSource(
            projectWithTaskLiveData,
            projectWithTasksEntities -> combine(
                    projectWithTasksEntities,
                    orderingMutableLiveData.getValue()
            )
        );

        listTaskViewStateMediatorLiveData.addSource(
            orderingMutableLiveData,
            ordering -> combine(
                    projectWithTaskLiveData.getValue(),
                    ordering
            )
        );

    }

    private void combine(
            @NonNull List<ProjectWithTasksEntity> projectsWithTaskEntity,
            @Nullable Ordering ordering
    ) {

        if (projectsWithTaskEntity == null || ordering == null) {
            return;
        }

        List<TaskViewStateItems> taskViewStateItemsList = new ArrayList<>();

        for (ProjectWithTasksEntity projectWithTasksEntities : projectsWithTaskEntity) {
            for (TaskEntity taskEntities : projectWithTasksEntities.getTaskEntities()) {
                if (taskEntities.getProjectId() == projectWithTasksEntities.getProject().getId()) {
                    taskViewStateItemsList.add(
                            new TaskViewStateItems(
                                 taskEntities.getName(),
                                 projectWithTasksEntities.getProject().getName(),
                                 projectWithTasksEntities.getProject().getColorInt(),
                                 taskEntities.getId(),
                                 taskEntities.getCreationTimestamp()
                            )
                    );
                }
            }
        }

        if (taskViewStateItemsList.isEmpty()) {
            listTaskViewStateMediatorLiveData.setValue(
                new ListTaskViewState(
                    new ArrayList<>(),
                    View.VISIBLE
                )
            );
        } else {
            listTaskViewStateMediatorLiveData.setValue(
                new ListTaskViewState(
                    getOrderedTaskViewStateItems(
                        taskViewStateItemsList,
                        ordering
                    ),
                    View.GONE
                )
            );
        }
    }

    @NonNull
    private List<TaskViewStateItems> getOrderedTaskViewStateItems(
        List<TaskViewStateItems> taskViewStateItemsList,
        Ordering ordering
    ) {
        switch (ordering) {
            case ALPHABETICAL:
                Collections.sort(taskViewStateItemsList, Comparator.comparing(TaskViewStateItems::getTaskName));
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(taskViewStateItemsList, Comparator.comparing(TaskViewStateItems::getTaskName).reversed());
                break;
            case OLDER_FIRST:
                Collections.sort(taskViewStateItemsList, Comparator.comparing(TaskViewStateItems::getCreationTimeStamp));
                break;
            case RECENT_FIRST:
                Collections.sort(taskViewStateItemsList, Comparator.comparing(TaskViewStateItems::getCreationTimeStamp).reversed());
                break;
            case DEFAULT:
            default:
                break;
        }

        return taskViewStateItemsList;
    }


    public LiveData<ListTaskViewState> getTaskListMutableLiveData() {
        return listTaskViewStateMediatorLiveData;
    }

    public void onDeleteButtonClick(long taskId) {
        ioExecutor.execute(() -> taskRepository.deleteTask(taskId));
    }

    public void onAlphabeticalFilterClick() {
        orderingMutableLiveData.setValue(Ordering.ALPHABETICAL);
    }

    public void onAlphabeticalInvertedFilterClick() {
        orderingMutableLiveData.setValue(Ordering.ALPHABETICAL_INVERTED);
    }

    public void onOlderFirstFilterClick() {
        orderingMutableLiveData.setValue(Ordering.OLDER_FIRST);
    }

    public void onRecentFirstFilterClick() {
        orderingMutableLiveData.setValue(Ordering.RECENT_FIRST);
    }

    enum Ordering {
        DEFAULT,
        ALPHABETICAL,
        ALPHABETICAL_INVERTED,
        OLDER_FIRST,
        RECENT_FIRST,
    }
}
