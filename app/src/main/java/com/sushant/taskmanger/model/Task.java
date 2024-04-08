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

