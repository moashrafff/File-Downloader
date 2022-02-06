package com.example.nagwaassignment.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nagwaassignment.Adapters.DownloadedFilesAdapter;
import com.example.nagwaassignment.FileViewModel;
import com.example.nagwaassignment.Pojo.FileModel;
import com.example.nagwaassignment.R;
import com.example.nagwaassignment.databinding.FragmentDownloadedFilesBinding;

import java.util.ArrayList;
import java.util.List;

public class DownloadedFiles extends Fragment {

     FileViewModel viewModel;
     DownloadedFilesAdapter downloadedFilesAdapter;
    FragmentDownloadedFilesBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = "DownloadedFiles";


    // TODO: Rename and change types of parameters
    private String mParam1;


    public DownloadedFiles() {

    }


    public static DownloadedFiles newInstance(String param1) {
        DownloadedFiles fragment = new DownloadedFiles();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        viewModel = new ViewModelProvider(requireActivity()).get(FileViewModel.class);
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