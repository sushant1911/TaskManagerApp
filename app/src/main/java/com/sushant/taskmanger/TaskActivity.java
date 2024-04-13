package com.sushant.taskmanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sushant.taskmanger.Service.ApiService;
import com.sushant.taskmanger.Service.RetrofitClientInstance;
import com.sushant.taskmanger.adapter.TaskAdapter;
import com.sushant.taskmanger.model.Response;
import com.sushant.taskmanger.model.Task;
import com.sushant.taskmanger.model.TaskResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TaskActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton addTaskFab;
    ApiService apiService;
    RetrofitClientInstance retrofitClientInstance = new RetrofitClientInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        addTaskFab = findViewById(R.id.task_list_fab);
        recyclerView=findViewById(R.id.taskList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         apiService = retrofitClientInstance.getRetrofit().create(ApiService.class);
        apiService.getAllTask("Bearer " + retrieveTokenFromSharedPreferences()).enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, retrofit2.Response<TaskResponse> response) {
                if(response.isSuccessful())
                {
                    List<Task> tasks=response.body().getData();
                    populateListView(tasks);
                    Toast.makeText(TaskActivity.this, "success to retrieve tasks", Toast.LENGTH_SHORT).show();

                }
                else {
                    Log.d("ResponseError", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Toast.makeText(TaskActivity.this, "failed to retrieve tasks", Toast.LENGTH_SHORT).show();

            }
        });
        addTaskFab.setOnClickListener(v -> {
            Intent intent = new Intent(TaskActivity.this, CreateTask.class);
            startActivity(intent);
        });
    }
    private void populateListView(List<Task> taskResponseList) {
        TaskAdapter taskAdapter = new TaskAdapter(taskResponseList);
        recyclerView.setAdapter(taskAdapter);
    }
    private String retrieveTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

}
