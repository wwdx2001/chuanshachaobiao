package com.sh3h.datautil.data.entity;


public class DUDeviceInfo extends DURequest {
    /**
     * device id
     */
    private String deviceID;

    /**
     * mac address
     */
    private String macAddress;

    /**
     * key
     */
    private String key;

    public DUDeviceInfo() {
        deviceID = null;
        macAddress = null;
        key = null;
    }

    public DUDeviceInfo(String deviceID, String macAddress, String key) {
        this.deviceID = deviceID;
        this.macAddress = macAddress;
        this.key = key;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
