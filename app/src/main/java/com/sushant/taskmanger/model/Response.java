package com.sushant.taskmanger.model;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("data")
    private String data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
