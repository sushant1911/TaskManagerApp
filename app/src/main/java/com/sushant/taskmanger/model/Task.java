package com.sushant.taskmanger.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.sushant.taskmanger.Enum.Priority;
import com.sushant.taskmanger.Enum.Status;

import java.util.Date;

public class Task {
    private long id;
    private String title;
    private String description;
    private Date deadline;
    private Priority priority;
    private Status status;
    private Date createdAt;
    private Date updatedAt;



    public Task(String title, String description, Date deadline, Priority priority) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}

