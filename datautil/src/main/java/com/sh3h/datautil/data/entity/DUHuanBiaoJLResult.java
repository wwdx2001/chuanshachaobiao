package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUHuanBiaoJLResult extends DUResponse {
    private List<DUHuanBiaoJL> duHuanBiaoJLList;

    public DUHuanBiaoJLResult() {
        duHuanBiaoJLList = null;
    }

    public List<DUHuanBiaoJL> getDuHuanBiaoJLList() {
        return duHuanBiaoJLList;
    }

    public void setDuHuanBiaoJLList(List<DUHuanBiaoJL> duHuanBiaoJLList) {
        this.duHuanBiaoJLList = duHuanBiaoJLList;
    }
}
