package com.sh3h.meterreading.service;


public class SyncSubDataInfo {
    public enum OperationType {
        NONE,
        MEDIA_INFO
    }

    public enum OperationResult {
        NONE,
        SUCCESS,
        FAILURE
    }

    private OperationType operationType;
    private String customerId;
    private String fileName;
    private OperationResult operationResult;

    public SyncSubDataInfo() {
        this.operationType = OperationType.NONE;
        this.customerId = null;
        this.fileName = null;
        this.operationResult = OperationResult.NONE;
    }

    public SyncSubDataInfo(OperationType operationType,
                           String customerId,
                           String fileName,
                           OperationResult operationResult) {
        this.operationType = operationType;
        this.customerId = customerId;
        this.fileName = fileName;
        this.operationResult = operationResult;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }
}
