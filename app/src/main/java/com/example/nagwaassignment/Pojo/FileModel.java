package com.example.nagwaassignment.Pojo;

public class FileModel {
    private int id;
    private String type;
    private String url;
    private String name;
    private int downloadingProgress = 0;
    private int downloadingId = -1;

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

}
