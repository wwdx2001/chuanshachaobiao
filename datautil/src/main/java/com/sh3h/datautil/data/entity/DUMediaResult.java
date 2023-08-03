package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUMediaResult extends DUResponse {
    private List<DUMedia> duMediaList;

    public DUMediaResult() {
        duMediaList = null;
    }

    public List<DUMedia> getDuMediaList() {
        return duMediaList;
    }

    public void setDuMediaList(List<DUMedia> duMediaList) {
        this.duMediaList = duMediaList;
    }
}
