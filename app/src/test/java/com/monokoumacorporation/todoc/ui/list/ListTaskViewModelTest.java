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

    private TaskRepository taskRepository;
    private Resources resources;
    private Executor ioExecutor;
    private ProjectRepository projectRepository;

    private ListTaskViewModel listTaskViewModel;

    @Before
    public void setUp() {
        taskRepository =  Mockito.mock(TaskRepository.class);
        resources = Mockito.mock(Resources.class);
        ioExecutor = Mockito.spy(new TestExecutor());
        projectRepository = Mockito.mock(ProjectRepository.class);

        listTaskViewModel = new ListTaskViewModel(
            taskRepository,
            resources,
            ioExecutor,
            projectRepository
        );
    }

    @Test
    public void nominal_case() {

    }
}
