package com.monokoumacorporation.todoc.ui.create;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.dao.ProjectDao;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.repository.ProjectRepository;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.ui.create.button.ProjectButtonViewStateItem;
import com.monokoumacorporation.todoc.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


public class CreateTaskViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;
    @NonNull
    private final Application context;
    @NonNull
    private final Executor mainExecutor;
    @NonNull
    private final Executor ioExecutor;

    private final MediatorLiveData<CreateTaskViewState> taskViewStateMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Long> selectedProjectIdMutableLiveData = new MutableLiveData<>(null);

    private final SingleLiveEvent<Void> onFinishActivityEvent = new SingleLiveEvent<>();

    private String taskName;

    public CreateTaskViewModel(
        @NonNull TaskRepository taskRepository,
        @NonNull Application context,
        @NonNull Executor mainExecutor,
        @NonNull Executor ioExecutor,
        ProjectRepository projectRepository) {

        this.taskRepository = taskRepository;
        this.context = context;
        this.mainExecutor = mainExecutor;
        this.ioExecutor = ioExecutor;

        LiveData<List<ProjectEntity>> projectsLiveData = projectRepository.getProjectEntityList();

        taskViewStateMediatorLiveData.addSource(selectedProjectIdMutableLiveData, new Observer<Long>() {
            @Override
            public void onChanged(Long selectedProjectId) {
                combine(selectedProjectId, projectsLiveData.getValue());
            }
        });

        taskViewStateMediatorLiveData.addSource(projectsLiveData, new Observer<List<ProjectEntity>>() {
            @Override
            public void onChanged(List<ProjectEntity> projectEntities) {
                combine(selectedProjectIdMutableLiveData.getValue(), projectEntities);
            }
        });
    }

    private void combine(@Nullable Long selectedProjectId, @Nullable List<ProjectEntity> projects) {
        List<ProjectButtonViewStateItem> projectButtonViewStateItemList = new ArrayList<>();

        if (projects != null) {
            for (ProjectEntity project : projects) {
                final ProjectButtonViewStateItem item;
                if (selectedProjectId != null && project.getId() == selectedProjectId) {
                    item = new ProjectButtonViewStateItem(
                        project.getId(),
                        project.getColor(),
                        project.getName()
                    );
                } else {
                    item = new ProjectButtonViewStateItem(
                        project.getId(),
                        ContextCompat.getColor(context, R.color.charcoal),
                        project.getName()
                    );
                }
                projectButtonViewStateItemList.add(item);
            }
        }

        taskViewStateMediatorLiveData.setValue(
            new CreateTaskViewState(
                null,
                View.GONE,
                projectButtonViewStateItemList
            )
        );
    }

    public void onTaskTextChange(String taskName) {
        this.taskName = taskName;
        final CreateTaskViewState previous = taskViewStateMediatorLiveData.getValue();

        if (previous != null) {
            taskViewStateMediatorLiveData.setValue(
                new CreateTaskViewState(
                    null,
                    previous.getCheckboxErrorVisibility(),
                    previous.getProjectButtonViewStateItemList()
                )
            );
        }
    }

    public void onProjectButtonClicked(long projectId) {
        selectedProjectIdMutableLiveData.setValue(projectId);
    }

    public void onAddButtonClicked() {
        final CreateTaskViewState previous = taskViewStateMediatorLiveData.getValue();

        Long projectId = selectedProjectIdMutableLiveData.getValue();

        if (previous != null) {
            if (taskName == null || taskName.isEmpty()) {
                taskViewStateMediatorLiveData.setValue(
                    new CreateTaskViewState(
                        context.getString(R.string.task_name_error),
                        previous.getCheckboxErrorVisibility(),
                        previous.getProjectButtonViewStateItemList()
                    )
                );
            } else if (projectId == null) {
                taskViewStateMediatorLiveData.setValue(
                    new CreateTaskViewState(
                        previous.getTaskInputErrorMessage(),
                        View.VISIBLE,
                        previous.getProjectButtonViewStateItemList()
                    )
                );
            } else {
                taskViewStateMediatorLiveData.setValue(
                    new CreateTaskViewState(
                        null,
                        View.GONE,
                        previous.getProjectButtonViewStateItemList()
                    )
                );
                ioExecutor.execute(() -> {
                    taskRepository.createTask(projectId, taskName);
                    //noinspection Convert2MethodRef
                    mainExecutor.execute(() -> onFinishActivityEvent.call());
                });
            }
        }
    }

    public LiveData<CreateTaskViewState> getTaskViewStateMediatorLiveData() {
        return taskViewStateMediatorLiveData;
    }

    public SingleLiveEvent<Void> getOnFinishActivityEvent() {
        return onFinishActivityEvent;
    }
}
