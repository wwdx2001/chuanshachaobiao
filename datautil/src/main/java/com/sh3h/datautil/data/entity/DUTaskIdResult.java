package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUTaskIdResult extends DUResponse {
    private List<String> idList;

    public DUTaskIdResult() {

    }

    public DUTaskIdResult(List<String> idList) {
        this.idList = idList;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }
}
