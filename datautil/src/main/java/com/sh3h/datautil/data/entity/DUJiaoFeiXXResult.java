package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUJiaoFeiXXResult extends DUResponse {
    private List<DUJiaoFeiXX> duJiaoFeiXXList;

    public DUJiaoFeiXXResult() {
        duJiaoFeiXXList = null;
    }

    public List<DUJiaoFeiXX> getDuJiaoFeiXXList() {
        return duJiaoFeiXXList;
    }

    public void setDuJiaoFeiXXList(List<DUJiaoFeiXX> duJiaoFeiXXList) {
        this.duJiaoFeiXXList = duJiaoFeiXXList;
    }
}
