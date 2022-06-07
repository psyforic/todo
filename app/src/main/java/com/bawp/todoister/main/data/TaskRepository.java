package com.bawp.todoister.main.data;

import android.app.AppComponentFactory;
import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.todoister.main.model.Task;
import com.bawp.todoister.main.util.TaskDB;

import java.util.List;

public class TaskRepository {
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        TaskDB taskDB=TaskDB.getDatabase(application);
        taskDao = taskDB.taskDao();
        allTasks= taskDao.getTasks();
    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }
    public void insert(Task task){
     TaskDB.databaseWriteExecutor.execute(()->taskDao.insertTask(task));
    }
    public LiveData<Task> get(long id){return taskDao.get(id);}

    public void update(Task task){
    TaskDB.databaseWriteExecutor.execute(()->taskDao.updateTask(task));
    }
    public void delete(Task task){
        TaskDB.databaseWriteExecutor.execute(()->taskDao.deleteTask(task));
    }
}
