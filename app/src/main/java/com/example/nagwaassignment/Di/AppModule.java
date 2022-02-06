package com.example.nagwaassignment.Di;

import android.app.Application;

import androidx.room.Room;

import com.example.nagwaassignment.DataBase.remote.FileInterface;
import com.example.nagwaassignment.DataBase.local.FileDao;
import com.example.nagwaassignment.DataBase.local.FilesDataBase;
import com.example.nagwaassignment.repository.Repository;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private static final String BASE_URL = "https://moashrafff.free.beeceptor.com";

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }


    @Singleton
    @Provides
    FileInterface provideRetrofitAPI (){
         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(FileInterface.class);
    }
    @Provides
    @Singleton
    FileDao provideFileDao(){
        FileDao files_database = Room.databaseBuilder(mApplication.getApplicationContext(),
                FilesDataBase.class, "FILES_DATABASE")
                .build()
                .fileDao();
        return files_database;
    }

    @Provides
    @Singleton
    Repository provideRepository(FileDao fileDao,FileInterface fileInterface){
        return new Repository(fileDao,fileInterface);
    }
}
