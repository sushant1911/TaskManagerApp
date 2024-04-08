package com.sushant.taskmanger.Service;

import com.sushant.taskmanger.model.SignInResponse;
import com.sushant.taskmanger.model.Task;
import com.sushant.taskmanger.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/auth/signup")
    Call<User> signUp(@Body User user);

    @POST("/auth/sign-in")
    Call<SignInResponse> signIn(@Body User user);
    @POST("/task/createTask")
    Call<Task> createTask(@Header("Authorization") String authorizationHeader, @Body Task task);


}

