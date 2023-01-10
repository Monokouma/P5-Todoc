package com.monokoumacorporation.todoc.ui.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.ui.create.CreateTaskActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListTaskActivity extends AppCompatActivity implements OnDeleteListener {

    private ListTaskViewModel listTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);
        listTaskViewModel = new ViewModelProvider(this).get(ListTaskViewModel.class);
        Toolbar toolbar = findViewById(R.id.list_task_act_toolbar);
        setSupportActionBar(toolbar);

        RecyclerView taskRV = findViewById(R.id.list_task_act_recycler_view);
        FloatingActionButton floatingActionButton = findViewById(R.id.list_task_act_fab);
        ConstraintLayout noTaskMessageLayout = findViewById(R.id.list_task_act_no_task_layout);
        ImageView noTaskImage = findViewById(R.id.list_task_act_no_task_image);

        floatingActionButton.setOnClickListener(view -> startActivity(CreateTaskActivity.navigate(this)));

        Glide.with(this).load(R.drawable.empty_list_gif).into(noTaskImage);
        taskRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final TaskListAdapter taskListAdapter = new TaskListAdapter(this);
        taskRV.setAdapter(taskListAdapter);

        listTaskViewModel.getTaskListMutableLiveData().observe(this, new Observer<ListTaskViewState>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onChanged(ListTaskViewState listTaskViewState) {
                taskListAdapter.submitList(listTaskViewState.getListActivityViewStateItemsList());
                noTaskMessageLayout.setVisibility(listTaskViewState.getEmptyListMessageVisibility());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.filter_alphabetical) {
            listTaskViewModel.onAlphabeticalFilterClick();
        } else if (itemId == R.id.filter_alphabetical_inverted) {
            listTaskViewModel.onAlphabeticalInvertedFilterClick();
        }  else if (itemId == R.id.filter_oldest_first) {
            listTaskViewModel.onOlderFirstFilterClick();
        } else if (itemId == R.id.filter_recent_first) {
            listTaskViewModel.onRecentFirstFilterClick();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;

    }

    @Override
    public void deleteItem(long taskId) {
        listTaskViewModel.onDeleteButtonClick(taskId);
    }
}