package com.sh3h.datautil.data.entity;

/**
 * Created by LiMeng on 2017/12/15.
 */

public class DUDelayHuanBiaoJLInfo extends DURequest {
    public enum FilterType {
        NONE,
        ALL
    }

    private String account;
    private String deviceId;

    public DUDelayHuanBiaoJLInfo(String account, String deviceId) {
        this.account = account;
        this.deviceId = deviceId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
