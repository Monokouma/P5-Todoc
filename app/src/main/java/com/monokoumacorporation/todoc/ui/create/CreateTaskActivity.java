package com.monokoumacorporation.todoc.ui.create;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.ui.create.button.OnProjectButton;
import com.monokoumacorporation.todoc.ui.create.button.ProjectButtonAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateTaskActivity extends AppCompatActivity implements OnProjectButton {
    private CreateTaskViewModel createTaskViewModel;

    public static Intent navigate(Context c) {
        Intent intent = new Intent(c, CreateTaskActivity.class);
        return intent;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        createTaskViewModel = new ViewModelProvider(this).get(CreateTaskViewModel.class);
        Toolbar toolbar = findViewById(R.id.create_task_act_toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
        TextInputEditText taskTIET = findViewById(R.id.create_task_act_task_tiet);
        TextView noCheckBoxCheckedErrorTV = findViewById(R.id.create_task_act_error_checkbox_tv);
        MaterialButton addTaskButton = findViewById(R.id.create_task_act_add_task_button);
        ProjectButtonAdapter projectButtonAdapter = new ProjectButtonAdapter(this);
        RecyclerView buttonRecyclerView = findViewById(R.id.create_task_act_button_rv);
        buttonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        buttonRecyclerView.setAdapter(projectButtonAdapter);
        addTaskButton.setOnClickListener(view -> createTaskViewModel.onAddButtonClicked());

        taskTIET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                createTaskViewModel.onTaskTextChange(editable.toString());
            }
        });

        createTaskViewModel.getTaskViewStateMediatorLiveData().observe(this, createTaskViewState -> {
            taskTIET.setError(createTaskViewState.getTaskInputErrorMessage());
            noCheckBoxCheckedErrorTV.setVisibility(createTaskViewState.getCheckboxErrorVisibility());
            projectButtonAdapter.submitList(createTaskViewState.getProjectButtonViewStateItemList());
        });

        createTaskViewModel.getOnFinishActivityEvent().observe(this, aVoid -> finish());
    }

    @Override
    public void onProjectButtonClicked(long buttonId) {
        createTaskViewModel.onProjectButtonClicked(buttonId);
    }
}