package com.example.nagwaassignment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nagwaassignment.Data.FileClient;
import com.example.nagwaassignment.Pojo.FileModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FileViewModel extends ViewModel {

    private static final String TAG = "FileViewModel";
    public MutableLiveData<List<FileModel>> files = new MutableLiveData<>();

    public void getFiles() {

        Observable observable = FileClient.getINSTANCE().getFiles()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observer<List<FileModel>> observer = new Observer<List<FileModel>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<FileModel> postModels) {
                files.setValue(postModels);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {

            }
        };


        observable.subscribe(observer);
    }
}
