package com.sushant.taskmanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sushant.taskmanger.Service.ApiService;
import com.sushant.taskmanger.Service.RetrofitClientInstance;
import com.sushant.taskmanger.model.SignInResponse;
import com.sushant.taskmanger.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonSignIn;
    private ApiService retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);

        // Initialize Retrofit API instance
        RetrofitClientInstance retrofitClientInstance = new RetrofitClientInstance();
        retrofitAPI = retrofitClientInstance.getRetrofit().create(ApiService.class);

        // Set click listener for Sign In button
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username and password from EditText fields
                String email = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Create a user object with username and password
                User signInRequest = new User(email, password);

                // Call the sign-in API
                retrofitAPI.signIn(signInRequest).enqueue(new Callback<SignInResponse>() {
                    @Override
                    public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Save the token in SharedPreferences
                            String token = response.body().getToken();
                            saveTokenInSharedPreferences(token);

                            // Handle successful sign-in
                            Toast.makeText(SignInActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            // Navigate to the next screen or perform any other action
                            // For example, you can start the MainActivity
                            startActivity(new Intent(SignInActivity.this, TaskActivity.class));
                            finish(); // Finish the current activity
                        } else {
                            // Handle sign-in failure
                            Toast.makeText(SignInActivity.this, "Sign-in failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignInResponse> call, Throwable t) {
                        // Handle network error
                        Toast.makeText(SignInActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Method to save token in SharedPreferences
    private void saveTokenInSharedPreferences(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }
}
