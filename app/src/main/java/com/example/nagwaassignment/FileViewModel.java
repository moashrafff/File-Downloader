package com.example.nagwaassignment;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.nagwaassignment.Data.FileClient;
import com.example.nagwaassignment.Pojo.FileModel;
import com.example.nagwaassignment.repository.Repository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FileViewModel extends AndroidViewModel {

    Repository repository;
    private static final String TAG = "FileViewModel";
    private MutableLiveData<List<FileModel>> _downloadedFilesMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<FileModel>> files = new MutableLiveData<>();
    public MutableLiveData<List<FileModel>> downloadedFilesMutableLiveData = new MutableLiveData<>();

    public FileViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void insertFile(FileModel fileModel) {
        repository.insertFile(fileModel);
    }

    public void getDownloadedFiles() {
        Observable<List<FileModel>> downloadedFiles = repository.getDownloadedFiles();

        downloadedFiles.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<FileModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<FileModel> fileModels) {
                        downloadedFilesMutableLiveData.setValue(fileModels);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getFiles() {

        Observable observable = FileClient.getINSTANCE().getFiles()
                .subscribeOn(Schedulers.io())
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
