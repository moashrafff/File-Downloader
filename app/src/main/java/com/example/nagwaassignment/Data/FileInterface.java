package com.example.nagwaassignment.Data;

import com.example.nagwaassignment.Pojo.FileModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface FileInterface {
    @GET("movies")
    public Observable<List<FileModel>> getFiles();
}
