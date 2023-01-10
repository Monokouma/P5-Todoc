package com.monokoumacorporation.todoc.data.repository;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monokoumacorporation.todoc.data.dao.ProjectDao;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.List;

@RunWith(JUnit4.class)
public class ProjectRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ProjectRepository projectRepository;

    private final ProjectDao projectDao = Mockito.mock(ProjectDao.class);

    @Before
    public void setUp() {
        projectRepository = new ProjectRepository(projectDao);
    }

    @Test
    public void verify_get_all_project() {
        //Given
        LiveData<List<ProjectEntity>> projectEntitiesLiveData = Mockito.spy(new MutableLiveData<>());
        Mockito.doReturn(projectEntitiesLiveData).when(projectDao).getAll();

        // When
        LiveData<List<ProjectEntity>> result = projectRepository.getProjectEntityList();

        // Then
        assertEquals(projectEntitiesLiveData, result);
        Mockito.verify(projectDao).getAll();
        Mockito.verifyNoMoreInteractions(projectDao, projectDao);
    }
}
