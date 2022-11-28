package com.monokoumacorporation.todoc.data.repository;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.monokoumacorporation.todoc.data.dao.ProjectDAO;
import com.monokoumacorporation.todoc.data.dao.TaskDAO;
import com.monokoumacorporation.todoc.utils.MainApplication;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class TaskRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TaskRepository taskRepository;

    @Before
    public void setUp() {
        Application application = Mockito.mock(MainApplication.class);
        TaskDAO taskDAO = Mockito.mock(TaskDAO.class);
        ProjectDAO projectDAO = Mockito.mock(ProjectDAO.class);

        taskRepository = new TaskRepository(application, projectDAO, taskDAO);
    }

    @Ignore
    @Test
    public void insert_task() {
        //Given
        int projectId = 1;
        String taskName = "taskName";
        //When
        taskRepository.createTask(
            projectId,
            taskName
        );
        //Then

    }
}
