package com.monokoumacorporation.todoc.ui.create;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.monokoumacorporation.todoc.LiveDataTestUtils;
import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.repository.ProjectRepository;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.utils.MainApplication;
import com.monokoumacorporation.todoc.utils.MainThreadExecutor;
import com.monokoumacorporation.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class CreateTaskViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TaskRepository taskRepository;
    private Application application;
    private Executor executor;
    private Executor mainExecutor;
    private CreateTaskViewModel createTaskViewModel;
    private ProjectRepository projectRepository;
    private static final String EXPECTED_TASK_NAME_ERROR = "EXPECTED_TASK_NAME_ERROR";

    @Before
    public void setUp() {
        taskRepository = Mockito.mock(TaskRepository.class);
        application = Mockito.mock(MainApplication.class);
        executor = Mockito.spy(new TestExecutor());
        projectRepository = Mockito.mock(ProjectRepository.class);

        mainExecutor = Mockito.spy(new TestExecutor());

        Mockito.doReturn(EXPECTED_TASK_NAME_ERROR).when(application).getString(R.string.task_name_error);

        createTaskViewModel = new CreateTaskViewModel(
            taskRepository,
            application,
            mainExecutor,
            executor,
            projectRepository
        );
    }



}
