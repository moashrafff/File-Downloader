package com.example.nagwaassignment.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nagwaassignment.Pojo.FileModel;
import com.example.nagwaassignment.R;
import com.example.nagwaassignment.databinding.FileItemBinding;

import java.util.ArrayList;

public class DownloadedFilesAdapter extends RecyclerView.Adapter<DownloadedFilesAdapter.DownloadedFilesHolder> {

    ArrayList<FileModel> files = new ArrayList<>();
    private static final String TAG = "DownloadedFilesAdapter";

    public void setFiles(ArrayList<FileModel> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DownloadedFilesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DownloadedFilesHolder holder = new DownloadedFilesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadedFilesHolder holder, int position) {
        FileModel model = files.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class DownloadedFilesHolder extends RecyclerView.ViewHolder{

        FileItemBinding binding ;
        FileModel model;
        public DownloadedFilesHolder(@NonNull View itemView) {
            super(itemView);
            binding = FileItemBinding.bind(itemView);
        }
        public void bind(FileModel model){
            this.model = model ;
            binding.nameTv.setText(model.getName());
            binding.typeTv.setText(model.getType());
            binding.progressBar.setVisibility(View.GONE);
            binding.imageView5.setVisibility(View.GONE);
            binding.itemIcon.setImageResource(R.drawable.nagwa_icon);
            binding.downloadingState.setVisibility(View.GONE);

            Log.d(TAG, "bind: "+model.toString());
        }
    }
}
