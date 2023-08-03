package com.sh3h.datautil.data.entity;

/**
 * Created by LiMeng on 2017/11/2.
 */

public class DUDelayIdInfo extends DURequest {
    private String account;
    private String deviceId;

    public DUDelayIdInfo(String account, String deviceId) {
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
