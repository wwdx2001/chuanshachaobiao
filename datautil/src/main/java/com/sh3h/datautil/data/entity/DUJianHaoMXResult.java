package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUJianHaoMXResult extends DUResponse {
    private List<DUJianHaoMX> duJianHaoMXList;

    public DUJianHaoMXResult() {
        this.duJianHaoMXList = null;
    }

    public List<DUJianHaoMX> getDuJianHaoMXList() {
        return duJianHaoMXList;
    }

    public void setDuJianHaoMXList(List<DUJianHaoMX> duJianHaoMXList) {
        this.duJianHaoMXList = duJianHaoMXList;
    }
}
