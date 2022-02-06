package com.example.nagwaassignment.Di;

import com.example.nagwaassignment.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject (MainActivity mainActivity);
}
