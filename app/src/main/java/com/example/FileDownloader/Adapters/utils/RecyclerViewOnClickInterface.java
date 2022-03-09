package com.example.FileDownloader.Adapters.utils;

import com.example.FileDownloader.Pojo.FileModel;

import java.util.ArrayList;

public interface RecyclerViewOnClickInterface {
    void onItemClick(int position, ArrayList<FileModel> files);
}
