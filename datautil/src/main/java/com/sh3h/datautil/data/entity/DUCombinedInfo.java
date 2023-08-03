package com.sh3h.datautil.data.entity;

import java.util.List;

/**
 * Created by zhangzhe on 2015/10/14.
 */
public class DUCombinedInfo extends DURequest{

    private DUCombined duCombined;
    private String account;
    private List<String> mCHList;

    public DUCombinedInfo() {
    }

    public DUCombinedInfo(DUCombined duCombined, String account, List<String> mCHList) {
        this.duCombined = duCombined;
        this.account = account;
        this.mCHList = mCHList;
    }

    public DUCombined getDuCombined() {
        return duCombined;
    }

    public void setDuCombined(DUCombined duCombined) {
        this.duCombined = duCombined;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public IDUHandler getDuHandler() {
        return duHandler;
    }

    @Override
    public void setDuHandler(IDUHandler duHandler) {
        this.duHandler = duHandler;
    }

    public List<String> getmCHList() {
        return mCHList;
    }

    public void setmCHList(List<String> mCHList) {
        this.mCHList = mCHList;
    }
}
