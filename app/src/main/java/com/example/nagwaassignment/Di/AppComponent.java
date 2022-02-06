package com.example.nagwaassignment.Di;

import android.app.Application;

import com.example.nagwaassignment.DataBase.remote.FileInterface;
import com.example.nagwaassignment.DataBase.local.FileDao;
import com.example.nagwaassignment.repository.Repository;
import com.example.nagwaassignment.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject (MainActivity mainActivity);
    Application application();
    FileDao fileDao();
    FileInterface fileInterface();
    Repository repository();

}
