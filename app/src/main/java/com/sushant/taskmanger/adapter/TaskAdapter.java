package com.sushant.taskmanger.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sushant.taskmanger.R;
import com.sushant.taskmanger.model.Task;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

    private List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task = taskList.get(position);
        holder.titleTextView.setText(task.getTitle());
        holder.descriptionTextView.setText(task.getDescription());
        holder.deadlineTextView.setText(task.getDeadline().toString()); // Format the date as needed
        holder.priorityTextView.setText(task.getPriority().toString());
        holder.statusTextView.setText(task.getStatus().toString());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
