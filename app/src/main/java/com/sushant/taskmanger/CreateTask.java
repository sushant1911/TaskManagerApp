package com.sushant.taskmanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sushant.taskmanger.Enum.Priority;
import com.sushant.taskmanger.Enum.Status;
import com.sushant.taskmanger.Service.ApiService;
import com.sushant.taskmanger.Service.RetrofitClientInstance;
import com.sushant.taskmanger.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.app.TimePickerDialog;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTask extends AppCompatActivity {
    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private EditText taskDeadlineEditText;
    private Spinner taskPriorityEditText;
    private Calendar calendar;
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
        taskPriorityEditText = findViewById(R.id.taskPrioritySpinner);
        submitTaskButton = findViewById(R.id.submitTaskButton);
        Spinner prioritySpinner = findViewById(R.id.taskPrioritySpinner);
        calendar = Calendar.getInstance();
        taskDeadlineEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<Priority> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Priority.values());

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        prioritySpinner.setAdapter(adapter);
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
        String inputFormat = "yyyy-MM-dd'T'HH:mm:ss";
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
        Priority priorityString = (Priority) taskPriorityEditText.getSelectedItem();
        if (priorityString == Priority.HIGH) {
            priority = Priority.HIGH;
        } else if (priorityString == Priority.MEDIUM) {
            priority = Priority.MEDIUM;
        } else {
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
                    sendNotification("Task Created", "Task: " + title + " created successfully");
                    Toast.makeText(CreateTask.this, "Task_Added_successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateTask.this, TaskActivity.class);
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

    private void sendNotification(String title, String message) {
        // Create an Intent for the TaskActivity
        Intent intent = new Intent(this, TaskActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        // Create the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent).setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Get the NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Check if the version is Oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Show the notification
        notificationManager.notify(0, notificationBuilder.build());
    }

    private String retrieveTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the calendar with the selected date
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Show the TimePicker dialog after selecting the date
                        showTimePickerDialog();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Update the calendar with the selected time
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        // Update the EditText with the selected date and time
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                        taskDeadlineEditText.setText(sdf.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false // 24-hour format
        );
        timePickerDialog.show();
    }
}