package com.monokoumacorporation.todoc.ui.create;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.utils.SingleLiveEvent;


public class CreateTaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;

    private final MutableLiveData<CreateTaskViewState> createTaskViewStateMutableLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<Void> onFinishActivityEvent = new SingleLiveEvent<>();

    private String taskName;

    private String projectName;

    private final static String TARTAMPION_CHECKBOX = "TARTAMPION";
    private final static String LUCIDIA_CHECKBOX = "LUCIDIA";
    private final static String CIRCUS_CHECKBOX = "CIRCUS";

    public CreateTaskViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        createTaskViewStateMutableLiveData.setValue(
            new CreateTaskViewState(
                null,
                View.GONE,
                false,
                false,
                false
            )
        );
    }

    public void onTaskTietTextChange(String taskName) {
        this.taskName = taskName;
    }

    public void onAddButtonClicked() {
        CreateTaskViewState previous = createTaskViewStateMutableLiveData.getValue();
        if (taskName == null || taskName.isEmpty()) {
            createTaskViewStateMutableLiveData.setValue(
                new CreateTaskViewState(
                    "Veuillez écrire une tache",
                    previous.getCheckboxErrorVisibility(),
                    previous.getTartampionCheckboxChecked(),
                    previous.getLucidiaCheckboxChecked(),
                    previous.getCircusCheckboxChecked()
                )
            );
        } else {
            createTaskViewStateMutableLiveData.setValue(
                new CreateTaskViewState(
                    null,
                    View.GONE,
                    previous.getTartampionCheckboxChecked(),
                    previous.getLucidiaCheckboxChecked(),
                    previous.getCircusCheckboxChecked()
                )
            );
            taskRepository.createTask(taskName, projectName);
            onFinishActivityEvent.call();
        }
        


        //Send to repo
    }

    public LiveData<CreateTaskViewState> getCreateTaskViewStateMutableLiveData() {
        return createTaskViewStateMutableLiveData;
    }

    public SingleLiveEvent<Void> getOnFinishActivityEvent() {
        return onFinishActivityEvent;
    }

    public void onCheckboxChecked(boolean isChecked, String from) {
        this.projectName = from;
    }

}
