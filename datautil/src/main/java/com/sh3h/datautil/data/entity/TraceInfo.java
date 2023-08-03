package com.sh3h.datautil.data.entity;

import java.io.Serializable;

public class TraceInfo implements Serializable {
    private double longitude;
    private double latitude;
    private int orderNumber;//册内序号
    private String text; //
    private boolean isRecorded; //是否已抄

    public TraceInfo(double longitude, double latitude, int orderNumber, String text, boolean isRecorded) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.orderNumber = orderNumber;
        this.text = text;
        this.isRecorded = isRecorded;
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

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRecorded() {
        return isRecorded;
    }

    public void setRecorded(boolean recorded) {
        isRecorded = recorded;
    }
}
