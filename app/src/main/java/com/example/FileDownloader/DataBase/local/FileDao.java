package com.example.FileDownloader.DataBase.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.FileDownloader.Pojo.FileModel;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface FileDao {

    @Insert
     void insertFile (FileModel fileModel);

    @Query("Select * from FILE_TABLE")
     Observable<List<FileModel>> getDownloadedFiles();

}
