package com.sh3h.datautil.data.entity;


import java.util.List;

public class DURushPayTaskInfo extends DURequest {
    public enum FilterType {
        NONE,
        ALL,
        NOT_UPLOAD,
        DELETE,
        ONE,
        PREVIOUS_ONE,
        NEXT_ONE
    }

    private FilterType filterType;
    private String account;
    private int taskId;
    private String cardId;
    private List<DURushPayTask> duRushPayTaskList;

    public DURushPayTaskInfo() {
        filterType = FilterType.ALL;
        account = null;
    }

    public DURushPayTaskInfo(String account,FilterType filterType) {
        this.account = account;
        this.filterType = filterType;
    }

    public DURushPayTaskInfo(String account,int taskId,FilterType filterType) {
        this.account = account;
        this.taskId = taskId;
        this.filterType = filterType;
    }

    public DURushPayTaskInfo(FilterType filterType,String account,int taskId) {
        this.filterType = filterType;
        this.account = account;
        this.taskId = taskId;
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

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public List<DURushPayTask> getDuRushPayTaskList() {
        return duRushPayTaskList;
    }

    public void setDuRushPayTaskList(List<DURushPayTask> duRushPayTaskList) {
        this.duRushPayTaskList = duRushPayTaskList;
    }
}
