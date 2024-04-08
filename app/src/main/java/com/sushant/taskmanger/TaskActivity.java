package com.sushant.taskmanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskActivity extends AppCompatActivity {


    private FloatingActionButton addTaskFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        addTaskFab=findViewById(R.id.task_list_fab);
        addTaskFab.setOnClickListener(v ->  {

                Intent intent=new Intent(TaskActivity.this,CreateTask.class);
                startActivity(intent);
        });
    }
}
