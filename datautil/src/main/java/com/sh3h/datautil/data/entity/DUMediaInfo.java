package com.sh3h.datautil.data.entity;


public class DUMediaInfo extends DURequest {
    public enum OperationType {
        NONE,
        INSERT,
        UPDATE,
        SELECT,
        All,
        DELETE,
        NOT_UPLOADED,
        UPLOADED,
        UPLOADING,
        EACH_VOLUME,
        DELETE_ALL,
        MORE_ITEMS,
        MORE_ITEMS_WAIFU,
        MORE_ITEMS_DELAY
    }

    public enum MeterReadingType {
        NORMAL,
        DELAYING,
        ALL,
        WAIFU,
        RUSH_PAY,
        SAMPLING
    }

    public static final int LIMIT = 6;

    private OperationType operationType;
    private MeterReadingType meterReadingType;
    private DUMedia duMedia;
    private boolean isLocked;
    private String taskIdsArry;
    private int offset;
    private int limit;

    public DUMediaInfo() {
        operationType = OperationType.NONE;
        meterReadingType = MeterReadingType.NORMAL;
        duMedia = null;
        isLocked = false;
        taskIdsArry = null;
        offset = 0;
        limit = 0;
    }

    public DUMediaInfo(OperationType operationType,
                       MeterReadingType meterReadingType,
                       DUMedia duMedia) {
        this.operationType = operationType;
        this.meterReadingType = meterReadingType;
        this.duMedia = duMedia;
    }

    public DUMediaInfo(OperationType operationType,
                       MeterReadingType meterReadingType,
                       DUMedia duMedia,
                       boolean isLocked,
                       IDUHandler duHandler) {
        this.operationType = operationType;
        this.meterReadingType = meterReadingType;
        this.duMedia = duMedia;
        this.duHandler = duHandler;
        this.isLocked = isLocked;
    }

    public DUMediaInfo(OperationType operationType,
                       MeterReadingType meterReadingType,
                       int offset,
                       int limit,
                       DUMedia duMedia) {
        this.operationType = operationType;
        this.meterReadingType = meterReadingType;
        this.offset = offset;
        this.limit = limit;
        this.duMedia = duMedia;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public MeterReadingType getMeterReadingType() {
        return meterReadingType;
    }

    public void setMeterReadingType(MeterReadingType meterReadingType) {
        this.meterReadingType = meterReadingType;
    }

    public DUMedia getDuMedia() {
        return duMedia;
    }

    public void setDuMedia(DUMedia duMedia) {
        this.duMedia = duMedia;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getTaskIdsArry() {
        return taskIdsArry;
    }

    public void setTaskIdsArry(String taskIdsArry) {
        this.taskIdsArry = taskIdsArry;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
