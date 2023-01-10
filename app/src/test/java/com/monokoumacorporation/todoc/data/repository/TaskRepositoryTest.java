package com.monokoumacorporation.todoc.data.repository;

import static org.junit.Assert.assertEquals;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monokoumacorporation.todoc.data.dao.ProjectDao;
import com.monokoumacorporation.todoc.data.dao.TaskDao;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;
import com.monokoumacorporation.todoc.utils.MainApplication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.time.Clock;
import java.util.List;

@RunWith(JUnit4.class)
public class TaskRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TaskRepository taskRepository;

    private final Application application = Mockito.mock(MainApplication.class);
    private final TaskDao taskDao = Mockito.mock(TaskDao.class);
    private final ProjectDao projectDao = Mockito.mock(ProjectDao.class);
    private final Clock clock = Mockito.mock(Clock.class);

    @Before
    public void setUp() {
        taskRepository = new TaskRepository(taskDao, clock);
    }

    @Test
    public void insert_task() {
        //Given
        int projectId = 1;
        String taskName = "taskName";
        LiveData<List<TaskEntity>> taskListLiveData = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(taskListLiveData).when(taskDao).getTaskListLiveData();

        //When
        taskRepository.createTask(
            projectId,
            taskName
        );
        LiveData<List<TaskEntity>> result = taskRepository.getTaskListLiveData();
        //Then
        assertEquals(taskListLiveData, result);
    }

    @Test
    public void verify_delete() {
        // Given
        long taskId = 666;

        // When
        taskRepository.deleteTask(taskId);

        // Then
        Mockito.verify(taskDao).delete(taskId);
        Mockito.verifyNoMoreInteractions(taskDao, projectDao);
    }
}
