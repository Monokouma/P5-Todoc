package com.monokoumacorporation.todoc.ui.list;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.monokoumacorporation.todoc.LiveDataTestUtils;
import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.entity.ProjectWithTasksEntity;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;
import com.monokoumacorporation.todoc.data.repository.ProjectRepository;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.ui.create.CreateTaskViewState;
import com.monokoumacorporation.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ListTaskViewModelTest {
    private static final int EXPECTED_CHARCOAL_COLOR = 0x353A47;
    private static final String EXPECTED_PROJECT_NAME = "EXPECTED_PROJECT_NAME";

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final Resources resources = Mockito.mock(Resources.class);
    private final ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final Executor ioExecutor = Mockito.spy(new TestExecutor());
    private final MutableLiveData<ListTaskViewModel.Ordering> orderingMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ProjectWithTasksEntity>> projectsWithTaskMutableLiveData = new MutableLiveData<>();
    private final Clock clock = Mockito.mock(Clock.class);

    private ListTaskViewModel listTaskViewModel;

    @Before
    public void setUp() {
        Mockito.doReturn(projectsWithTaskMutableLiveData).when(taskRepository).getAllProjectWithTaskLiveData();
        Mockito.doReturn(EXPECTED_PROJECT_NAME).when(resources).getString(R.string.projet_tartampion);
        orderingMutableLiveData.setValue(ListTaskViewModel.Ordering.DEFAULT);

        listTaskViewModel = new ListTaskViewModel(
            resources,
            projectRepository,
            taskRepository,
            ioExecutor
        );
    }

    @Test
    public void nominal_case() {
        ListTaskViewState result = LiveDataTestUtils.getValueForTesting(listTaskViewModel.getTaskListMutableLiveData());

        assertEquals(getDefaultListTaskViewState(), result);
    }

    @Test
    public void empty_task_list_should_make_appear_no_task_message() {
        ListTaskViewState result = LiveDataTestUtils.getValueForTesting(listTaskViewModel.getTaskListMutableLiveData());

        assertEquals(View.VISIBLE, result.getEmptyListMessageVisibility());
    }

    @Test
    public void not_empty_task_list_should_not_make_appear_no_task_message() {
        projectsWithTaskMutableLiveData.setValue(getDefaultTaskEntities());
        ListTaskViewState result = LiveDataTestUtils.getValueForTesting(listTaskViewModel.getTaskListMutableLiveData());

        assertEquals(View.GONE, result.getEmptyListMessageVisibility());
    }

    @Test
    public void delete_one_task() {
        // Given
        long taskId = 1;

        // When
        listTaskViewModel.onDeleteButtonClick(taskId);

        // Then
        Mockito.verify(taskRepository).deleteTask(taskId);
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

    @NonNull
    private List<ProjectWithTasksEntity> getDefaultTaskEntities() {
        List<ProjectWithTasksEntity> projectWithTasksEntities= new ArrayList<>();
        ProjectEntity projectEntity = new ProjectEntity(0,
                EXPECTED_PROJECT_NAME,
                EXPECTED_CHARCOAL_COLOR);
        List<TaskEntity> taskEntities = new ArrayList<>();
        taskEntities.add(
                new TaskEntity(
                        1,
                        0,
                        "name0",
                        clock.millis()
                )
        );

            projectWithTasksEntities.add(
                    projectEntity,
                    taskEntities
            );

        return projectWithTasksEntities;
    }
    // region OUT

    // endregion IN
    private ListTaskViewState getDefaultListTaskViewState() {
        return listTaskViewModel.getTaskListMutableLiveData().getValue();
    }
    // endregion OUT
}
