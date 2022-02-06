package com.example.nagwaassignment.Pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FILE_TABLE")

public class FileModel {

    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private int id;
    private String type;
    private String url;
    private String name;
    private int downloadingProgress = 0;
    private int downloadingId = -1;
    private boolean isDownload = false;

    public FileModel(int uniqueId, int id, String type, String url, String name, int downloadingProgress, int downloadingId, boolean isDownload) {
        this.uniqueId = uniqueId;
        this.id = id;
        this.type = type;
        this.url = url;
        this.name = name;
        this.downloadingProgress = downloadingProgress;
        this.downloadingId = downloadingId;
        this.isDownload = isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public int getDownloadingProgress() {
        return downloadingProgress;
    }

    public void setDownloadingProgress(int downloadingProgress) {
        this.downloadingProgress = downloadingProgress;
    }

    public int getDownloadingId() {
        return downloadingId;
    }

    public void setDownloadingId(int downloadingId) {
        this.downloadingId = downloadingId;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "uniqueId=" + uniqueId +
                ", id=" + id +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", downloadingProgress=" + downloadingProgress +
                ", downloadingId=" + downloadingId +
                ", isDownload=" + isDownload +
                '}';
    }
}
