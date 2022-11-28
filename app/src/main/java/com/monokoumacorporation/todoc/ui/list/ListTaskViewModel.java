package com.monokoumacorporation.todoc.ui.list;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ListTaskViewModel extends ViewModel {

    private final MediatorLiveData<ListTaskViewState> listTaskViewStateMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> isAlphabeticalOrderEnableMutableLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isAlphabeticalInvertedOrderEnableMutableLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isOlderFirstEnableMutableLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isRecentFirstEnableMutableLiveData = new MutableLiveData<>(false);

    private final TaskRepository taskRepository;
    private final Resources resources;

    public ListTaskViewModel(TaskRepository taskRepository, Resources resources) {
        this.taskRepository = taskRepository;
        this.resources = resources;

        LiveData<List<TaskEntity>> taskListLiveData = taskRepository.getTaskListLiveData();
        LiveData<List<ProjectEntity>> projectListLiveData = taskRepository.getProjectistLiveData();

        listTaskViewStateMediatorLiveData.addSource(taskListLiveData, new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> tasks) {
                combine(tasks,
                    isAlphabeticalOrderEnableMutableLiveData.getValue(),
                    isAlphabeticalInvertedOrderEnableMutableLiveData.getValue(),
                    isOlderFirstEnableMutableLiveData.getValue(),
                    isRecentFirstEnableMutableLiveData.getValue(),
                    projectListLiveData.getValue()
                );
            }
        });

        listTaskViewStateMediatorLiveData.addSource(isAlphabeticalOrderEnableMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isAlphabeticalOrderEnable) {
                combine(taskListLiveData.getValue(),
                    isAlphabeticalOrderEnable,
                    isAlphabeticalInvertedOrderEnableMutableLiveData.getValue(),
                    isOlderFirstEnableMutableLiveData.getValue(),
                    isRecentFirstEnableMutableLiveData.getValue(),
                    projectListLiveData.getValue()
                );
            }
        });

        listTaskViewStateMediatorLiveData.addSource(isAlphabeticalInvertedOrderEnableMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isAlphabeticalInvertedOrderEnable) {
                combine(taskListLiveData.getValue(),
                    isAlphabeticalOrderEnableMutableLiveData.getValue(),
                    isAlphabeticalInvertedOrderEnable,
                    isOlderFirstEnableMutableLiveData.getValue(),
                    isRecentFirstEnableMutableLiveData.getValue(),
                    projectListLiveData.getValue()
                );
            }
        });

        listTaskViewStateMediatorLiveData.addSource(isOlderFirstEnableMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isOlderFirstEnable) {
                combine(taskListLiveData.getValue(),
                    isAlphabeticalOrderEnableMutableLiveData.getValue(),
                    isAlphabeticalInvertedOrderEnableMutableLiveData.getValue(),
                    isOlderFirstEnable,
                    isRecentFirstEnableMutableLiveData.getValue(),
                    projectListLiveData.getValue()
                );
            }
        });

        listTaskViewStateMediatorLiveData.addSource(isRecentFirstEnableMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRecentFirstEnable) {
                combine(taskListLiveData.getValue(),
                    isAlphabeticalOrderEnableMutableLiveData.getValue(),
                    isAlphabeticalInvertedOrderEnableMutableLiveData.getValue(),
                    isOlderFirstEnableMutableLiveData.getValue(),
                    isRecentFirstEnable,
                    projectListLiveData.getValue()
                );
            }
        });
        listTaskViewStateMediatorLiveData.addSource(projectListLiveData, new Observer<List<ProjectEntity>>() {
            @Override
            public void onChanged(List<ProjectEntity> projectEntities) {
                combine(taskListLiveData.getValue(),
                    isAlphabeticalOrderEnableMutableLiveData.getValue(),
                    isAlphabeticalInvertedOrderEnableMutableLiveData.getValue(),
                    isOlderFirstEnableMutableLiveData.getValue(),
                    isRecentFirstEnableMutableLiveData.getValue(),
                    projectEntities
                );
            }
        });
    }

    private void combine(@Nullable List<TaskEntity> tasks,
                         @Nullable Boolean isAlphabeticalOrderEnable,
                         @Nullable Boolean isAlphabeticalInvertedOrderEnable,
                         @Nullable Boolean isOlderFirstEnable,
                         @Nullable Boolean isRecentFirstEnable,
                         @Nullable List<ProjectEntity> projects) {

        if (tasks == null || projects == null) {
            return;
        }
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
                    task.getId(),
                    task.getCreationTimestamp()
                )
            );
        }



        listTaskViewStateMediatorLiveData.setValue(
            new ListTaskViewState(
                getSortedTaskViewStateItems(
                    taskViewStateItemsList,
                    isAlphabeticalOrderEnable,
                    isAlphabeticalInvertedOrderEnable,
                    isOlderFirstEnable,
                    isRecentFirstEnable
                )
            )
        );
    }

    @SuppressLint("NewApi")
    @NonNull
    private List<TaskViewStateItems> getSortedTaskViewStateItems(List<TaskViewStateItems> taskViewStateItemsList,
                                                                 Boolean isAlphabeticalOrderEnable,
                                                                 Boolean isAlphabeticalInvertedOrderEnable,
                                                                 Boolean isOlderFirstEnable,
                                                                 Boolean isRecentFirstEnable) {
        if (isAlphabeticalOrderEnable) {
            taskViewStateItemsList.sort(Comparator.comparing(TaskViewStateItems::getTaskName));
            return taskViewStateItemsList;
        } else if (isAlphabeticalInvertedOrderEnable) {
            taskViewStateItemsList.sort(Comparator.comparing(TaskViewStateItems::getTaskName).reversed());
            return taskViewStateItemsList;
        } else if (isOlderFirstEnable) {
            taskViewStateItemsList.sort(Comparator.comparing(TaskViewStateItems::getCreationTimeStamp));
            return taskViewStateItemsList;
        } else if (isRecentFirstEnable) {
            taskViewStateItemsList.sort(Comparator.comparing(TaskViewStateItems::getCreationTimeStamp).reversed());
            return taskViewStateItemsList;
        } else {
            return taskViewStateItemsList;
        }

    }




    public LiveData<ListTaskViewState> getTaskListMutableLiveData() {
        return listTaskViewStateMediatorLiveData;
    }

    public void onDeleteButtonClick(long taskId) {
        taskRepository.deleteTask(taskId);
    }

    public void onAlphabeticalFilterClick() {
        if (isAlphabeticalOrderEnableMutableLiveData.getValue()) {
            isAlphabeticalOrderEnableMutableLiveData.setValue(false);
        } else {
            isAlphabeticalOrderEnableMutableLiveData.setValue(true);

            isAlphabeticalInvertedOrderEnableMutableLiveData.setValue(false);
            isOlderFirstEnableMutableLiveData.setValue(false);
            isRecentFirstEnableMutableLiveData.setValue(false);
        }
    }

    public void onAlphabeticalInvertedFilterClick() {
        if (isAlphabeticalInvertedOrderEnableMutableLiveData.getValue()) {
            isAlphabeticalInvertedOrderEnableMutableLiveData.setValue(false);
        } else {
            isAlphabeticalInvertedOrderEnableMutableLiveData.setValue(true);

            isAlphabeticalOrderEnableMutableLiveData.setValue(false);
            isOlderFirstEnableMutableLiveData.setValue(false);
            isRecentFirstEnableMutableLiveData.setValue(false);
        }
    }

    public void onOlderFirstFilterClick() {
        if (isOlderFirstEnableMutableLiveData.getValue()) {
            isOlderFirstEnableMutableLiveData.setValue(false);
        } else {
            isOlderFirstEnableMutableLiveData.setValue(true);

            isAlphabeticalOrderEnableMutableLiveData.setValue(false);
            isAlphabeticalInvertedOrderEnableMutableLiveData.setValue(false);
            isRecentFirstEnableMutableLiveData.setValue(false);
        }
    }

    public void onRecentFirstFilterClick() {
        if (isRecentFirstEnableMutableLiveData.getValue()) {
            isRecentFirstEnableMutableLiveData.setValue(false);
        } else {
            isRecentFirstEnableMutableLiveData.setValue(true);

            isAlphabeticalOrderEnableMutableLiveData.setValue(false);
            isAlphabeticalInvertedOrderEnableMutableLiveData.setValue(false);
            isOlderFirstEnableMutableLiveData.setValue(false);
        }
    }

}
