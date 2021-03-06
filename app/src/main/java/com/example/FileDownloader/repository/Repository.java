package com.example.FileDownloader.repository;

import com.example.FileDownloader.DataBase.remote.FileInterface;
import com.example.FileDownloader.DataBase.local.FileDao;
import com.example.FileDownloader.DataBase.local.FilesDataBase;
import com.example.FileDownloader.Pojo.FileModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class Repository {


    @Inject
    FileDao fileDao;
    @Inject
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
