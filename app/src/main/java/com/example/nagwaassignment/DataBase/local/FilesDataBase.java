package com.example.nagwaassignment.DataBase.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.nagwaassignment.Pojo.FileModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {FileModel.class}, version = 1, exportSchema = false)
public abstract class FilesDataBase extends RoomDatabase {

    public abstract FileDao fileDao();


    private static volatile FilesDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


}
