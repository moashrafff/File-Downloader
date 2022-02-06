package com.example.nagwaassignment.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nagwaassignment.Pojo.FileModel;
import com.example.nagwaassignment.R;
import com.example.nagwaassignment.Adapters.utils.RecyclerViewOnClickInterface;
import com.example.nagwaassignment.databinding.FileItemBinding;

import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FilesHolder> {

    ArrayList<FileModel> files = new ArrayList<>();
    private RecyclerViewOnClickInterface recyclerViewOnClickInterface;
    private static final String TAG = "FilesAdapter";

    public void setRecyclerViewOnClickInterface(RecyclerViewOnClickInterface recyclerViewOnClickInterface) {
        this.recyclerViewOnClickInterface = recyclerViewOnClickInterface;
    }

    public void setFiles(ArrayList<FileModel> files) {
        this.files = files;
        notifyDataSetChanged();
    }
    public void setFiles(ArrayList<FileModel> files, int position) {
        this.files = files;

    }

    @NonNull
    @Override
    public FilesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilesHolder holder = new FilesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilesHolder holder, int position) {
        FileModel model = files.get(position);
        holder.bind(model,files);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class FilesHolder extends RecyclerView.ViewHolder{
        FileItemBinding binding;
        FileModel model ;
        public FilesHolder(@NonNull View itemView) {
            super(itemView);
            binding = FileItemBinding.bind(itemView);
        }
        public void bind(FileModel model, ArrayList<FileModel> files){
            this.model = model ;
            binding.fileNameTextView.setText(model.getName());
            binding.fileTypeTextView.setText(model.getType());
            binding.downloadingProgressBar.setMax(100);
            binding.downloadingProgressBar.setIndeterminate(false);
            binding.itemIcon.setImageResource(R.drawable.file_icon);
            binding.downloadingProgressBar.setProgress(model.getDownloadingProgress());

            if (model.isDownload() ){
                binding.downloadStateIcon.setVisibility(View.VISIBLE);
                binding.downloadImageView.setVisibility(View.GONE);
                binding.downloadingProgressBar.setVisibility(View.GONE);
            }else {
                binding.downloadStateIcon.setVisibility(View.GONE);
                binding.downloadImageView.setVisibility(View.VISIBLE);
            }

            Log.d(TAG, "bind: "+model.toString());

            binding.downloadImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewOnClickInterface.onItemClick(getAdapterPosition(), files);
                }
            });
        }
    }

}
