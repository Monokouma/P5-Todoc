package com.monokoumacorporation.todoc.data.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.monokoumacorporation.todoc.data.database.TodocDatabase;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {
    private static final String EXPECTED_PROJECT_NAME_1 = "EXPECTED_PROJECT_NAME_1";
    private static final int EXPECTED_PROJECT_COLOR_1 = 1;
    private static final String EXPECTED_PROJECT_NAME_2 = "EXPECTED_PROJECT_NAME_2";
    private static final int EXPECTED_PROJECT_COLOR_2 = 2;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TodocDatabase todocDatabase;
    private ProjectDao projectDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        todocDatabase = Room
                .inMemoryDatabaseBuilder(context, TodocDatabase.class)
                .build();
        projectDao = todocDatabase.getProjectDao();
    }

    @After
    public void closeDb() {
        todocDatabase.close();
    }

    @Test
    public void insert_one() {
        // Given
        ProjectEntity projectEntity = new ProjectEntity(EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1);

        // When
        projectDao.insert(projectEntity);
        List<ProjectEntity> results = LiveDataTestUtils.getValueForTesting(projectDao.getAll());

        // Then
        assertEquals(
                Collections.singletonList(
                        new ProjectEntity(1, EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1)
                ),
                results
        );
    }

    @Test
    public void insert_two() {
        // Given
        ProjectEntity projectEntity = new ProjectEntity(EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1);
        ProjectEntity projectEntity2 = new ProjectEntity(EXPECTED_PROJECT_NAME_2, EXPECTED_PROJECT_COLOR_2);

        // When
        projectDao.insert(projectEntity);
        projectDao.insert(projectEntity2);
        List<ProjectEntity> results = LiveDataTestUtils.getValueForTesting(projectDao.getAll());

        // Then
        assertEquals(
                Arrays.asList(
                        new ProjectEntity(1, EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1),
                        new ProjectEntity(2, EXPECTED_PROJECT_NAME_2, EXPECTED_PROJECT_COLOR_2)
                ),
                results
        );
    }

    @Test
    public void insert_two_then_getAllSync() {
        // Given
        ProjectEntity projectEntity = new ProjectEntity(EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1);
        ProjectEntity projectEntity2 = new ProjectEntity(EXPECTED_PROJECT_NAME_2, EXPECTED_PROJECT_COLOR_2);

        // When
        projectDao.insert(projectEntity);
        projectDao.insert(projectEntity2);
        List<ProjectEntity> results = projectDao.getAllSync();

        // Then
        assertEquals(
                Arrays.asList(
                        new ProjectEntity(1, EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1),
                        new ProjectEntity(2, EXPECTED_PROJECT_NAME_2, EXPECTED_PROJECT_COLOR_2)
                ),
                results
        );
    }
    @Test(expected = SQLiteException.class)
    public void insert_should_fail_with_duplicate_id() {
        // Given
        ProjectEntity projectEntity = new ProjectEntity(1, EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1);

        // When
        projectDao.insert(projectEntity);
        projectDao.insert(projectEntity);
    }

    @Test
    public void getAll_should_return_empty() {
        // When
        List<ProjectEntity> results = LiveDataTestUtils.getValueForTesting(projectDao.getAll());

        // Then
        assertTrue(results.isEmpty());
    }

    @Test
    public void getAllSync_should_return_empty() {
        // When
        List<ProjectEntity> results = projectDao.getAllSync();

        // Then
        assertTrue(results.isEmpty());
    }
}
