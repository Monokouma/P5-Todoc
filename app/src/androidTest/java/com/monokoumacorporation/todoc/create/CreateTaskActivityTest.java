package com.monokoumacorporation.todoc.create;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.ui.create.CreateTaskActivity;
import com.monokoumacorporation.todoc.utils.CreateTaskUtils;
import com.monokoumacorporation.todoc.utils.EditTextErrorMatcher;
import com.monokoumacorporation.todoc.utils.MyViewAction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateTaskActivityTest {


    private CreateTaskActivity createTaskActivity;

    @Before
    public void setUp() {
        ActivityScenario<CreateTaskActivity> activityScenario = ActivityScenario.launch(CreateTaskActivity.class);
        activityScenario.onActivity(activity -> createTaskActivity = activity);
    }

    @After
    public void tearDown() {
        createTaskActivity = null;
    }

    @Test
    public void insert_task_should_show_no_error() {

        CreateTaskUtils.createTask("foo", 1);

        assertTrue(createTaskActivity.isFinishing());
    }

    @Test
    public void insert_task_with_no_name_should_show_error() {
        CreateTaskUtils.createTask("", 1);

        onView(
                ViewMatchers.withId(R.id.create_task_act_task_tiet)
        ).check(
                matches(
                        new EditTextErrorMatcher(R.string.task_name_error)
                )
        );

        assertFalse(createTaskActivity.isFinishing());
    }

    @Test
    public void insert_task_with_no_project_button_clicked_should_show_error() {
        CreateTaskUtils.createTaskWithoutProjectSelection("foo");

        onView(
                withId(R.id.create_task_act_error_checkbox_tv)
        ).check(
                matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))
        );

        assertFalse(createTaskActivity.isFinishing());
    }

}

