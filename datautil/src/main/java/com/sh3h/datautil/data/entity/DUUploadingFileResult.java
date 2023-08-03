package com.sh3h.datautil.data.entity;


public class DUUploadingFileResult extends DUResponse {
    private boolean isSuccess;

    public DUUploadingFileResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
