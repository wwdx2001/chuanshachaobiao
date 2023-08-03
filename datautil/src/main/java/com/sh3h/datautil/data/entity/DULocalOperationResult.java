package com.sh3h.datautil.data.entity;


import java.util.List;

public class DULocalOperationResult extends DUResponse {
    private List<IDUEntity> duEntityList;

    public DULocalOperationResult() {
        duEntityList = null;
    }

    public List<IDUEntity> getDuEntityList() {
        return duEntityList;
    }

    public void setDuEntityList(List<IDUEntity> duEntityList) {
        this.duEntityList = duEntityList;
    }
}
