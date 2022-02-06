package com.example.nagwaassignment.Data;

import com.example.nagwaassignment.Pojo.FileModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FileClient {

    private static final String BASE_URL = "https://ashraf.free.beeceptor.com/";
    private FileInterface fileInterface;
    private static FileClient INSTANCE;

    public FileClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        fileInterface = retrofit.create(FileInterface.class);
    }

    public static FileClient getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new FileClient();
        }
        return INSTANCE;
    }

    public Observable<List<FileModel>> getFiles() {
        return fileInterface.getFiles();
    }
}
