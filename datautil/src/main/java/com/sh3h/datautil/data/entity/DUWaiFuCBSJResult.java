package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUWaiFuCBSJResult extends DUResponse {
    public enum FilterType {
        NONE,
        UPLOADING,
        UPDATING
    }

    private FilterType filterType;
    private int taskId;
    private String volume;
    private int successCount;
    private int failureCount;
    private List<DUWaiFuCBSJ> duWaiFuCBSJList;

    public int getCeNeiXH() {
        return ceNeiXH;
    }

    public void setCeNeiXH(int ceNeiXH) {
        this.ceNeiXH = ceNeiXH;
    }

    private  int ceNeiXH;

    public DUWaiFuCBSJResult() {
        filterType = FilterType.NONE;
        taskId = 0;
        volume = null;
        successCount = 0;
        failureCount = 0;
        duWaiFuCBSJList = null;
    }

    public DUWaiFuCBSJResult(FilterType filterType,
                          int taskId,
                          String volume,
                          int successCount,
                          int failureCount) {
        this();
        this.filterType = filterType;
        this.taskId = taskId;
        this.volume = volume;
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
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

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public List<DUWaiFuCBSJ> getDuWaiFuCBSJList() {
        return duWaiFuCBSJList;
    }

    public void setDuWaiFuCBSJList(List<DUWaiFuCBSJ> duWaiFuCBSJList) {
        this.duWaiFuCBSJList = duWaiFuCBSJList;
    }
}
