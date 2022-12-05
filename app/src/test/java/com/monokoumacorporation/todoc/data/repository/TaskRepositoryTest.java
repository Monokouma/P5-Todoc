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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.util.List;

public class TaskRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TaskRepository taskRepository;

    private final Application application = Mockito.mock(MainApplication.class);
    private final TaskDao taskDAO = Mockito.mock(TaskDao.class);
    private final ProjectDao projectDAO = Mockito.mock(ProjectDao.class);
    private final Clock clock = Mockito.mock(Clock.class);

    @Before
    public void setUp() {


        taskRepository = new TaskRepository(projectDAO, taskDAO, clock);
    }

    @Ignore
    @Test
    public void insert_task() {
        //Given
        int projectId = 1;
        String taskName = "taskName";
        LiveData<List<TaskEntity>> taskListLiveData = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(taskListLiveData).when(taskDAO).getTaskListLiveData();

        //When
        taskRepository.createTask(
            projectId,
            taskName
        );
        LiveData<List<TaskEntity>> result = taskRepository.getTaskListLiveData();
        //Then
        assertEquals(taskListLiveData, result);
    }
}
