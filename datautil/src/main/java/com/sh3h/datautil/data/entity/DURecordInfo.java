package com.sh3h.datautil.data.entity;


import java.util.List;

public class DURecordInfo extends DURequest {
    public enum FilterType {
        NONE,
        ALL,
        NORMAL,
        UNNORMAL,
        SEARCH,
        ONE,
        PREVIOUS_ONE,
        PREVIOUS_ONE_NOT_READING,
        NEXT_UNFINISHED_ONE_WITH_CENEIXH,
        NEXT_ONE,
        NEXT_ONE_NOT_READING,
        UPDATING_ALL,
        UPDATING_ONE,
        NOT_UPLOADED,
        EACH_VOLUME,
        UPLOADING,
        RE_UPDATING_ALL,
        ALL_NO_CONDITION,
        DELETE_ALL,
        DELETE_ONE,
        FINISH,
        UNFINISH,

//        ALL_LIMIT,                  //ALL_LIMIT，UNFINISHING_LIMIT，FINISHING_LIMIT，SEARCH_LIMIT用于分页显示数据
//        UNFINISHING_LIMIT,
//        FINISHING_LIMIT,
//        SEARCH_LIMIT,
//        OLDDATA,
//        NEWDATA,
        TEMPOPRARYDATA,
        NOT_UPLOADED_SAMPLINGDATA,
        UPLOADING_SAMPLINGS,
        ARREAR,
        LOW_INCOME
    }

    private String account;
    private int taskId;
    private String volume;
    private FilterType filterType;
    private String key;
    private String customerId;
    private long orderNumber;

    private List<DURecord> duRecordList;
    private String deviceId;
    private boolean isLocked;
    private boolean isAdjustingSequence;

    private String taskIdsArr;
    private double waterHighN;

    public DURecordInfo() {
        account = null;
        taskId = 0;
        volume = null;
        filterType = FilterType.NONE;
        key = null;
        customerId = null;
        orderNumber = 0;
        duRecordList = null;
        deviceId = null;
        isLocked = false;
        isAdjustingSequence = false;
        taskIdsArr = null;
    }

//    public DURecordInfo(FilterType filterType,
//                         String account,
//                         String taskIdsArr) {
//        this.filterType = filterType;
//        this.account = account;
//        this.taskIdsArr = taskIdsArr;
//        this.duHandler = null;
//    }

    public DURecordInfo(FilterType filterType) {
        this.filterType = filterType;
        this.duHandler = null;
    }

    // for server
    public DURecordInfo(String account) {
        this();
        this.account = account;
    }

    public DURecordInfo(int taskId, String volume) {
        this();
        this.taskId = taskId;
        this.volume = volume;
    }

    // for local
//    public DURecordInfo(String account,
//                        int taskId,
//                        String volume,
//                        String customerId,
//                        FilterType filterType) {
//        this.account = account;
//        this.taskId = taskId;
//        this.volume = volume;
//        this.filterType = filterType;
//        this.duHandler = null;
//        this.customerId = customerId;
//    }

    // for local
    public DURecordInfo(FilterType filterType,
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

    // for local
    public DURecordInfo(String account,
                        int taskId,
                        String volume,
                        FilterType filterType) {
        this();
        this.account = account;
        this.taskId = taskId;
        this.volume = volume;
        this.filterType = filterType;
    }

    public DURecordInfo(FilterType filterType,
                        String account,
                        int taskId,
                        String volume,
                        List<DURecord> duRecordList) {
        this();
        this.account = account;
        this.taskId = taskId;
        this.volume = volume;
        this.filterType = filterType;
        this.duRecordList = duRecordList;
    }

    // for local of searching
    public DURecordInfo(FilterType filterType,
                        String account,
                        int taskId,
                        String volume,
                        String key,
                        long orderNumber) {
        this();
        this.taskId = taskId;
        this.account = account;
        this.volume = volume;
        this.filterType = filterType;
        this.key = key;
        this.isAdjustingSequence = false;
        this.orderNumber = orderNumber;
    }

    // for local of searching
//    public DURecordInfo(String account,
//                        int taskId,
//                        String volume,
//                        FilterType filterType,
//                        String key) {
//        this.account = account;
//        this.taskId = taskId;
//        this.volume = volume;
//        this.filterType = filterType;
//        this.key = key;
//        this.duHandler = null;
//        this.customerId = null;
//        this.orderNumber = 0;
//        this.duRecordList = null;
//        this.deviceId = null;
//        this.isLocked = false;
//        this.isAdjustingSequence = false;
//    }

    // for one record
//    public DURecordInfo(String account,
//                        int taskId,
//                        String volume,
//                        FilterType filterType,
//                        String customerId,
//                        long orderNumber) {
//        this.account = account;
//        this.taskId = taskId;
//        this.volume = volume;
//        this.filterType = filterType;
//        this.key = null;
//        this.customerId = customerId;
//        this.orderNumber = orderNumber;
//        this.duHandler = null;
//        this.duRecordList = null;
//        this.deviceId = null;
//        this.isLocked = false;
//        this.isAdjustingSequence = false;
//    }

    // for updating records
//    public DURecordInfo(FilterType filterType,
//                        List<DURecord> duRecordList,
//                        boolean isAdjustingSequence) {
//        this.filterType = filterType;
//        this.duRecordList = duRecordList;
//        this.duHandler = null;
//        this.deviceId = null;
//        this.isLocked = false;
//        this.isAdjustingSequence = isAdjustingSequence;
//    }

    // for uploading one record
    public DURecordInfo(String account,
                        int taskId,
                        String deviceId,
                        FilterType filterType,
                        List<DURecord> duRecordList) {
        this();
        this.account = account;
        this.taskId = taskId;
        this.deviceId = deviceId;
        this.filterType = filterType;
        this.duRecordList = duRecordList;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public List<DURecord> getDuRecordList() {
        return duRecordList;
    }

    public void setDuRecordList(List<DURecord> duRecordList) {
        this.duRecordList = duRecordList;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isAdjustingSequence() {
        return isAdjustingSequence;
    }

    public void setIsAdjustingSequence(boolean isAdjustingSequence) {
        this.isAdjustingSequence = isAdjustingSequence;
    }

    public String getTaskIdsArr() {
        return taskIdsArr;
    }

    public void setTaskIdsArr(String taskIdsArr) {
        this.taskIdsArr = taskIdsArr;
    }

    public double getWaterHighN() {
        return waterHighN;
    }

    public void setWaterHighN(double waterHighN) {
        this.waterHighN = waterHighN;
    }
}
