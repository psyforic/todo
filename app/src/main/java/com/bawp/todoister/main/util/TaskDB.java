package com.bawp.todoister.main.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bawp.todoister.main.data.TaskDao;
import com.bawp.todoister.main.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({Tools.class})
public abstract class TaskDB extends RoomDatabase {
    public static final int NUMBER_OF_THREADS=4;
    private static  volatile TaskDB INSTANCE;


    public static final RoomDatabase.Callback roomDbCallback= new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(()->{
                TaskDao taskDao= INSTANCE.taskDao();
                taskDao.deleteAll();
            });
        }
    };
    public static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TaskDB getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (TaskDB.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                                    TaskDB.class,Constants.DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract TaskDao taskDao();

}
