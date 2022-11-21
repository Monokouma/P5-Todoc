package com.monokoumacorporation.todoc.ui.create;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.ui.list.ListTaskViewModel;
import com.monokoumacorporation.todoc.utils.ViewModelFactory;

public class CreateTaskActivity extends AppCompatActivity {

    public static Intent navigate(Context c) {
        Intent intent = new Intent(c, CreateTaskActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        final CreateTaskViewModel createTaskViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateTaskViewModel.class);

        Toolbar toolbar = findViewById(R.id.create_task_act_toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        TextInputLayout taskTIL = findViewById(R.id.create_task_act_task_til);
        TextInputEditText taskTIET = findViewById(R.id.create_task_act_task_tiet);

        TextView noCheckBoxCheckedErrorTV = findViewById(R.id.create_task_act_error_checkbox_tv);
        MaterialCheckBox tartampionChecBox = findViewById(R.id.create_task_act_task_tartampion_checkbox);
        MaterialCheckBox lucidiaCheckBox = findViewById(R.id.create_task_act_task_lucidia_checkbox);
        MaterialCheckBox circusCheckBox = findViewById(R.id.create_task_act_task_circus_checkbox);

        MaterialButton addTaskButton = findViewById(R.id.create_task_act_add_task_button);


    }
}