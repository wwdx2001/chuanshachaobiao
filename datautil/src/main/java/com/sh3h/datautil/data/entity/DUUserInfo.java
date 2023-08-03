package com.sh3h.datautil.data.entity;


public class DUUserInfo extends DURequest {
    private int userId;

    public DUUserInfo() {

    }

    public DUUserInfo(int userId, IDUHandler duHandler) {
        this.userId = userId;
        this.duHandler = duHandler;
    }

    public DUUserInfo(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
