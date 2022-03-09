package com.example.FileDownloader.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FileDownloader.Adapters.DownloadedFilesAdapter;
import com.example.FileDownloader.Di.AppComponent;
import com.example.FileDownloader.Di.AppModule;
import com.example.FileDownloader.Di.DaggerAppComponent;
import com.example.FileDownloader.databinding.FragmentDownloadedFilesBinding;
import com.example.FileDownloader.viewModel.FileViewModel;
import com.example.FileDownloader.Pojo.FileModel;
import com.example.FileDownloader.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DownloadedFilesFragment extends Fragment {

     FileViewModel viewModel;
     DownloadedFilesAdapter downloadedFilesAdapter;
    FragmentDownloadedFilesBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = "DownloadedFiles";
    @Inject
    FileViewModel fileViewModel;
    AppComponent build;

    // TODO: Rename and change types of parameters
    private String mParam1;


    public DownloadedFilesFragment() {

    }


    public static DownloadedFilesFragment newInstance(String param1) {
        DownloadedFilesFragment fragment = new DownloadedFilesFragment();
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
        viewModel = ViewModelProviders.of(this).get(FileViewModel.class);

        viewModel.injectRepo(build.repository());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDownloadedFilesBinding.inflate(getLayoutInflater(),container,false);
        downloadedFilesAdapter = new DownloadedFilesAdapter();

        binding.downloadedFilesRecyclerView.setAdapter(downloadedFilesAdapter);
        binding.downloadedFilesRecyclerView.setHasFixedSize(true);
        binding.downloadedFilesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getDownloadedFiles();
        viewModel.downloadedFilesMutableLiveData.observe(requireActivity(), new Observer<List<FileModel>>() {
            @Override
            public void onChanged(List<FileModel> fileModels) {
                downloadedFilesAdapter.setFiles((ArrayList<FileModel>) fileModels);
                Log.d(TAG, "onChanged: "+fileModels.toString());
            }
        });

    }

}