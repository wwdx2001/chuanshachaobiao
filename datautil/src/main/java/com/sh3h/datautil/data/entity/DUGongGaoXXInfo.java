package com.sh3h.datautil.data.entity;


public class DUGongGaoXXInfo extends DURequest {
    public enum FilterType {
        NONE,
        SELECT_ALL,
        SELECT_ONE
    }

    private FilterType filterType;
    private String account;
    private String deviceId;
    private int newsId;

    public DUGongGaoXXInfo(String account, int i, Object o, IDUHandler iduHandler) {
        this.filterType = FilterType.NONE;
        this.account = null;
        this.deviceId = null;
        this.newsId = 0;
    }

    public DUGongGaoXXInfo(String account,
                           String deviceId,
                           IDUHandler duHandler) {
        this.filterType = FilterType.NONE;
        this.account = account;
        this.deviceId = deviceId;
        this.duHandler = duHandler;
        this.newsId = 0;
    }

    public DUGongGaoXXInfo(FilterType filterType,
                           String account,
                           String deviceId,
                           IDUHandler duHandler) {
        this.filterType = filterType;
        this.account = account;
        this.deviceId = deviceId;
        this.duHandler = duHandler;
        this.newsId = 0;
    }

    public DUGongGaoXXInfo(FilterType filterType,
                           int newsId, // 0 when file type is SELECT_ONE
                           IDUHandler duHandler) {
        this.filterType = filterType;
        this.newsId = newsId;
        this.account = null;
        this.deviceId = null;
        this.duHandler = duHandler;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
