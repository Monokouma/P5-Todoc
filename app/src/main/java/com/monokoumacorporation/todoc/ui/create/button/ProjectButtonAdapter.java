package com.monokoumacorporation.todoc.ui.create.button;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.monokoumacorporation.todoc.R;

public class ProjectButtonAdapter extends ListAdapter<ProjectButtonViewStateItem, ProjectButtonAdapter.ViewHolder> {

    @NonNull
    private final OnProjectButton listener;

    public ProjectButtonAdapter(@NonNull OnProjectButton listener) {
        super(new ProjectButtonAdapterDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_button_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialButton projectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            projectButton = itemView.findViewById(R.id.project_button);
        }

        public void bind(
            @NonNull final ProjectButtonViewStateItem projectButtonViewStateItem, @NonNull OnProjectButton onProjectButton) {
            projectButton.setOnClickListener(view -> onProjectButton.onProjectButtonClicked(projectButtonViewStateItem.getId()));
            projectButton.setBackgroundColor(projectButtonViewStateItem.getBackgroundColor());
            projectButton.setText(projectButtonViewStateItem.getButtonName());
        }
    }

    private static class ProjectButtonAdapterDiffCallback extends DiffUtil.ItemCallback<ProjectButtonViewStateItem> {

        @Override
        public boolean areItemsTheSame(@NonNull ProjectButtonViewStateItem oldItem, @NonNull ProjectButtonViewStateItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProjectButtonViewStateItem oldItem, @NonNull ProjectButtonViewStateItem newItem) {
            return oldItem.equals(newItem);
        }
    }

}
