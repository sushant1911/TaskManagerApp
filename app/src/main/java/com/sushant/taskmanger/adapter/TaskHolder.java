package com.sushant.taskmanger.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sushant.taskmanger.R;

public class TaskHolder extends RecyclerView.ViewHolder {

    TextView titleTextView, descriptionTextView, deadlineTextView, priorityTextView, statusTextView;

    public TaskHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.taskListItem_title);
        descriptionTextView = itemView.findViewById(R.id.taskListItem_description);
        deadlineTextView = itemView.findViewById(R.id.taskListItem_deadline);
        priorityTextView = itemView.findViewById(R.id.taskListItem_priority);
        statusTextView = itemView.findViewById(R.id.taskListItem_status);
    }
}
