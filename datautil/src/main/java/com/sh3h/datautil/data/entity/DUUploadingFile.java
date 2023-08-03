package com.sh3h.datautil.data.entity;


public class DUUploadingFile extends DURequest {
    public enum FileType {
        ZIP,
        DB,
        PNG,
        LOG
    }

    private FileType fileType;
    private String account;
    private String path;

    public DUUploadingFile(FileType fileType, String account, String path) {
        this.fileType = fileType;
        this.account = account;
        this.path = path;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
