package com.example.FileDownloader.Fragments;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FileDownloader.Di.AppComponent;
import com.example.FileDownloader.Di.AppModule;
import com.example.FileDownloader.Di.DaggerAppComponent;
import com.example.FileDownloader.viewModel.FileViewModel;
import com.example.FileDownloader.Adapters.FilesAdapter;
import com.example.FileDownloader.Pojo.FileModel;
import com.example.FileDownloader.Adapters.utils.RecyclerViewOnClickInterface;
import com.example.FileDownloader.databinding.FragmentFilesBinding;
import com.example.FileDownloader.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FilesFragment extends Fragment implements RecyclerViewOnClickInterface {

    FileViewModel viewModel;
    FilesAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private final String NOTIFICATION_TITLE = "Nagwa";
    FragmentFilesBinding binding;
    private static final String TAG = "FilesFragment";


    AppComponent build;



    public FilesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FilesFragment newInstance(String param1) {
        FilesFragment fragment = new FilesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        build = DaggerAppComponent.builder().appModule(new AppModule(getActivity().getApplication())).build();
        build.inject((MainActivity) requireActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFilesBinding.inflate(getLayoutInflater(), container, false);
        adapter = new FilesAdapter();
        adapter.setRecyclerViewOnClickInterface(this);
        binding.filesRecyclerView.setAdapter(adapter);
        binding.filesRecyclerView.setHasFixedSize(true);
        binding.filesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FileViewModel.class);
        viewModel.injectRepo(build.repository()
);

        viewModel.getFiles();
        getDownloadedFilesAndGetAdapter();

    }

    private void getDownloadedFilesAndGetAdapter() {
        viewModel.getDownloadedFiles();
        viewModel.files.observe(requireActivity(), new Observer<List<FileModel>>() {
            @Override
            public void onChanged(List<FileModel> newFileModels) {
                viewModel.downloadedFilesMutableLiveData.observe(requireActivity(), new Observer<List<FileModel>>() {
                    @Override
                    public void onChanged(List<FileModel> downloadedFileModels) {
                        Log.d(TAG, "onChanged: ");
                        List<FileModel> fileModels = compareBetweenDownloadedAndNewFiles(newFileModels, downloadedFileModels);
                        binding.filesRecyclerView.getRecycledViewPool().clear();
                        adapter.setFiles((ArrayList<FileModel>) fileModels);
                    }
                });

            }
        });
    }


    public void downLoad(String url, String outputFileName, String title, Context context, int position, ArrayList<FileModel> models) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        request.setDescription("Downloading .. " + outputFileName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, outputFileName);

        DownloadManager manager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        long downloadId = manager.enqueue(request);
        models.get(position).setDownloadingId((int) downloadId);
        new Thread(new Runnable() {

            @SuppressLint("Range")
            @Override
            public void run() {

                boolean downloading = true;

                while (downloading) {

                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadId);

                    Cursor cursor = manager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        models.get(position).setDownloadingProgress(100);
                        Log.d(TAG, "run: Complete");

                        downloading = false;
                        FileModel fileModel = models.get(position);
                        fileModel.setDownload(true);
                        viewModel.insertFile(fileModel);

                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getDownloadedFilesAndGetAdapter();
                            }
                        });

                    }

                    final int dl_progress = (int) ((bytes_downloaded * 100) / bytes_total);
                    Log.d(TAG, "run: " + dl_progress);
                    if (dl_progress >= 0) {
                        models.get(position).setDownloadingProgress(dl_progress);
                    }

                    adapter.setFiles(models, position);
                    binding.filesRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemChanged(position);
                        }
                    });
                    cursor.close();
                }

            }
        }).start();
    }

    @Override
    public void onItemClick(int position, ArrayList<FileModel> files) {
        FileModel clickedFile = files.get(position);
        downLoad(clickedFile.getUrl(), clickedFile.getName(), NOTIFICATION_TITLE, requireContext(), position, files);


    }

    private FileModel getFileModelByFileId(ArrayList<FileModel> models, int downloaderId) {
        for (FileModel fileModel : models
        ) {
            if (fileModel.getDownloadingId() == downloaderId) {
                return fileModel;
            }
        }
        return null;
    }

    private List<FileModel> compareBetweenDownloadedAndNewFiles(List<FileModel> newFiles, List<FileModel> downloadedFiles) {
        for (FileModel newFileModel : newFiles
        ) {
            for (FileModel downLoadedFileModel : downloadedFiles
            ) {
                if (newFileModel.getId() == downLoadedFileModel.getId()) {
                    newFileModel.setDownload(true);
                }
            }
        }
        return newFiles;
    }


}

