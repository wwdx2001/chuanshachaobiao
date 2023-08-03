package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUGongGaoXXResult extends DUResponse {
    private List<DUGongGaoXX> duGongGaoXXList;

    public DUGongGaoXXResult() {
        duGongGaoXXList = null;
    }

    public List<DUGongGaoXX> getDuGongGaoXXList() {
        return duGongGaoXXList;
    }

    public void setDuGongGaoXXList(List<DUGongGaoXX> duGongGaoXXList) {
        this.duGongGaoXXList = duGongGaoXXList;
    }
}
