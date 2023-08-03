package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUWaiFuCBSJInfo extends DURequest {
    public enum FilterType {
        NONE,
        SYNC_ALL,
        SYNC_ONE,
        SEARCH,
        SEARCH_ALL,
        SEARCH_ONE,
        UPDATE_ONE,
        FINISHING,
        UNFINISHING,
        PREVIOUS_ONE,
        PREVIOUS_ONE_NOT_READING,
        NEXT_ONE,
        NEXT_ONE_NOT_READING,
        ONE,
        NOT_UPLOAD,
        UPLOADING,
    }

    private FilterType filterType;
    private String account;
    private List<DUWaiFuCBSJ> duWaiFuCBSJList;
    private int taskId;
    private String key;
    private String volume;
    private String customerId;
    private long orderNumber;
    private String deviceId;



    public DUWaiFuCBSJInfo() {
        filterType = FilterType.NONE;
        account = null;
        duWaiFuCBSJList = null;
        key = null;
        deviceId = null;
    }

    public DUWaiFuCBSJInfo(FilterType filterType,
                           String account,
                           List<DUWaiFuCBSJ> duWaiFuCBSJList) {
        this.filterType = filterType;
        this.account = account;
        this.duWaiFuCBSJList = duWaiFuCBSJList;
    }

    public DUWaiFuCBSJInfo(FilterType filterType,
                           String account
                           ) {
        this.filterType = filterType;
        this.account = account;
    }

    public DUWaiFuCBSJInfo(FilterType filterType,
                           String key,
                           String account
    ) {
        this.filterType = filterType;
        this.key = key;
        this.account = account;
    }



    // for local
    public DUWaiFuCBSJInfo(FilterType filterType,
                        String account,
                        int taskId,
                        String volume,
                        String customerId,
                        int orderNumber) {
        this();
        this.account = account;
        this.filterType = filterType;
        this.taskId = taskId;
        this.volume = volume;
        this.customerId = customerId;
        this.orderNumber = orderNumber;
    }

    // for uploading one record
    public DUWaiFuCBSJInfo(String account,
                        int taskId,
                        String deviceId,
                        FilterType filterType,
                        List<DUWaiFuCBSJ> duWaiFuCBSJs) {
        this();
        this.account = account;
        this.taskId = taskId;
        this.deviceId = deviceId;
        this.filterType = filterType;
        this.duWaiFuCBSJList = duWaiFuCBSJs;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<DUWaiFuCBSJ> getDuWaiFuCBSJList() {
        return duWaiFuCBSJList;
    }

    public void setDuWaiFuCBSJList(List<DUWaiFuCBSJ> duWaiFuCBSJList) {
        this.duWaiFuCBSJList = duWaiFuCBSJList;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
