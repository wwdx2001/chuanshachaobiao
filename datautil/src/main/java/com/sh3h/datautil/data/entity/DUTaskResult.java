package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUTaskResult extends DUResponse {
    private List<DUTask> duTaskList;

    public DUTaskResult() {
        duTaskList = null;
    }

    public List<DUTask> getDUTaskList() {
        return duTaskList;
    }

    public void setDUTaskList(List<DUTask> duTaskList) {
        this.duTaskList = duTaskList;
    }
}
