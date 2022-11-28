package com.monokoumacorporation.todoc.ui.list;

import android.content.res.Resources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class ListTaskViewModel extends ViewModel {

    private final MediatorLiveData<ListTaskViewState> listTaskViewStateMediatorLiveData = new MediatorLiveData<>();

    private final TaskRepository taskRepository;

    public ListTaskViewModel(TaskRepository taskRepository, Resources resources) {
        this.taskRepository = taskRepository;

        LiveData<List<TaskEntity>> taskListLiveData = taskRepository.getTaskListLiveData();

        listTaskViewStateMediatorLiveData.addSource(taskListLiveData, new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> tasks) {
                String projectName;
                int projectColor;

                List<TaskViewStateItems> taskViewStateItemsList = new ArrayList<>();
                for (TaskEntity task : tasks) {
                    if (task.getProjectId() == 1) {
                        projectName = resources.getString(R.string.projet_tartampion);
                        projectColor = resources.getColor(R.color.dogwood_rose);
                    } else if (task.getProjectId() == 2) {
                        projectName = resources.getString(R.string.projet_lucidia);
                        projectColor = resources.getColor(R.color.green_munsell);
                    } else if (task.getProjectId() == 3) {
                        projectName = resources.getString(R.string.projet_circus);
                        projectColor = resources.getColor(R.color.marigold);
                    } else {
                        projectName = resources.getString(R.string.app_name);
                        projectColor = resources.getColor(R.color.charcoal);
                    }
                    taskViewStateItemsList.add(
                        new TaskViewStateItems(
                            task.getName(),
                            projectName,
                            projectColor,
                            task.getId()
                        )
                    );
                }

                listTaskViewStateMediatorLiveData.setValue(
                    new ListTaskViewState(
                        taskViewStateItemsList
                    )
                );
            }
        });


    }

    public LiveData<ListTaskViewState> getTaskListMutableLiveData() {
        return listTaskViewStateMediatorLiveData;
    }

    public void onDeleteButtonClick(long taskId) {
        taskRepository.deleteTask(taskId);
    }
}
