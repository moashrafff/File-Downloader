package com.example.nagwaassignment.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    public static FilesDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FilesDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FilesDataBase.class, "FILES_DATABASE")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
