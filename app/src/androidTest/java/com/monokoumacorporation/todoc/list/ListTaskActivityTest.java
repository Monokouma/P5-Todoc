package com.monokoumacorporation.todoc.list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.monokoumacorporation.todoc.utils.ViewAssertions.hasRecyclerViewItemCount;
import static com.monokoumacorporation.todoc.utils.ViewAssertions.onRecyclerViewItem;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.ui.list.ListTaskActivity;
import com.monokoumacorporation.todoc.utils.CreateTaskUtils;
import com.monokoumacorporation.todoc.utils.MyViewAction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListTaskActivityTest {
    private static final String FIRST_TASK_NAME = "FIRST_TASK_NAME";
    private static final Integer FIRST_PROJECT_BUTTON_ID = 0;

    private static final String SECOND_TASK_NAME = "SECOND_TASK_NAME";
    private static final Integer SECOND_PROJECT_BUTTON_ID = 1;

    private static final String THIRD_TASK_NAME = "THIRD_TASK_NAME";
    private static final Integer THIRD_PROJECT_BUTTON_ID = 2;

    private ListTaskActivity listTaskActivity;

    @Before
    public void setUp() {
        ActivityScenario<ListTaskActivity> activityScenario = ActivityScenario.launch(ListTaskActivity.class);
        activityScenario.onActivity(activity -> listTaskActivity = activity);
    }

    @After
    public void tearDown() {
        listTaskActivity = null;
    }

    @Test
    public void create_multiple_task_with_filtering() throws InterruptedException {
        //Delete all task
        nukeTable();
        //Insert 3 task
        insertTasks();
        //Verify initial task insertion
        checkItemInsertion();

        //Click on filter menu
        openFilterMenu();

        //Filter alphabetically inverted
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        //Verify item are filtered
        checkInvertedAlphabeticalSort();

        //Click on filter menu
        openFilterMenu();

        //Filter alphabetically
        onView(withText(R.string.sort_alphabetical)).perform(click());
        //Verify item are filtered
        checkAlphabeticalSort();

        //Click on filter menu
        openFilterMenu();

        //Filter newest first
        onView(withText(R.string.sort_recent_first)).perform(click());
        //Verify item are filtered
        checkNewestFirstSort();

        //Click on filter menu
        openFilterMenu();

        //Filter oldest first
        onView(withText(R.string.sort_oldest_first)).perform(click());
        //Verify item are filtered
        checkOlderFirstSort();

        deleteTask();
        deleteTask();
        deleteTask();

        Thread.sleep(300);
        checkRecyclerViewHasNoItem();

        checkNoTaskLayoutVisibility();
    }

    private void checkNoTaskLayoutVisibility() {
        onView(
                withId(R.id.list_task_act_no_task_layout)
        ).check(
                matches(
                        withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)
                )
        );
    }

    private void checkRecyclerViewHasNoItem() {
        onView(withId(R.id.list_task_act_recycler_view))
                .check(hasRecyclerViewItemCount(0));
    }

    private void deleteTask() {
        onView(
                withId(R.id.list_task_act_recycler_view)
        ).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.task_list_item_delete_image))
        );

    }

    private void checkNewestFirstSort() {
        onView(
                withId(R.id.list_task_act_recycler_view)
        )
                .check(hasRecyclerViewItemCount(3))
                .check(
                        onRecyclerViewItem(
                                2,
                                R.id.task_list_item_task_name_tv,
                                withText(FIRST_TASK_NAME)
                        )
                ).check(
                        onRecyclerViewItem(
                                1,
                                R.id.task_list_item_task_name_tv,
                                withText(SECOND_TASK_NAME)
                        )
                ).check(
                        onRecyclerViewItem(
                                0,
                                R.id.task_list_item_task_name_tv,
                                withText(THIRD_TASK_NAME)
                        )
                );
    }

    private void checkOlderFirstSort() {
        onView(
                withId(R.id.list_task_act_recycler_view)
        )
                .check(hasRecyclerViewItemCount(3))
                .check(
                        onRecyclerViewItem(
                                0,
                                R.id.task_list_item_task_name_tv,
                                withText(FIRST_TASK_NAME)
                        )
                ).check(
                        onRecyclerViewItem(
                                1,
                                R.id.task_list_item_task_name_tv,
                                withText(SECOND_TASK_NAME)
                        )
                ).check(
                        onRecyclerViewItem(
                                2,
                                R.id.task_list_item_task_name_tv,
                                withText(THIRD_TASK_NAME)
                        )
                );
    }

    private void checkAlphabeticalSort() {
        onView(
                withId(R.id.list_task_act_recycler_view)
        )
                .check(hasRecyclerViewItemCount(3))
                .check(
                        onRecyclerViewItem(
                                0,
                                R.id.task_list_item_task_name_tv,
                                withText(FIRST_TASK_NAME)
                        )
                ).check(
                        onRecyclerViewItem(
                                1,
                                R.id.task_list_item_task_name_tv,
                                withText(SECOND_TASK_NAME)
                        )
                ).check(
                        onRecyclerViewItem(
                                2,
                                R.id.task_list_item_task_name_tv,
                                withText(THIRD_TASK_NAME)
                        )
                );
    }

    private void openFilterMenu() {
        onView(
                withId(R.id.action_filter)
        ).perform(
                click()
        );
    }

    private void checkInvertedAlphabeticalSort() {
        onView(
                withId(R.id.list_task_act_recycler_view)
        )
                .check(hasRecyclerViewItemCount(3))
                .check(
                        onRecyclerViewItem(
                                2,
                                R.id.task_list_item_task_name_tv,
                                withText(FIRST_TASK_NAME)
                        )
                ).check(
                        onRecyclerViewItem(
                                1,
                                R.id.task_list_item_task_name_tv,
                                withText(SECOND_TASK_NAME)
                        )
                ).check(
                        onRecyclerViewItem(
                                0,
                                R.id.task_list_item_task_name_tv,
                                withText(THIRD_TASK_NAME)
                        )
                );
    }

    private void checkItemInsertion() {
        onView(
                withId(R.id.list_task_act_recycler_view)
        )
                .check(hasRecyclerViewItemCount(3))
                .check(
                        onRecyclerViewItem(
                                0,
                                R.id.task_list_item_task_name_tv,
                                withText(FIRST_TASK_NAME)
                        )
                ).check(
                        onRecyclerViewItem(
                                1,
                                R.id.task_list_item_task_name_tv,
                                withText(SECOND_TASK_NAME)
                        )
                ).check(
                        onRecyclerViewItem(
                                2,
                                R.id.task_list_item_task_name_tv,
                                withText(THIRD_TASK_NAME)
                        )
                );
    }

    private void insertTasks() {
        onView(withId(R.id.list_task_act_fab)).perform(click());
        CreateTaskUtils.createTask(FIRST_TASK_NAME, FIRST_PROJECT_BUTTON_ID);

        onView(withId(R.id.list_task_act_fab)).perform(click());
        CreateTaskUtils.createTask(SECOND_TASK_NAME, SECOND_PROJECT_BUTTON_ID);

        onView(withId(R.id.list_task_act_fab)).perform(click());
        CreateTaskUtils.createTask(THIRD_TASK_NAME, THIRD_PROJECT_BUTTON_ID);
    }

    private void nukeTable() {
        onView(withId(R.id.list_task_act_delete_all_fab)).perform(click());
    }
}


