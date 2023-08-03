package com.sh3h.datautil.data.entity;

import java.util.List;

/**
 * Created by zhangzhe on 2015/12/1.
 */
public class DUChaoBiaoGJResult extends DUResponse {

    private List<DUChaoBiaoGJ> duChaoBiaoGJList;

    public DUChaoBiaoGJResult() {
        this.duChaoBiaoGJList = null;
    }

    public List<DUChaoBiaoGJ> getDuChaoBiaoGJList() {
        return duChaoBiaoGJList;
    }

    public void setDuChaoBiaoGJList(List<DUChaoBiaoGJ> duChaoBiaoGJList) {
        this.duChaoBiaoGJList = duChaoBiaoGJList;
    }
}
