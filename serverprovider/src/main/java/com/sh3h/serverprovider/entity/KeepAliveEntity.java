package com.sh3h.serverprovider.entity;


import org.json.JSONObject;

public class KeepAliveEntity {
    public long currentTime;

    public KeepAliveEntity() {
        currentTime = 0;
    }

    public KeepAliveEntity(long currentTime) {
        this.currentTime = currentTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public static KeepAliveEntity fromJson(JSONObject object) {
        KeepAliveEntity keepAliveEntity = new KeepAliveEntity();
        keepAliveEntity.setCurrentTime(object.optLong("currentTime"));
        return keepAliveEntity;
    }
}
