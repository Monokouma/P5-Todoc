package com.monokoumacorporation.todoc.ui.create;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Insert;

import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.utils.SingleLiveEvent;


public class CreateTaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;

    private final MutableLiveData<CreateTaskViewState> createTaskViewStateMutableLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<Void> onFinishActivityEvent = new SingleLiveEvent<>();

    private String taskName;
    private Integer projectId;

    private final Resources resources;

    public CreateTaskViewModel(TaskRepository taskRepository, Resources resources) {
        this.taskRepository = taskRepository;
        this.resources = resources;

        createTaskViewStateMutableLiveData.setValue(
            new CreateTaskViewState(
                null,
                View.GONE,
                resources.getColor(R.color.charcoal),
                resources.getColor(R.color.charcoal),
                resources.getColor(R.color.charcoal),
                resources.getColor(R.color.white),
                resources.getColor(R.color.white),
                resources.getColor(R.color.white)
            )
        );
    }

    public void onTaskTietTextChange(String taskName) {
        this.taskName = taskName;
        final CreateTaskViewState previous = createTaskViewStateMutableLiveData.getValue();
        createTaskViewStateMutableLiveData.setValue(
            new CreateTaskViewState(
                null,
                previous.getCheckboxErrorVisibility(),
                previous.getTartampionButtonBackgroundColor(),
                previous.getLucidiaButtonBackgroundColor(),
                previous.getCircusButtonBackgroundColor(),
                previous.getTartampionTextButtonColor(),
                previous.getLucidiaTextButtonColor(),
                previous.getCircusTextButtonColor()
            )
        );

    }

    public void onAddButtonClicked() {
        final CreateTaskViewState previous = createTaskViewStateMutableLiveData.getValue();

        if (taskName == null || taskName.isEmpty()) {
            createTaskViewStateMutableLiveData.setValue(
                new CreateTaskViewState(
                    resources.getString(R.string.task_name_error),
                    previous.getCheckboxErrorVisibility(),
                    previous.getTartampionButtonBackgroundColor(),
                    previous.getLucidiaButtonBackgroundColor(),
                    previous.getCircusButtonBackgroundColor(),
                    previous.getTartampionTextButtonColor(),
                    previous.getLucidiaTextButtonColor(),
                    previous.getCircusTextButtonColor()
                )
            );
        } else if (projectId == null) {
            createTaskViewStateMutableLiveData.setValue(
                new CreateTaskViewState(
                    previous.getTaskInputErrorMessage(),
                    View.VISIBLE,
                    previous.getTartampionButtonBackgroundColor(),
                    previous.getLucidiaButtonBackgroundColor(),
                    previous.getCircusButtonBackgroundColor(),
                    previous.getTartampionTextButtonColor(),
                    previous.getLucidiaTextButtonColor(),
                    previous.getCircusTextButtonColor()
                )
            );
        } else {
            createTaskViewStateMutableLiveData.setValue(
                new CreateTaskViewState(
                    null,
                    View.GONE,
                    previous.getTartampionButtonBackgroundColor(),
                    previous.getLucidiaButtonBackgroundColor(),
                    previous.getCircusButtonBackgroundColor(),
                    previous.getTartampionTextButtonColor(),
                    previous.getLucidiaTextButtonColor(),
                    previous.getCircusTextButtonColor()
                )
            );
            taskRepository.createTask(projectId, taskName);
            onFinishActivityEvent.call();
        }

    }

    public LiveData<CreateTaskViewState> getCreateTaskViewStateMutableLiveData() {
        return createTaskViewStateMutableLiveData;
    }

    public SingleLiveEvent<Void> getOnFinishActivityEvent() {
        return onFinishActivityEvent;
    }


    public void onTartampionButtonClicked(int tartampionId) {
        projectId = tartampionId;
        final CreateTaskViewState previous = createTaskViewStateMutableLiveData.getValue();
        createTaskViewStateMutableLiveData.setValue(
            new CreateTaskViewState(
                previous.getTaskInputErrorMessage(),
                View.GONE,
                resources.getColor(R.color.dogwood_rose),
                resources.getColor(R.color.charcoal),
                resources.getColor(R.color.charcoal),
                resources.getColor(R.color.white),
                resources.getColor(R.color.white),
                resources.getColor(R.color.white)
            )
        );

    }

    public void onLucidiaButtonClicked(int lucidiaId) {
        projectId = lucidiaId;
        final CreateTaskViewState previous = createTaskViewStateMutableLiveData.getValue();
        createTaskViewStateMutableLiveData.setValue(
            new CreateTaskViewState(
                previous.getTaskInputErrorMessage(),
                View.GONE,

                resources.getColor(R.color.charcoal),
                resources.getColor(R.color.green_munsell),
                resources.getColor(R.color.charcoal),

                resources.getColor(R.color.white),
                resources.getColor(R.color.white),
                resources.getColor(R.color.white)
            )
        );
    }

    public void onCircusButtonClicked(int circusId) {
        projectId = circusId;
        final CreateTaskViewState previous = createTaskViewStateMutableLiveData.getValue();
        createTaskViewStateMutableLiveData.setValue(
            new CreateTaskViewState(
                previous.getTaskInputErrorMessage(),
                View.GONE,

                resources.getColor(R.color.charcoal),
                resources.getColor(R.color.charcoal),
                resources.getColor(R.color.marigold),

                resources.getColor(R.color.white),
                resources.getColor(R.color.white),
                resources.getColor(R.color.white)
            )
        );
    }
}
