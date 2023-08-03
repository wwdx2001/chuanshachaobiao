package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUFeiYongZKLResult extends DUResponse {
    private List<DUFeiYongZKL> feiYongZKLList;

    public DUFeiYongZKLResult() {
        feiYongZKLList = null;
    }

    public List<DUFeiYongZKL> getFeiYongZKLList() {
        return feiYongZKLList;
    }

    public void setFeiYongZKLList(List<DUFeiYongZKL> feiYongZKLList) {
        this.feiYongZKLList = feiYongZKLList;
    }
}
