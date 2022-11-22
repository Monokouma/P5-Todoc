package com.monokoumacorporation.todoc.ui.list;

import android.content.res.Resources;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.model.Task;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class ListTaskViewModel extends ViewModel {

    private final MediatorLiveData<ListTaskViewState> listTaskViewStateMediatorLiveData = new MediatorLiveData<>();


    public ListTaskViewModel(TaskRepository taskRepository, Resources resources) {

        LiveData<List<Task>> taskListLiveData = taskRepository.getTaskListLiveData();

        listTaskViewStateMediatorLiveData.addSource(taskListLiveData, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                List<TaskViewStateItems> taskViewStateItemsList = new ArrayList<>();
                for (Task task : tasks) {
                    taskViewStateItemsList.add(
                        new TaskViewStateItems(
                            task.getName(),
                            String.valueOf(task.getProjectId()),
                            resources.getColor(R.color.dogwood_rose)
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
}
