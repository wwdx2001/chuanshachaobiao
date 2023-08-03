package com.sh3h.datautil.data.entity;

/**
 * Created by xulongjun on 2016/3/15.
 */
public class DUKeepAliveResult extends DUResponse {
    private long time;

    public DUKeepAliveResult() {
    }

    public DUKeepAliveResult(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
