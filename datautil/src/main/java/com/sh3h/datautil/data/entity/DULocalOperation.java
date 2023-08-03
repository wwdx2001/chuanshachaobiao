package com.sh3h.datautil.data.entity;


import java.util.List;

public class DULocalOperation extends DURequest {
    public enum LocalOperation {
        NONE,
        INSERT,
        SELECT,
        UPDATE,
        DELETE
    }

    private LocalOperation localOperation;
    private List<IDUEntity> duEntityList;

    public DULocalOperation() {
        localOperation = LocalOperation.NONE;
        duEntityList = null;
    }

    public LocalOperation getLocalOperation() {
        return localOperation;
    }

    public void setLocalOperation(LocalOperation localOperation) {
        this.localOperation = localOperation;
    }

    public List<IDUEntity> getDuEntityList() {
        return duEntityList;
    }

    public void setDuEntityList(List<IDUEntity> duEntityList) {
        this.duEntityList = duEntityList;
    }
}
