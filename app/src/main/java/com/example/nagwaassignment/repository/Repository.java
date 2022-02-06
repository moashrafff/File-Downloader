package com.example.nagwaassignment.repository;

import android.app.Application;

import com.example.nagwaassignment.DataBase.FileDao;
import com.example.nagwaassignment.DataBase.FilesDataBase;
import com.example.nagwaassignment.Pojo.FileModel;

import java.util.List;

import io.reactivex.Observable;

public class Repository {

    FileDao fileDao;

    public Repository(Application application) {
        FilesDataBase dataBase = FilesDataBase.getDatabase(application);
        fileDao = dataBase.fileDao();
    }

    public void insertFile(FileModel fileModel) {
        FilesDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                fileDao.insertFile(fileModel);
            }
        });
    }

    public Observable<List<FileModel>> getDownloadedFiles() {
        return fileDao.getDownloadedFiles();
    }

}
