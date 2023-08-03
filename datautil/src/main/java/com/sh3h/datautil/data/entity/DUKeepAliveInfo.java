package com.sh3h.datautil.data.entity;

/**
 * Created by xulongjun on 2016/3/15.
 */
public class DUKeepAliveInfo extends DURequest {
    private DUKeepAlive duKeepAlive;

    public DUKeepAliveInfo(DUKeepAlive duKeepAlive) {
        this.duKeepAlive = duKeepAlive;
    }

    public DUKeepAlive getDuKeepAlive() {
        return duKeepAlive;
    }

    public void setDuKeepAlive(DUKeepAlive duKeepAlive) {
        this.duKeepAlive = duKeepAlive;
    }
}
