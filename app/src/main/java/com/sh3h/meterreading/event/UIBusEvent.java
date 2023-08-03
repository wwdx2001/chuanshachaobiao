package com.sh3h.meterreading.event;


import com.sh3h.datautil.data.entity.DUTask;
import com.sh3h.meterreading.service.SyncDataInfo;
import com.sh3h.meterreading.service.SyncType;

import java.util.List;
import java.util.Map;

public class UIBusEvent {
    public static class VersionServiceStart {
    }

    public static class VersionServiceEnd {
        private boolean isNewApk;

        public VersionServiceEnd(boolean isNewApk) {
            this.isNewApk = isNewApk;
        }

        public boolean isNewApk() {
            return isNewApk;
        }
    }

    public static class ServiceIsBusy {
        private String type;

        public ServiceIsBusy(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    // external event
    public static class SyncDataStart {
        private SyncType syncType;

        public SyncDataStart(SyncType syncType) {
            this.syncType = syncType;
        }

        public SyncType getSyncType() {
            return syncType;
        }

        public void setSyncType(SyncType syncType) {
            this.syncType = syncType;
        }
    }

//    public static class SyncDataResult {
//        public enum Type {
//            RECORD,
//            MEDIA
//        }
//
//        private Type type;
//        private boolean isSuccess;
//        private String error;
//
//        public SyncDataResult(Type type, boolean isSuccess, String error) {
//            this.type = type;
//            this.isSuccess = isSuccess;
//            this.error = error;
//        }
//
//        public Type getType() {
//            return type;
//        }
//
//        public void setType(Type type) {
//            this.type = type;
//        }
//
//        public boolean isSuccess() {
//            return isSuccess;
//        }
//
//        public void setSuccess(boolean success) {
//            isSuccess = success;
//        }
//
//        public String getError() {
//            return error;
//        }
//
//        public void setError(String error) {
//            this.error = error;
//        }
//    }

    // internal event
    public static class UploadingDataEndInternal {
    }

    // internal event
    public static class UploadingCardsEndInternal {
    }

    // internal event
    public static class UploadingMediasEndInternal {
    }

    // internal event
    public static class UploadingDelayMediasEndInternal {
        private boolean result;

        public UploadingDelayMediasEndInternal(boolean result) {
            this.result = result;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }

    public static class UpdateRushPayTask {
    }

    // internal event
    public static class UploadingMoreMediasEndInternal {
        private int taskId;
        private String volume;

        public UploadingMoreMediasEndInternal() {
        }

        public UploadingMoreMediasEndInternal(int taskId, String volume) {
            this.taskId = taskId;
            this.volume = volume;
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
    }

    // external event
    public static class SyncDataEnd {
        private SyncType syncType;
        private Map<String, List<SyncDataInfo>> syncDataInfoMap;

        public SyncDataEnd(SyncType syncType,
                           Map<String, List<SyncDataInfo>> syncDataInfoMap) {
            this.syncType = syncType;
            this.syncDataInfoMap = syncDataInfoMap;
        }

        public SyncType getSyncType() {
            return syncType;
        }

        public void setSyncType(SyncType syncType) {
            this.syncType = syncType;
        }

        public Map<String, List<SyncDataInfo>> getSyncDataInfoMap() {
            return syncDataInfoMap;
        }

        public void setSyncDataInfoMap(Map<String, List<SyncDataInfo>> syncDataInfoMap) {
            this.syncDataInfoMap = syncDataInfoMap;
        }
    }

    public static class UpdateTaskList {
    }

    public static class UpdateVolumeList {
    }

    public static class ExitingSystem {
    }

    public static class UpdateSamplingList {
    }
    public static class UpdateSamplingTask {
    }

    public static class StatisticDataFinish {
        private DUTask duTask;

        public StatisticDataFinish(DUTask duTask) {
            this.duTask = duTask;
        }

        public DUTask getDuTask() {
            return duTask;
        }
    }

    public static class UpdateRecord {
        private String customerId;
        private int orderNumber;

        public UpdateRecord(String customerId, int orderNumber) {
            this.customerId = customerId;
            this.orderNumber = orderNumber;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public int getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(int orderNumber) {
            this.orderNumber = orderNumber;
        }
    }

    public static class QianFeiInfo {
        private boolean isQianFei;
        private String cid;

        public QianFeiInfo(boolean isQianFei, String cid) {
            this.isQianFei = isQianFei;
            this.cid = cid;
        }

        public String getCid() {
            return cid;
        }

        public boolean isQianFei() {
            return isQianFei;
        }
    }

    public static class UploadCardCoordinateResult {
        private int taskId;
        private String volume;
        private String customerId;
        private boolean isSuccess;
        private double longitude;
        private double latitude;

        public UploadCardCoordinateResult(int taskId,
                                          String volume,
                                          String customerId,
                                          boolean isSuccess,
                                          double longitude,
                                          double latitude) {
            this.taskId = taskId;
            this.volume = volume;
            this.customerId = customerId;
            this.isSuccess = isSuccess;
            this.longitude = longitude;
            this.latitude = latitude;
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

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

    public static class Recording {
        private String volume;

        public Recording(String volume) {
            this.volume = volume;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }
    }

    public static class InitConfigResult {
        private boolean isSuccess;

        public InitConfigResult(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }
    }

    public static class parentConfigResult {
        private boolean netWork;
        private String photoQuality;

        public parentConfigResult(boolean netWork, String photoQuality) {
            this.netWork = netWork;
            this.photoQuality = photoQuality;
        }

        public boolean isNetWork() {
            return netWork;
        }

        public void setNetWork(boolean netWork) {
            this.netWork = netWork;
        }

        public String getPhotoQuality() {
            return photoQuality;
        }

        public void setPhotoQuality(String photoQuality) {
            this.photoQuality = photoQuality;
        }
    }

    public static class InitUserConfigResult {
        private boolean isSuccess;

        public InitUserConfigResult(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }
    }

    public static class CustomerInformationUpdating {
        public CustomerInformationUpdating() {
        }
    }
}
