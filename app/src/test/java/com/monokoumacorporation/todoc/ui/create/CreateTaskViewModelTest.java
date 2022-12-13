package com.monokoumacorporation.todoc.ui.create;

import static org.junit.Assert.assertEquals;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.monokoumacorporation.todoc.LiveDataTestUtils;
import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.repository.ProjectRepository;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class CreateTaskViewModelTest {

    private static final String EXPECTED_TASK_NAME_ERROR = "EXPECTED_TASK_NAME_ERROR";
    private static final int EXPECTED_CHARCOAL_COLOR = 0x353A47;


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final Resources resources = Mockito.mock(Resources.class);
    private final ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final Executor ioExecutor = Mockito.spy(new TestExecutor());
    private final Executor mainExecutor = Mockito.spy(new TestExecutor());
    private final MutableLiveData<List<ProjectEntity>> projectsMutableLiveData = new MutableLiveData<>();

    private CreateTaskViewModel createTaskViewModel;

    @Before
    public void setUp() {
        Mockito.doReturn(EXPECTED_TASK_NAME_ERROR).when(resources).getString(R.string.task_name_error);
        Mockito.doReturn(EXPECTED_CHARCOAL_COLOR).when(resources).getColor(R.color.charcoal);
        Mockito.doReturn(projectsMutableLiveData).when(projectRepository).getProjectEntityList();
        projectsMutableLiveData.setValue(getDefaultProjectEntities());

        createTaskViewModel = new CreateTaskViewModel(
            taskRepository,
            resources,
            mainExecutor,
            ioExecutor,
            projectRepository
        );

    }

    @Test
    public void nominal_case() {
        // When
        CreateTaskViewState result = LiveDataTestUtils.getValueForTesting(createTaskViewModel.getTaskViewStateMediatorLiveData());

        // Then
        assertEquals(getDefaultCreateTaskViewState(), result);
    }

    // region IN
    @NonNull
    private List<ProjectEntity> getDefaultProjectEntities() {
        List<ProjectEntity> projectEntities = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            projectEntities.add(new ProjectEntity(i, "EXPECTED_PROJECT_NAME" + i, EXPECTED_CHARCOAL_COLOR));
        }

        return projectEntities;
    }
    // endregion IN


    // region OUT
    private CreateTaskViewState getDefaultCreateTaskViewState() {
        return Mockito.mock(CreateTaskViewState.class); // TODO MONO
    }
    // endregion OUT
}
