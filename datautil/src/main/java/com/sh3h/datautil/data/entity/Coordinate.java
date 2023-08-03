package com.sh3h.datautil.data.entity;

import java.io.Serializable;

/**
 * Created by xulongjun on 2017/3/31.
 */

public class Coordinate implements Serializable {
    private double longitude;
    private double latitude;
    private int ceNeiNo;//册内序号
    private String text;

    public Coordinate() {
    }

    public Coordinate(double longitude, double latitude, String testText) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.text = testText;
    }

    public Coordinate(double longitude, double latitude, int ceNeiNo) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.ceNeiNo = ceNeiNo;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCeNeiNo() {
        return ceNeiNo;
    }

    public void setCeNeiNo(int ceNeiNo) {
        this.ceNeiNo = ceNeiNo;
    }
}
