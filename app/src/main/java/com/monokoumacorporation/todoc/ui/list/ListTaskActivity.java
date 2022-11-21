package com.monokoumacorporation.todoc.ui.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.ui.create.CreateTaskActivity;
import com.monokoumacorporation.todoc.utils.ViewModelFactory;

public class ListTaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);
        final ListTaskViewModel listTaskViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ListTaskViewModel.class);
        RecyclerView taskRV = findViewById(R.id.list_task_act_no_task_recycler_view);
        FloatingActionButton floatingActionButton = findViewById(R.id.list_task_act_fab);
        floatingActionButton.setOnClickListener(view -> startActivity(CreateTaskActivity.navigate(this)));
    }
}