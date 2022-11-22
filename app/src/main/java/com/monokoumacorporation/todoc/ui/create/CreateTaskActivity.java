package com.monokoumacorporation.todoc.ui.create;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.ui.list.ListTaskViewModel;
import com.monokoumacorporation.todoc.utils.ViewModelFactory;

public class CreateTaskActivity extends AppCompatActivity {


    private final static int TARTAMPION_ID = 1 ;
    private final static int LUCIDIA_ID = 2;
    private final static int CIRCUS_ID = 3;

    public static Intent navigate(Context c) {
        Intent intent = new Intent(c, CreateTaskActivity.class);
        return intent;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        final CreateTaskViewModel createTaskViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateTaskViewModel.class);

        Toolbar toolbar = findViewById(R.id.create_task_act_toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        TextInputLayout taskTIL = findViewById(R.id.create_task_act_task_til);
        TextInputEditText taskTIET = findViewById(R.id.create_task_act_task_tiet);

        MaterialButton tartampionButton = findViewById(R.id.create_task_act_tartampion_button);
        MaterialButton lucidiaButton = findViewById(R.id.create_task_act_lucidia_button);
        MaterialButton circusButton = findViewById(R.id.create_task_act_circus_button);

        TextView noCheckBoxCheckedErrorTV = findViewById(R.id.create_task_act_error_checkbox_tv);

        MaterialButton addTaskButton = findViewById(R.id.create_task_act_add_task_button);

        addTaskButton.setOnClickListener(view -> createTaskViewModel.onAddButtonClicked());

        tartampionButton.setOnClickListener(view -> createTaskViewModel.onTartampionButtonClicked(TARTAMPION_ID));
        lucidiaButton.setOnClickListener(view -> createTaskViewModel.onLucidiaButtonClicked(LUCIDIA_ID));
        circusButton.setOnClickListener(view -> createTaskViewModel.onCircusButtonClicked(CIRCUS_ID));

        taskTIET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                createTaskViewModel.onTaskTietTextChange(editable.toString());
            }
        });

        createTaskViewModel.getCreateTaskViewStateMutableLiveData().observe(this, createTaskViewState -> {
            taskTIET.setError(createTaskViewState.getTaskInputErrorMessage());
            noCheckBoxCheckedErrorTV.setVisibility(createTaskViewState.getCheckboxErrorVisibility());
            tartampionButton.setBackgroundColor(createTaskViewState.getTartampionButtonBackgroundColor());
            lucidiaButton.setBackgroundColor(createTaskViewState.getLucidiaButtonBackgroundColor());
            circusButton.setBackgroundColor(createTaskViewState.getCircusButtonBackgroundColor());

            tartampionButton.setTextColor(createTaskViewState.getTartampionTextButtonColor());
            lucidiaButton.setTextColor(createTaskViewState.getLucidiaTextButtonColor());
            circusButton.setTextColor(createTaskViewState.getCircusTextButtonColor());
        });

        createTaskViewModel.getOnFinishActivityEvent().observe(this, aVoid -> finish());
    }
}