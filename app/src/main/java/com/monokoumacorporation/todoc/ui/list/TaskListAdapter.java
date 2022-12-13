package com.monokoumacorporation.todoc.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDivider;
import com.monokoumacorporation.todoc.R;

public class TaskListAdapter extends ListAdapter<TaskViewStateItems, TaskListAdapter.ViewHolder> {

    @NonNull
    private final OnDeleteListener listener;

    public TaskListAdapter(@NonNull OnDeleteListener listener) {
        super(new TaskListAdapterDiffCallBack());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_items, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView projectColorImage;
        private final TextView taskNameTv;
        private final TextView projectName;
        private final ImageView deleteImage;
        private final MaterialDivider materialDivider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            projectColorImage = itemView.findViewById(R.id.task_list_item_circle_image);
            taskNameTv = itemView.findViewById(R.id.task_list_item_task_name_tv);
            projectName = itemView.findViewById(R.id.task_list_item_project_name_tv);
            deleteImage = itemView.findViewById(R.id.task_list_item_delete_image);
            materialDivider = itemView.findViewById(R.id.task_list_item_divider);
        }

        public void bind(@NonNull final TaskViewStateItems listActivityViewStateItems, @NonNull OnDeleteListener listener) {
            projectColorImage.setColorFilter(listActivityViewStateItems.getProjectColor());
            taskNameTv.setText(listActivityViewStateItems.getTaskName());
            projectName.setText(listActivityViewStateItems.getProjectName());
            materialDivider.setDividerColor(listActivityViewStateItems.getProjectColor());
            deleteImage.setColorFilter(listActivityViewStateItems.getProjectColor());
            deleteImage.setOnClickListener(view -> listener.deleteItem(listActivityViewStateItems.getId()));
        }
    }

    private static class TaskListAdapterDiffCallBack extends DiffUtil.ItemCallback<TaskViewStateItems> {

        @Override
        public boolean areItemsTheSame(@NonNull TaskViewStateItems oldItem, @NonNull TaskViewStateItems newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskViewStateItems oldItem, @NonNull TaskViewStateItems newItem) {
            return oldItem.equals(newItem);
        }
    }

}
