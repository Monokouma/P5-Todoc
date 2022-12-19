package com.monokoumacorporation.todoc.ui.list;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.monokoumacorporation.todoc.LiveDataTestUtils;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
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

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final Resources resources = Mockito.mock(Resources.class);
    private final ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final Executor ioExecutor = Mockito.spy(new TestExecutor());
    private final MutableLiveData<ListTaskViewModel.Ordering> orderingMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ProjectEntity>> projectsMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<TaskEntity>> taskMutableLiveData = new MutableLiveData<>();
    private final Clock clock = Mockito.mock(Clock.class);

    private ListTaskViewModel listTaskViewModel;

    @Before
    public void setUp() {
        Mockito.doReturn(projectsMutableLiveData).when(projectRepository).getProjectEntityList();
        Mockito.doReturn(taskMutableLiveData).when(taskRepository).getTaskListLiveData();
        orderingMutableLiveData.setValue(ListTaskViewModel.Ordering.DEFAULT);
        projectsMutableLiveData.setValue(getDefaultProjectEntities());
        taskMutableLiveData.setValue(new ArrayList<>());

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
        taskMutableLiveData.setValue(getDefaultTaskEntities());
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
    private List<TaskEntity> getDefaultTaskEntities() {
        List<TaskEntity> taskEntities= new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            taskEntities.add(
                new TaskEntity(
                    i,
                    i,
                    "name" + i,
                    clock.millis()
                )
            );
        }
        return taskEntities;
    }
    // region OUT

    // endregion IN
    private ListTaskViewState getDefaultListTaskViewState() {
        return listTaskViewModel.getTaskListMutableLiveData().getValue();
    }
    // endregion OUT
}
