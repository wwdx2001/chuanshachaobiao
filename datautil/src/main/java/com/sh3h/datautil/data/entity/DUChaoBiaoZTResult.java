package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUChaoBiaoZTResult extends DUResponse {
    private List<DUChaoBiaoZT> duChaoBiaoZTList;

    public DUChaoBiaoZTResult() {
        duChaoBiaoZTList = null;
    }

    public List<DUChaoBiaoZT> getDuChaoBiaoZTList() {
        return duChaoBiaoZTList;
    }

    public void setDuChaoBiaoZTList(List<DUChaoBiaoZT> duChaoBiaoZTList) {
        this.duChaoBiaoZTList = duChaoBiaoZTList;
    }
}
