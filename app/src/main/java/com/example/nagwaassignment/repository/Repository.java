package com.example.nagwaassignment.repository;

import android.app.Application;

import com.example.nagwaassignment.Data.FileInterface;
import com.example.nagwaassignment.DataBase.FileDao;
import com.example.nagwaassignment.DataBase.FilesDataBase;
import com.example.nagwaassignment.Pojo.FileModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class Repository {


    FileDao fileDao;
    FileInterface fileInterface;

    @Inject
    public Repository(FileDao fileDao, FileInterface fileInterface) {
        this.fileDao = fileDao;
        this.fileInterface = fileInterface;
    }

    public Observable<List<FileModel>> getNewFiles() {
        return fileInterface.getFiles();
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
