package com.sh3h.datautil.data.entity;


public class DUCardCoordinateResult extends DUResponse {
    private DUCardCoordinateInfo duCardCoordinateInfo;
    private boolean isSuccess;

    public DUCardCoordinateResult(DUCardCoordinateInfo duCardCoordinateInfo,
                                  boolean isSuccess) {
        this.duCardCoordinateInfo = duCardCoordinateInfo;
        this.isSuccess = isSuccess;
    }

    public DUCardCoordinateInfo getDuCardCoordinateInfo() {
        return duCardCoordinateInfo;
    }

    public void setDuCardCoordinateInfo(DUCardCoordinateInfo duCardCoordinateInfo) {
        this.duCardCoordinateInfo = duCardCoordinateInfo;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
