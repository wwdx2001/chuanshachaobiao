package com.sh3h.datautil.data.entity;

/**
 * Created by xulongjun on 2016/2/26.
 */
public class DUCheckVolume {
    boolean mIsChecked;
    String cid;

    public DUCheckVolume() {
    }

    public DUCheckVolume(boolean mIsChecked, String cid) {
        this.mIsChecked = mIsChecked;
        this.cid = cid;
    }

    public boolean ismIsChecked() {
        return mIsChecked;
    }

    public void setmIsChecked(boolean mIsChecked) {
        this.mIsChecked = mIsChecked;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
