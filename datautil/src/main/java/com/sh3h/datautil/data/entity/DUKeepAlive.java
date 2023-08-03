package com.sh3h.datautil.data.entity;

/**
 * Created by xulongjun on 2016/3/15.
 */
public class DUKeepAlive implements IDUEntity {
    // / <summary>
    // / 用户编号
    // / </summary>
    private String userID;
    // / <summary>
    // / 设备编号
    // / </summary>
    private String deviceID;
    // / <summary>
    // / 应用程序版本
    // / </summary>
    private String appVersion;
    // / <summary>
    // / 数据版本
    // / </summary>
    private String dataVersion;

    // / <summary>
    // / 发送呼吸包时间
    // / </summary>
    private long sendTime;

    // / <summary>
    // / X坐标
    // / </summary>
    private String x;

    // / <summary>
    // / Y坐标
    // / </summary>
    private String y;

    public DUKeepAlive() {

    }

    public DUKeepAlive(String userID,
                       String deviceID,
                       String appVersion,
                       String dataVersion,
                       long sendTime,
                       String x,
                       String y) {
        this.userID = userID;
        this.deviceID = deviceID;
        this.appVersion = appVersion;
        this.dataVersion = dataVersion;
        this.sendTime = sendTime;
        this.x = x;
        this.y = y;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
