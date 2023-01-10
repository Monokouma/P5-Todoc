package com.monokoumacorporation.todoc.utils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.contrib.RecyclerViewActions;

import com.monokoumacorporation.todoc.R;

public class CreateTaskUtils {
    //Region in
    public static void createTask(String taskName, Integer boutonPosition) {
        onView(
                withId(R.id.create_task_act_task_tiet)
        ).perform(
                replaceText(taskName),
                closeSoftKeyboard()
        );

        onView(
                withId(R.id.create_task_act_button_rv)
        ).perform(
                RecyclerViewActions.actionOnItemAtPosition(boutonPosition, MyViewAction.clickChildViewWithId(R.id.project_button))
        );

        onView(
                withId(R.id.create_task_act_add_task_button)
        ).perform(
                click()
        );
    }

    public static void createTaskWithoutProjectSelection(String taskName) {
        onView(
                withId(R.id.create_task_act_task_tiet)
        ).perform(
                replaceText(taskName),
                closeSoftKeyboard()
        );

        onView(
                withId(R.id.create_task_act_add_task_button)
        ).perform(
                click()
        );
    }
}
