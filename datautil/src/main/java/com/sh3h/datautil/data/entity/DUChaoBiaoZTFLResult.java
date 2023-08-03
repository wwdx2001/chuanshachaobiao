package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUChaoBiaoZTFLResult extends DUResponse {
    private List<DUChaoBiaoZTFL> duChaoBiaoZTList;

    public DUChaoBiaoZTFLResult() {
        duChaoBiaoZTList = null;
    }

    public List<DUChaoBiaoZTFL> getDuChaoBiaoZTList() {
        return duChaoBiaoZTList;
    }

    public void setDuChaoBiaoZTList(List<DUChaoBiaoZTFL> duChaoBiaoZTList) {
        this.duChaoBiaoZTList = duChaoBiaoZTList;
    }
}
