package com.sushant.taskmanger;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sushant.taskmanger.Enum.Priority;
import com.sushant.taskmanger.Enum.Status;
import com.sushant.taskmanger.Service.ApiService;
import com.sushant.taskmanger.Service.RetrofitClientInstance;
import com.sushant.taskmanger.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CreateTask extends AppCompatActivity {
    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private EditText taskDeadlineEditText;
    private EditText taskPriorityEditText;
    private EditText taskStatusEditText;
    private Button submitTaskButton;
    ApiService apiService;
    String jwtToken;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        taskTitleEditText = findViewById(R.id.taskTitleEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        taskDeadlineEditText = findViewById(R.id.taskDeadlineEditText);
        taskPriorityEditText = findViewById(R.id.taskPriorityEditText);
        submitTaskButton = findViewById(R.id.submitTaskButton);
        // Initialize Retrofit
        RetrofitClientInstance retrofitClientInstance = new RetrofitClientInstance();
        apiService = retrofitClientInstance.getRetrofit().create(ApiService.class);
        jwtToken = retrieveTokenFromSharedPreferences();

        submitTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to submit task
                submitTask();
            }
        });
    }

    private void submitTask() {
        // Get data from EditText fields
        String title = taskTitleEditText.getText().toString();
        String description = taskDescriptionEditText.getText().toString();
        Date deadline = null;
        Priority priority = null;
        String inputFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdfInput = new SimpleDateFormat(inputFormat);

        try {
            Date deadlineParsed = sdfInput.parse(taskDeadlineEditText.getText().toString());

            // Format the parsed date in the required format
            SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            String deadlineFormatted = sdfOutput.format(deadlineParsed);
            deadline = sdfOutput.parse(deadlineFormatted);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String priorityString = taskPriorityEditText.getText().toString();
        if (priorityString.equals(Priority.HIGH.toString())) {
            priority = Priority.HIGH;
        } else if (priorityString.equals(Priority.MEDIUM.toString())) {
            priority = Priority.MEDIUM;
        } else if (priorityString.equals(Priority.LOW.toString())) {
            priority = Priority.LOW;
        }

        // Create Task object
        Task task = new Task(title, description, deadline, priority);

        // Call API to update task with PUT request and pass JWT token
        Call<Task> call = apiService.createTask("Bearer " + jwtToken, task);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(CreateTask.this, "Task_Added_successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(CreateTask.this,TaskActivity.class);
                    startActivity(intent);
                } else {
                    // Handle sign-up failure
                    Toast.makeText(CreateTask.this, "Task_Added failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                // Handle failure
                t.printStackTrace();
            }
        });
    }

    private String retrieveTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }
}