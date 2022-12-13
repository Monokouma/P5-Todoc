package com.monokoumacorporation.todoc.ui.list;

import android.content.res.Resources;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.monokoumacorporation.todoc.data.repository.ProjectRepository;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.concurrent.Executor;

@RunWith(JUnit4.class)
public class ListTaskViewModelTest {
    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private Resources resources;
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private Executor ioExecutor;

    private ListTaskViewModel listTaskViewModel;

    @Before
    public void setUp() {
        resources = Mockito.mock(Resources.class);
        projectRepository = Mockito.mock(ProjectRepository.class);
        taskRepository =  Mockito.mock(TaskRepository.class);
        ioExecutor = Mockito.spy(new TestExecutor());

        listTaskViewModel = new ListTaskViewModel(
            resources,
            projectRepository,
            taskRepository,
            ioExecutor
        );
    }

    @Test
    public void nominal_case() {

    }
}
