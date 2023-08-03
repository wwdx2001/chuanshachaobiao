package com.sh3h.meterreading.service;


import java.util.List;

public class SyncDataInfo {
    public enum OperationType {
        NONE("none"),
        DOWNLOADING_TASKS("downloading_tasks"),
        DOWNLOADING_SAMPLING_TASKS("downloading_sampling_tasks"),
        DOWNLOADING_ALL_DATA_OF_ONE_TASK("downloading_all_data_of_one_task"),
        DOWNLOADING_ALL_DATA_OF_ALL_TASK("downloading_all_data_of_all_task"),
        DOWNLOADING_ALL_DATA_OF_ALL_SAMPING_TASK("downloading_all_data_of_all_sampling_task"),
        DOWNLOADING_ALL_DATA_OF_ALL_WAIFU("downloading_all_data_of_all_waifu"),
        DOWNLOADING_CARDS("downloading_cards"),
        DOWNLOADING_RECORDS("downloading_records"),
        DOWNLOADING_PRERECORDS("downloading_prerecords"),
        DOWNLOADING_PAYMENTS("downloading_payments"),
        DOWNLOADING_ARREARAGES("downloading_arrearages"),
        DOWNLOADING_REPLACEMENTS("downloading_replacements"),
        DOWNLOADING_TEMPORARY("downloading_temporary"),
        DOWNLOADING_CARD("downloading_card"),
        DOWNLOADING_RUSHPAYTASK("downloading_RushPayTasks"),

        DOWNLOADING_DELAY_ALL("downloading_delay_all"),

        UPLOADING_WAIFU_MEDIAS("uploading_waifu_medias"),

        UPLOADING_SINGLE_RECORD("uploading_single_record"),
        UPLOADING_VOLUME_RECORDS("uploading_volume_records"),
        UPLOADING_DELAY_RECORDS("uploading_delay_records"),
        UPLOADING_SINGLE_MEDIAS("uploading_single_medias"),
        UPLOADING_SINGLE_SAMPLING_MEDIAS("uploading_single_sampling_medias"),
        UPLOADING_VOLUME_MEDIAS("uploading_volume_medias"),
        UPLOADING_DELAY_MEDIAS("uploading_delay_medias"),
        UPLOADING_SAMPLING_VOLUME_MEDIAS("uploading_sampling_volume_medias"),
        UPLOADING_MEDIA_INFO("uploading_media_info"),
        UPLOADING_VOLUME_CARDS("uploading_volume_cards"),
        UPLOADING_DELAY_CARDS("uploading_delay_cards"),
        UPLOADING_VOlUME_SAMPLINGS("uploading_volume_samplings"),
        UPLOADING_SINGLE_SAMPLING("uploading_single_sampling"),
        SYNC_WAIFUCBSJS("sync_waifucbsjs"),
        UPLOADING_RUSH_PAY_TASKS("uploading_rush_pay_tasks"),
        UPLOADING_WAIFUCBSJS("uploading_waifucbsjs"),
        UPLOADING_RUSH_PAY_TASK_MEDIAS("uploading_rush_pay_task_medias"),
        UPLOADING_SINGLE_RUSH_PAY_TASK("uploading_single_rush_pay_task");





        private String name;

        private OperationType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum OperationResult {
        NONE,
        SUCCESS,
        FAILURE
    }

    private OperationType operationType;
    private int taskId;
    private String volume;
    private String customerId;
    private OperationResult operationResult;
    private List<SyncSubDataInfo> syncSubDataInfoList;
    private int successCount;
    private int failureCount;

    public SyncDataInfo() {
        this.operationType = OperationType.NONE;
        this.taskId = 0;
        this.volume = null;
        this.customerId = null;
        this.operationResult = OperationResult.NONE;
        this.syncSubDataInfoList = null;
        this.successCount = 0;
        this.failureCount = 0;
    }

    public SyncDataInfo(OperationType operationType,
                        int taskId,
                        String volume,
                        String customerId,
                        OperationResult operationResult) {
        this();
        this.operationType = operationType;
        this.taskId = taskId;
        this.volume = volume;
        this.customerId = customerId;
        this.operationResult = operationResult;
    }

    public SyncDataInfo(OperationType operationType,
                        int successCount,
                        OperationResult operationResult) {
        this();
        this.operationType = operationType;
        this.successCount = successCount;
        this.operationResult = operationResult;
    }



    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
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

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    public List<SyncSubDataInfo> getSyncSubDataInfoList() {
        return syncSubDataInfoList;
    }

    public void setSyncSubDataInfoList(List<SyncSubDataInfo> syncSubDataInfoList) {
        this.syncSubDataInfoList = syncSubDataInfoList;
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
}
