package com.sushant.taskmanger.model;

import com.google.gson.annotations.SerializedName;

public class SignInResponse {
    @SerializedName("data")
    private String token;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
