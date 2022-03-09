package com.example.FileDownloader.Di;

import android.app.Application;

import com.example.FileDownloader.DataBase.remote.FileInterface;
import com.example.FileDownloader.DataBase.local.FileDao;
import com.example.FileDownloader.repository.Repository;
import com.example.FileDownloader.ui.MainActivity;

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
