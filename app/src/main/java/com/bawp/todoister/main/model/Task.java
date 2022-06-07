package com.bawp.todoister.main.model;


import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "ToDO_DB")
public class Task {
    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    @ColumnInfo(name="task_id")
    @PrimaryKey(autoGenerate = true)
    public long taskId;
    @ColumnInfo(name="task_name")
    public String task;
    @ColumnInfo(name="priority")
    public Priority priority;
    @ColumnInfo(name="due_date")
    public Date date;
    @ColumnInfo(name="date_created")
    public Date dateCreated;
    @ColumnInfo(name = "is_done")
    boolean isComplete;

    public Task(String task, Priority priority, Date date, Date dateCreated, boolean isComplete) {
        this.task = task;
        this.priority = priority;
        this.date = date;
        this.dateCreated = dateCreated;
        this.isComplete = isComplete;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", task='" + task + '\'' +
                ", priority=" + priority +
                ", date=" + date +
                ", dateCreated=" + dateCreated +
                ", isComplete=" + isComplete +
                '}';
    }
}
