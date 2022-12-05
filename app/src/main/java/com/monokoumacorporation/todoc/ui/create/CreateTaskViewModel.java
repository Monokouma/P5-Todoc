package com.monokoumacorporation.todoc.ui.create;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.ui.create.button.Button;
import com.monokoumacorporation.todoc.ui.create.button.ProjectButtonViewStateItem;
import com.monokoumacorporation.todoc.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;


public class CreateTaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    private final Resources resources;
    private final Executor mainExecutor;
    private final Executor ioExecutor;

    private final MediatorLiveData<CreateTaskViewState> taskViewStateMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Map<Button, Boolean>> buttonMapMutableLiveData = new MutableLiveData<>(populateWithButton());

    private final SingleLiveEvent<Void> onFinishActivityEvent = new SingleLiveEvent<>();

    private String taskName;
    private Integer projectId;

    public CreateTaskViewModel(
        TaskRepository taskRepository,
        Resources resources,
        Executor mainExecutor,
        Executor ioExecutor
    ) {
        this.taskRepository = taskRepository;
        this.resources = resources;
        this.mainExecutor = mainExecutor;
        this.ioExecutor = ioExecutor;

        taskViewStateMediatorLiveData.addSource(buttonMapMutableLiveData, new Observer<Map<Button, Boolean>>() {
            @Override
            public void onChanged(Map<Button, Boolean> buttonBooleanMap) {

                List<ProjectButtonViewStateItem> projectButtonViewStateItemList = new ArrayList<>();

                for (Map.Entry<Button, Boolean> entry : buttonBooleanMap.entrySet()) {
                    projectButtonViewStateItemList.add(
                        new ProjectButtonViewStateItem(
                            entry.getKey().getId(),
                            entry.getKey().getBackgroundColor(),
                            entry.getKey().getProjectName()
                        )
                    );
                }

                taskViewStateMediatorLiveData.setValue(
                    new CreateTaskViewState(
                        null,
                        View.GONE,
                        projectButtonViewStateItemList
                    )
                );
            }
        });
    }

    public void onTaskTextChange(String taskName) {
        this.taskName = taskName;
        final CreateTaskViewState previous = taskViewStateMediatorLiveData.getValue();

        if (previous != null) {
            taskViewStateMediatorLiveData.setValue(
                new CreateTaskViewState(
                    null,
                    previous.getCheckboxErrorVisibility(),
                    getButtonList()
                )
            );
        }
    }

    public void onAddButtonClicked() {
        final CreateTaskViewState previous = taskViewStateMediatorLiveData.getValue();

        if (previous != null) {
            if (taskName == null || taskName.isEmpty()) {
                taskViewStateMediatorLiveData.setValue(
                    new CreateTaskViewState(
                        resources.getString(R.string.task_name_error),
                        previous.getCheckboxErrorVisibility(),
                        getButtonList()
                    )
                );
            } else if (projectId == null) {
                taskViewStateMediatorLiveData.setValue(
                    new CreateTaskViewState(
                        previous.getTaskInputErrorMessage(),
                        View.VISIBLE,
                        getButtonList()
                    )
                );
            } else {
                taskViewStateMediatorLiveData.setValue(
                    new CreateTaskViewState(
                        null,
                        View.GONE,
                        getButtonList()
                    )
                );
                ioExecutor.execute(() -> {
                    taskRepository.createTask(projectId, taskName);
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

    public void onProjectButtonClicked(int projectId) {
        this.projectId = projectId;

    }

    private List<ProjectButtonViewStateItem> getButtonList() {
        List<ProjectButtonViewStateItem> projectButtonViewStateItemList = new ArrayList<>();

        for (Button button : Button.values()) {
            projectButtonViewStateItemList.add(
                new ProjectButtonViewStateItem(
                    button.getId(),
                    button.getBackgroundColor(),
                    button.getProjectName()
                )
            );
        }

        return projectButtonViewStateItemList;
    }

    @NonNull
    private Map<Button, Boolean> populateWithButton() {
        Map<Button, Boolean> buttons = new LinkedHashMap<>();
        for (Button button : Button.values()) {
            buttons.put(button, false);
        }
        return buttons;
    }
}
