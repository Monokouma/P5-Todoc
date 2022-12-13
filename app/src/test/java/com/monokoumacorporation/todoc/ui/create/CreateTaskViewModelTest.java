package com.monokoumacorporation.todoc.ui.create;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;

import com.monokoumacorporation.todoc.LiveDataTestUtils;
import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.dao.ProjectDao;
import com.monokoumacorporation.todoc.data.dao.TaskDao;
import com.monokoumacorporation.todoc.data.database.TodocDatabase;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.repository.ProjectRepository;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.utils.MainApplication;
import com.monokoumacorporation.todoc.utils.MainThreadExecutor;
import com.monokoumacorporation.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@RunWith(JUnit4.class)
public class CreateTaskViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private CreateTaskViewModel createTaskViewModel;
    private static final String EXPECTED_TASK_NAME_ERROR = "EXPECTED_TASK_NAME_ERROR";

    private final Application application = Mockito.mock(Application.class);
    private final ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final Executor ioExecutor = Mockito.spy(new TestExecutor());
    private final Executor mainExecutor = Mockito.spy(new TestExecutor());
    private final MutableLiveData<List<ProjectEntity>> projectsMutableLiveData = new MutableLiveData<>();

    @Before
    public void setUp() {

        Mockito.doReturn(EXPECTED_TASK_NAME_ERROR).when(application).getString(R.string.task_name_error);
        Mockito.doReturn(projectsMutableLiveData).when(projectRepository).getProjectEntityList();
        projectsMutableLiveData.setValue(getDefaultProjectEntities());

        createTaskViewModel = new CreateTaskViewModel(
            taskRepository,
            application,
            mainExecutor,
            ioExecutor,
            projectRepository
        );

    }

    @Test
    public void nominal_case() {
        //Given
        CreateTaskViewState result = LiveDataTestUtils.getValueForTesting(createTaskViewModel.getTaskViewStateMediatorLiveData());
    }

    @NonNull
    private List<ProjectEntity> getDefaultProjectEntities() {
        List<ProjectEntity> projectEntities = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            projectEntities.add(new ProjectEntity(i, "EXPECTED_PROJECT_NAME" + i, i));
        }

        return projectEntities;
    }
}
