package com.monokoumacorporation.todoc.data.dao;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.res.Resources;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.database.TodocDatabase;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.entity.ProjectWithTasksEntity;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;
import com.monokoumacorporation.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private static final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private static final int EXPECTED_TARTAMPION_COLOR = 0xDC136C;
    private static final String EXPECTED_TARTAMPION_NAME = "EXPECTED_TARTAMPION_NAME";

    private final Resources resources = Mockito.mock(Resources.class);


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TodocDatabase todocDatabase;
    private TaskDao taskDao;
    private ProjectDao projectDao;

    @Before
    public void setUp() {
        Mockito.doReturn(EXPECTED_TARTAMPION_COLOR).when(resources).getColor(R.color.dogwood_rose);
        Mockito.doReturn(EXPECTED_TARTAMPION_NAME).when(resources).getString(R.string.projet_tartampion);

    }

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        todocDatabase = Room
                .inMemoryDatabaseBuilder(context, TodocDatabase.class)
                .build();
        taskDao = todocDatabase.getTaskDao();
        projectDao = todocDatabase.getProjectDao();
        projectDao.insert(new ProjectEntity(EXPECTED_TARTAMPION_NAME, EXPECTED_TARTAMPION_COLOR));

    }

    @After
    public void closeDb() {
        todocDatabase.close();
    }

    @Test
    public void insertOneTask() {
        taskDao.insertTask(new TaskEntity(1, "foo", clock.millis()));

        List<ProjectWithTasksEntity> results = LiveDataTestUtils.getValueForTesting(taskDao.getAllProjectWithTasks());
        System.out.println(results.toString());

        assertEquals(
                Collections.singletonList(
                        new ProjectWithTasksEntity(
                                new ProjectEntity(
                                        1,
                                        resources.getString(R.string.projet_tartampion),
                                        R.color.dogwood_rose
                                ),
                                Arrays.asList(
                                        new TaskEntity(
                                                1,
                                                "foo",
                                                clock.millis()
                                        )
                                )
                        )
                ),
                results
        );

    }

    @Test
    public void insertTwoTask() {
        TaskEntity taskOne = new TaskEntity(1, "foo", clock.millis());
        TaskEntity taskTwo = new TaskEntity(2, "bar", clock.millis());
        taskDao.insertTask(taskOne);
        taskDao.insertTask(taskTwo);

        List<ProjectWithTasksEntity> results = LiveDataTestUtils.getValueForTesting(taskDao.getAllProjectWithTasks());

        System.out.println(results.toString());
    }
}
