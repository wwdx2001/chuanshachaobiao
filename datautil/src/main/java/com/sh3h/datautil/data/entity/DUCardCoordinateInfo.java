package com.sh3h.datautil.data.entity;


public class DUCardCoordinateInfo extends DURequest {
    private int taskId;
    private String volume;
    private String customerId;
    private double longitude;
    private double latitude;

    public DUCardCoordinateInfo(int taskId,
                                String volume,
                                String customerId,
                                double longitude,
                                double latitude) {
        this.taskId = taskId;
        this.volume = volume;
        this.customerId = customerId;
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
