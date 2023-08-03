package com.sh3h.datautil.data.entity;


public class DUShuiLiangFTXXInfo extends DURequest {
    private String volume;

    public DUShuiLiangFTXXInfo() {

    }

    public DUShuiLiangFTXXInfo(String volume,
                               IDUHandler duHandler) {
        this.volume = volume;
        this.duHandler = duHandler;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
