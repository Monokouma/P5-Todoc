package com.monokoumacorporation.todoc.data.dao;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private static final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private static final String EXPECTED_TARTAMPION_NAME = "EXPECTED_TARTAMPION_NAME";
    private static final String EXPECTED_LUCIDIA_NAME = "EXPECTED_LUCIDIA_NAME";
    private static final int EXPECTED_TARTAMPION_COLOR = 0xDC136C;
    private static final int EXPECTED_LUCIDIA_COLOR = 0x06A77D;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TodocDatabase todocDatabase;
    private TaskDao taskDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        todocDatabase = Room
            .inMemoryDatabaseBuilder(context, TodocDatabase.class)
            .build();
        taskDao = todocDatabase.getTaskDao();
        ProjectDao projectDao = todocDatabase.getProjectDao();
        projectDao.insert(new ProjectEntity(EXPECTED_TARTAMPION_NAME, EXPECTED_TARTAMPION_COLOR));
        projectDao.insert(new ProjectEntity(EXPECTED_LUCIDIA_NAME, EXPECTED_LUCIDIA_COLOR));
    }

    @After
    public void tearDown() {
        todocDatabase.close();
    }

    @Test
    public void insertOneTask() {
        // Given
        taskDao.insertTask(new TaskEntity(1, "foo", clock.millis()));

        // When
        List<ProjectWithTasksEntity> results = LiveDataTestUtils.getValueForTesting(taskDao.getAllProjectWithTasks());

        // Then
        assertEquals(
            new ArrayList<>() {{
                add(
                    new ProjectWithTasksEntity(
                        new ProjectEntity(
                            1,
                            EXPECTED_TARTAMPION_NAME,
                            EXPECTED_TARTAMPION_COLOR
                        ),
                        new ArrayList<>() {{
                            add(
                                new TaskEntity(
                                    1,
                                    1,
                                    "foo",
                                    clock.millis()
                                )
                            );
                        }}
                    )
                );

                add(
                    new ProjectWithTasksEntity(
                        new ProjectEntity(
                            2,
                            EXPECTED_LUCIDIA_NAME,
                            EXPECTED_LUCIDIA_COLOR
                        ),
                        new ArrayList<>()
                    )
                );
            }},
            results
        );
    }

    @Test
    public void insertTwoTaskWithTwoProjects() {
        TaskEntity taskOne = new TaskEntity(1, "foo", clock.millis());
        TaskEntity taskTwo = new TaskEntity(2, "bar", clock.millis());
        taskDao.insertTask(taskOne);
        taskDao.insertTask(taskTwo);

        List<ProjectWithTasksEntity> results = LiveDataTestUtils.getValueForTesting(taskDao.getAllProjectWithTasks());

        assertEquals(
            new ArrayList<>() {{
                add(
                    new ProjectWithTasksEntity(
                        new ProjectEntity(
                            1,
                            EXPECTED_TARTAMPION_NAME,
                            EXPECTED_TARTAMPION_COLOR
                        ),
                        new ArrayList<>() {{
                            add(
                                new TaskEntity(
                                    1,
                                    1,
                                    "foo",
                                    clock.millis()
                                )
                            );
                        }}
                    )
                );

                add(
                    new ProjectWithTasksEntity(
                        new ProjectEntity(
                            2,
                            EXPECTED_LUCIDIA_NAME,
                            EXPECTED_LUCIDIA_COLOR
                        ),
                        new ArrayList<>() {{
                            add(
                                new TaskEntity(
                                    2,
                                    2,
                                    "bar",
                                    clock.millis()
                                )
                            );
                        }}
                    )
                );
            }},
            results
        );
    }

    @Test
    public void insertTwoTaskWithOneProject() {
        TaskEntity taskOne = new TaskEntity(1, "foo", clock.millis());
        TaskEntity taskTwo = new TaskEntity(1, "bar", clock.millis());
        taskDao.insertTask(taskOne);
        taskDao.insertTask(taskTwo);

        List<ProjectWithTasksEntity> results = LiveDataTestUtils.getValueForTesting(taskDao.getAllProjectWithTasks());

        assertEquals(
            new ArrayList<>() {{
                add(
                    new ProjectWithTasksEntity(
                        new ProjectEntity(
                            1,
                            EXPECTED_TARTAMPION_NAME,
                            EXPECTED_TARTAMPION_COLOR
                        ),
                        new ArrayList<>() {{
                            add(
                                new TaskEntity(
                                    1,
                                    1,
                                    "foo",
                                    clock.millis()
                                )
                            );
                            add(
                                new TaskEntity(
                                    2,
                                    1,
                                    "bar",
                                    clock.millis()
                                )
                            );
                        }}
                    )
                );

                add(
                    new ProjectWithTasksEntity(
                        new ProjectEntity(
                            2,
                            EXPECTED_LUCIDIA_NAME,
                            EXPECTED_LUCIDIA_COLOR
                        ),
                        new ArrayList<>()
                    )
                );
            }},
            results
        );
    }
}
