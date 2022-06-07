package com.bawp.todoister.main.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.todoister.main.data.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    public static TaskRepository taskRepository;
    public final LiveData<List<Task>> allTasks;

    public TaskViewModel(@NonNull Application application){
        super(application);
        taskRepository=new TaskRepository(application);
        allTasks=taskRepository.getAllTasks();
    }
    public LiveData<List<Task>> getAllTasks(){return allTasks;}
    public static void insert(Task task){taskRepository.insert(task);}
    public LiveData<Task> get(long id){return taskRepository.get(id);}
    public static void update(Task task){taskRepository.update(task);}
    public static void delete(Task task){taskRepository.delete(task);}
}
