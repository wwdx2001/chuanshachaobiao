package com.sh3h.datautil.data.entity;


public class DUDeviceResult extends DUResponse {
    /**
     * 是否激活成功
     */
    private boolean success;

    /**
     * 激活码
     */
    private String code;

    /**
     * 返回信息
     */
    private String error;

    // / <summary>
    // / 激活状态
    // / </summary>
    private String activationStatus;

    // / <summary>
    // / 激活时间
    // / </summary>
    private long activationTime;

    // / <summary>
    // / 设备状态
    // / </summary>
    private String deviceStatus;

    public DUDeviceResult() {
        this.success = false;
        this.code = null;
        this.error = null;
        this.activationStatus = null;
        this.activationTime = 0;
        this.deviceStatus = null;
    }

    public DUDeviceResult(boolean success,
                          String code,
                          String error,
                          String activationStatus,
                          long activationTime,
                          String deviceStatus) {
        this.success = success;
        this.code = code;
        this.error = error;
        this.activationStatus = activationStatus;
        this.activationTime = activationTime;
        this.deviceStatus = deviceStatus;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(String activationStatus) {
        this.activationStatus = activationStatus;
    }

    public long getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(long activationTime) {
        this.activationTime = activationTime;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
}
