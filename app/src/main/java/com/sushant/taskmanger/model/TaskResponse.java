package com.sushant.taskmanger.model;


import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TaskResponse {
    @SerializedName("data")
    private List<Task> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public List<Task> getData() {
        return data;
    }

    public void setData(List<Task> data) {
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
