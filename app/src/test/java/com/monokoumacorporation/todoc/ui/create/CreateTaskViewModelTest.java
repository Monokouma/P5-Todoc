package com.monokoumacorporation.todoc.ui.create;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.content.res.Resources;
import android.view.View;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.monokoumacorporation.todoc.LiveDataTestUtils;
import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.repository.TaskRepository;
import com.monokoumacorporation.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class CreateTaskViewModelTest {
    private static final int DEFAULT_BACKGROUND_BUTTON_COLOR = R.color.charcoal;
    private static final int DEFAULT_TEXT_BUTTON_COLOR = R.color.white;

    private static final int EXEPECTED_TARTAMPION_BUTTON_BACKGROUND_SELECTED_COLOR = R.color.dogwood_rose;
    private static final int EXEPECTED_LUCIDIA_BUTTON_BACKGROUND_SELECTED_COLOR = R.color.green_munsell;
    private static final int EXEPECTED_CIRCUS_BUTTON_BACKGROUND_SELECTED_COLOR = R.color.marigold;

    private static final String EXPECTED_TASK_NAME_ERROR = "EXPECTED_TASK_NAME_ERROR";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final Resources resources = Mockito.mock(Resources.class);
    private CreateTaskViewModel createTaskViewModel;

    @Before
    public void setUp() {
        createTaskViewModel = new CreateTaskViewModel(taskRepository, resources, new TestExecutor(), new TestExecutor());
        Mockito.doReturn(EXPECTED_TASK_NAME_ERROR).when(resources).getString(R.string.task_name_error);
    }

    @Test
    public void nominal_case() {
        //When
        CreateTaskViewState result = LiveDataTestUtils.getValueForTesting(createTaskViewModel.getTaskViewStateMediatorLiveData());

        //Then
        assertNull(
            result.getTaskInputErrorMessage()
        );
        assertEquals(result.getCheckboxErrorVisibility(),
            View.GONE
        );

        assertEquals(
            resources.getColor(result.getTartampionButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(result.getLucidiaButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );
        assertEquals(
            resources.getColor(result.getCircusButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR),
            resources.getColor(result.getTartampionTextButtonColor())
        );
        assertEquals(
            resources.getColor(result.getLucidiaTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)

        );
        assertEquals(
            resources.getColor(result.getCircusTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)
        );
    }

    @Test
    public void tartampion_button_selected_should_return_selected_color() {
        //Given
        createTaskViewModel.onProjectButtonClicked(0);

        //When
        CreateTaskViewState result = LiveDataTestUtils.getValueForTesting(createTaskViewModel.getTaskViewStateMediatorLiveData());

        //Then
        assertNull(
            result.getTaskInputErrorMessage()
        );
        assertEquals(result.getCheckboxErrorVisibility(),
            View.GONE
        );

        assertEquals(
            resources.getColor(result.getTartampionButtonBackgroundColor()),
            resources.getColor(EXEPECTED_TARTAMPION_BUTTON_BACKGROUND_SELECTED_COLOR)
        );

        assertEquals(
            resources.getColor(result.getLucidiaButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );
        assertEquals(
            resources.getColor(result.getCircusButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR),
            resources.getColor(result.getTartampionTextButtonColor())
        );
        assertEquals(
            resources.getColor(result.getLucidiaTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)

        );
        assertEquals(
            resources.getColor(result.getCircusTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)
        );
    }

    @Test
    public void lucidia_button_selected_should_return_selected_color() {
        //Given
        createTaskViewModel.onLucidiaButtonClicked(1);

        //When
        CreateTaskViewState result = LiveDataTestUtils.getValueForTesting(createTaskViewModel.getTaskViewStateMediatorLiveData());

        //Then
        assertNull(
            result.getTaskInputErrorMessage()
        );
        assertEquals(result.getCheckboxErrorVisibility(),
            View.GONE
        );

        assertEquals(
            resources.getColor(result.getTartampionButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(result.getLucidiaButtonBackgroundColor()),
            resources.getColor(EXEPECTED_LUCIDIA_BUTTON_BACKGROUND_SELECTED_COLOR)
        );
        assertEquals(
            resources.getColor(result.getCircusButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR),
            resources.getColor(result.getTartampionTextButtonColor())
        );
        assertEquals(
            resources.getColor(result.getLucidiaTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)

        );
        assertEquals(
            resources.getColor(result.getCircusTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)
        );
    }

    @Test
    public void circus_button_selected_should_return_selected_color() {
        //Given
        createTaskViewModel.onCircusButtonClicked(2);


        //When
        CreateTaskViewState result = LiveDataTestUtils.getValueForTesting(createTaskViewModel.getTaskViewStateMediatorLiveData());

        //Then
        assertNull(
            result.getTaskInputErrorMessage()
        );
        assertEquals(result.getCheckboxErrorVisibility(),
            View.GONE
        );

        assertEquals(
            resources.getColor(result.getTartampionButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(result.getLucidiaButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );
        assertEquals(
            resources.getColor(result.getCircusButtonBackgroundColor()),
            resources.getColor(EXEPECTED_CIRCUS_BUTTON_BACKGROUND_SELECTED_COLOR)
        );

        assertEquals(
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR),
            resources.getColor(result.getTartampionTextButtonColor())
        );
        assertEquals(
            resources.getColor(result.getLucidiaTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)

        );
        assertEquals(
            resources.getColor(result.getCircusTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)
        );
    }

    @Test
    public void all_field_filled_should_create_task_and_finish_activity() {
        //Given
        createTaskViewModel.onLucidiaButtonClicked(1);
        createTaskViewModel.onTaskTextChange("task_name");
        createTaskViewModel.onAddButtonClicked();

        //When
        CreateTaskViewState result = LiveDataTestUtils.getValueForTesting(createTaskViewModel.getTaskViewStateMediatorLiveData());

        //Then
        assertNull(
            result.getTaskInputErrorMessage()
        );
        assertEquals(result.getCheckboxErrorVisibility(),
            View.GONE
        );

        assertEquals(
            resources.getColor(result.getTartampionButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(result.getLucidiaButtonBackgroundColor()),
            resources.getColor(EXEPECTED_LUCIDIA_BUTTON_BACKGROUND_SELECTED_COLOR)
        );
        assertEquals(
            resources.getColor(result.getCircusButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR),
            resources.getColor(result.getTartampionTextButtonColor())
        );
        assertEquals(
            resources.getColor(result.getLucidiaTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)

        );
        assertEquals(
            resources.getColor(result.getCircusTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)
        );
    }

    @Test
    public void empty_task_name_given_should_return_error() {
        //Given
        createTaskViewModel.onLucidiaButtonClicked(1);
        createTaskViewModel.onAddButtonClicked();

        //When
        CreateTaskViewState result = LiveDataTestUtils.getValueForTesting(createTaskViewModel.getTaskViewStateMediatorLiveData());

        //Then
        assertEquals(
            result.getTaskInputErrorMessage(),
            EXPECTED_TASK_NAME_ERROR

        );
        assertEquals(result.getCheckboxErrorVisibility(),
            View.GONE
        );

        assertEquals(
            resources.getColor(result.getTartampionButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(result.getLucidiaButtonBackgroundColor()),
            resources.getColor(EXEPECTED_LUCIDIA_BUTTON_BACKGROUND_SELECTED_COLOR)
        );
        assertEquals(
            resources.getColor(result.getCircusButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR),
            resources.getColor(result.getTartampionTextButtonColor())
        );
        assertEquals(
            resources.getColor(result.getLucidiaTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)

        );
        assertEquals(
            resources.getColor(result.getCircusTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)
        );
    }

    @Test
    public void no_project_selected_should_make_appear_error_tv() {
        //Given
        createTaskViewModel.onTaskTextChange("task_name");
        createTaskViewModel.onAddButtonClicked();

        //When
        CreateTaskViewState result = LiveDataTestUtils.getValueForTesting(createTaskViewModel.getTaskViewStateMediatorLiveData());

        //Then
        assertNull(
            result.getTaskInputErrorMessage()
        );

        assertEquals(result.getCheckboxErrorVisibility(),
            View.VISIBLE
        );

        assertEquals(
            resources.getColor(result.getTartampionButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(result.getLucidiaButtonBackgroundColor()),
            resources.getColor(EXEPECTED_LUCIDIA_BUTTON_BACKGROUND_SELECTED_COLOR)
        );
        assertEquals(
            resources.getColor(result.getCircusButtonBackgroundColor()),
            resources.getColor(DEFAULT_BACKGROUND_BUTTON_COLOR)
        );

        assertEquals(
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR),
            resources.getColor(result.getTartampionTextButtonColor())
        );
        assertEquals(
            resources.getColor(result.getLucidiaTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)

        );
        assertEquals(
            resources.getColor(result.getCircusTextButtonColor()),
            resources.getColor(DEFAULT_TEXT_BUTTON_COLOR)
        );
    }

    @Test
    public void verifyOnAddButtonClicked() {

        //
        Mockito.verify(taskRepository).createTask();
    }
}
