package com.sushant.taskmanger.Service;

import com.sushant.taskmanger.model.Response;
import com.sushant.taskmanger.model.Task;
import com.sushant.taskmanger.model.TaskResponse;
import com.sushant.taskmanger.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/auth/signup")
    Call<User> signUp(@Body User user);

    @POST("/auth/sign-in")
    Call<Response> signIn(@Body User user);
    @POST("/task/createTask")
    Call<Task> createTask(@Header("Authorization") String authorizationHeader, @Body Task task);

    @GET("/task/getAllTasksByUserId")
    Call <TaskResponse> getAllTask(@Header("Authorization") String authorizationHeader);


}

