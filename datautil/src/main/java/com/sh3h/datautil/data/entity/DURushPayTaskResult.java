package com.sh3h.datautil.data.entity;


import java.util.List;

public class DURushPayTaskResult extends DUResponse {

    private FilterType filterType;
    private int taskId;
    private int successCount;
    private int failureCount;
    private String cid;
    private List<DURushPayTask> duRushPayTaskList;
    private List<DURushPayTaskBackResult> rushPayTaskBackResults;

    public class DURushPayTaskBackResult {
        private int taskId;
        private boolean isSuccess;
        private String message;

        public DURushPayTaskBackResult(int taskId, boolean isSuccess, String message) {
            this.taskId = taskId;
            this.isSuccess = isSuccess;
            this.message = message;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public enum FilterType {
        NONE,
        UPLOADING,
        UPDATING,
        NOT_UPLOAD
    }


    public DURushPayTaskResult() {
        filterType = FilterType.NONE;
        taskId = 0;
        successCount = 0;
        failureCount = 0;
        duRushPayTaskList = null;
    }

    public DURushPayTaskResult(FilterType filterType,
                               int successCount,
                               int failureCount) {
        this();
        this.filterType = filterType;
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public DURushPayTaskResult(FilterType filterType,
                               List<DURushPayTask> duRushPayTaskList,
                               int successCount,
                               int failureCount) {
        this();
        this.filterType = filterType;
        this.duRushPayTaskList = duRushPayTaskList;
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

    public List<DURushPayTask> getDURushPayTaskList() {
        return duRushPayTaskList;
    }

    public void setDURushPayTaskList(List<DURushPayTask> duRushPayTaskList) {
        this.duRushPayTaskList = duRushPayTaskList;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<DURushPayTaskBackResult> getRushPayTaskBackResults() {
        return rushPayTaskBackResults;
    }

    public void setRushPayTaskBackResults(List<DURushPayTaskBackResult> rushPayTaskBackResults) {
        this.rushPayTaskBackResults = rushPayTaskBackResults;
    }
}
