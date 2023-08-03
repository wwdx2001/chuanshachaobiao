package com.sh3h.datautil.data.entity;


import java.util.List;

public class DUDelayRecordInfo extends DURequest {
    public enum FilterType {
        NONE,
        ALL,
        UNFINISHING,
        FINISHING,
        HIGHAMOUNT,
        LOWAMOUNT,
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
        TEMPOPRARYDATA,
        NOT_UPLOADED_SAMPLINGDATA,
        UPLOADING_SAMPLINGS,
        ARREAR,
        LOW_INCOME
    }

    private String account;
    private FilterType filterType;
    private int renWuId;
    private String ch;
    private String customerId;
    private long ceNeiXH;
    private String deviceId;
    private double waterHighN;
    private List<DUDelayRecord> delayRecords;

    public DUDelayRecordInfo() {
        account = null;
        filterType = FilterType.NONE;
        ceNeiXH = 0;
        deviceId = null;
    }

    public DUDelayRecordInfo(FilterType filterType, String account) {
        this.account = account;
        this.filterType = filterType;
    }

    public DUDelayRecordInfo(FilterType filterType, String account, List<DUDelayRecord> delayRecords) {
        this.account = account;
        this.filterType = filterType;
        this.delayRecords = delayRecords;
        this.deviceId = account;
    }

    public DUDelayRecordInfo(FilterType filterType, String account, int renWuId,
                             String customerId, long orderNumber) {
        this.filterType = filterType;
        this.account = account;
        this.renWuId = renWuId;
        this.customerId = customerId;
        this.ceNeiXH = orderNumber;
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

    public int getRenWuId() {
        return renWuId;
    }

    public void setRenWuId(int renWuId) {
        this.renWuId = renWuId;
    }

    public long getCeNeiXH() {
        return ceNeiXH;
    }

    public void setCeNeiXH(long ceNeiXH) {
        this.ceNeiXH = ceNeiXH;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getWaterHighN() {
        return waterHighN;
    }

    public void setWaterHighN(double waterHighN) {
        this.waterHighN = waterHighN;
    }

    public List<DUDelayRecord> getDelayRecords() {
        return delayRecords;
    }

    public void setDelayRecords(List<DUDelayRecord> delayRecords) {
        this.delayRecords = delayRecords;
    }
}
