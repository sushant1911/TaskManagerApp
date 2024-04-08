package com.sushant.taskmanger.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.sushant.taskmanger.Enum.Priority;
import com.sushant.taskmanger.Enum.Status;

import java.util.Date;

public class Task implements Parcelable {
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
// Constructor, getters, setters, and other methods

    // Parcelable implementation
    protected Task(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        deadline = new Date(in.readLong());
        priority = Priority.valueOf(in.readString());
        status = Status.valueOf(in.readString());
        createdAt = new Date(in.readLong());
        updatedAt = new Date(in.readLong());
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(deadline.getTime());
        dest.writeString(priority.name());
        dest.writeString(status.name());
        dest.writeLong(createdAt.getTime());
        dest.writeLong(updatedAt.getTime());
    }
}

