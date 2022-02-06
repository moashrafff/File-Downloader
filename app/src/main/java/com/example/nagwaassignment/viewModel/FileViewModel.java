package com.example.nagwaassignment.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nagwaassignment.Pojo.FileModel;
import com.example.nagwaassignment.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FileViewModel extends ViewModel {


    @Inject
    Repository repository;

    private static final String TAG = "FileViewModel";
    private MutableLiveData<List<FileModel>> _downloadedFilesMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<FileModel>> files = new MutableLiveData<>();
    public MutableLiveData<List<FileModel>> downloadedFilesMutableLiveData = new MutableLiveData<>();


//
//    @Inject
//    public FileViewModel(Repository repository) {
//        this.repository = repository;
//    }

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

        Observable observable = repository.getNewFiles()
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

    public void injectRepo(Repository repository) {
        this.repository=repository;
    }
}
