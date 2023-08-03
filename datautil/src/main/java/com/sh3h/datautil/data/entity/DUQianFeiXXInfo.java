package com.sh3h.datautil.data.entity;


public class DUQianFeiXXInfo extends DURequest {
    public enum FilterType {
        ALL,
        ONE
    }

    private FilterType filterType;
    private String customerId;
    private String volume;
    private int month;

    public DUQianFeiXXInfo() {
        filterType = FilterType.ALL;
        customerId = null;
        volume = null;
        month = 1;
    }

    public DUQianFeiXXInfo(String volume,
                           int month) {
        this();
        this.volume = volume;
        this.month = month;
    }

    public DUQianFeiXXInfo(String volume, String customerId) {
        this();
        this.filterType = FilterType.ONE;
        this.volume = volume;
        this.customerId = customerId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
