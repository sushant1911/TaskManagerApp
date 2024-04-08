package com.sushant.taskmanger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sushant.taskmanger.Service.ApiService;
import retrofit2.converter.gson.GsonConverterFactory;

import com.sushant.taskmanger.Service.RetrofitClientInstance;
import com.sushant.taskmanger.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button buttonSignUp;
    private ApiService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        RetrofitClientInstance retrofitClientInstance=new RetrofitClientInstance();
        ApiService retrofitAPI = retrofitClientInstance.getRetrofit().create(ApiService.class);
        // Create User object
        // Set click listener for Sign Up button
        buttonSignUp.setOnClickListener(view-> {
                // Get user input from EditText fields
                String username = editTextUsername.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                // below line is to create an instance for our retrofit api class.
                User user = new User(username, email, password);

                // Call sign up API
               retrofitAPI.signUp(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            // Handle successful sign-up
                            Toast.makeText(MainActivity.this, "Sign-up successful", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle sign-up failure
                            Toast.makeText(MainActivity.this, "Sign-up failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        // Handle network error
                        Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                    }
                });
        });
    }
}
