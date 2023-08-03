package com.sh3h.datautil.data.entity;


public class DUTaskIdInfo extends DURequest {
    private String account;
    private FilterType filterType;

    public enum FilterType {
        CHAOBIAO_TASK,
        SAMPLING_TASK
    }

    public DUTaskIdInfo() {

    }

    public DUTaskIdInfo(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }
}
