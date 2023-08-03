package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUCardResult extends DUResponse {
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
    private List<DUCard> duCardList;

    public DUCardResult() {
        filterType = FilterType.NONE;
        taskId = 0;
        volume = null;
        successCount = 0;
        failureCount = 0;
        duCardList = null;
    }

    public DUCardResult(FilterType filterType,
                        String volume,
                        int successCount,
                        int failureCount) {
        this();
        this.filterType = filterType;
        this.volume = volume;
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public DUCardResult(FilterType filterType,
                        int taskId,
                        String volume,
                        List<DUCard> duCardList) {
        this();
        this.filterType = filterType;
        this.taskId = taskId;
        this.volume = volume;
        this.duCardList = duCardList;
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

    public List<DUCard> getDuCardList() {
        return duCardList;
    }

    public void setDuCardList(List<DUCard> duCardList) {
        this.duCardList = duCardList;
    }
}
