package com.bawp.todoister.main.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bawp.todoister.main.model.Task;
import com.bawp.todoister.main.util.Constants;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
     void insertTask(Task task);

   @Query("DELETE FROM "+Constants.DATABASE_NAME)
    void deleteAll();

    @Query("SELECT * FROM "+Constants.DATABASE_NAME)
     LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM "+Constants.DATABASE_NAME+" WHERE task_id == :id")
     LiveData<Task> get(long id);

    @Update
     void updateTask(Task task);

    @Delete
     void deleteTask(Task task);
}
