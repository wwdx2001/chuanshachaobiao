package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUChaoBiaoJLResult extends DUResponse {
    private List<DUChaoBiaoJL> duChaoBiaoJLList;

    public DUChaoBiaoJLResult() {
        duChaoBiaoJLList = null;
    }

    public List<DUChaoBiaoJL> getDuChaoBiaoJLList() {
        return duChaoBiaoJLList;
    }

    public void setDuChaoBiaoJLList(List<DUChaoBiaoJL> duChaoBiaoJLList) {
        this.duChaoBiaoJLList = duChaoBiaoJLList;
    }
}
