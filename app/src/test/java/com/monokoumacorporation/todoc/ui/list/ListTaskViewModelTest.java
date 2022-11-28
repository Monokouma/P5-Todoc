package com.monokoumacorporation.todoc.ui.list;

import android.content.res.Resources;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.monokoumacorporation.todoc.data.repository.TaskRepository;

import org.junit.Rule;
import org.mockito.Mockito;

public class ListTaskViewModelTest {
    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final Resources resources = Mockito.mock(Resources.class);


}
