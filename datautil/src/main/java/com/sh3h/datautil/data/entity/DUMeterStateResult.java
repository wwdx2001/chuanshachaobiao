package com.sh3h.datautil.data.entity;


public class DUMeterStateResult extends DUResponse {
    private boolean isQianFei;
    private boolean isHuanBiaoZT;

    public DUMeterStateResult() {
        isQianFei = false;
        isHuanBiaoZT = false;
    }

    public DUMeterStateResult(boolean isQianFei, boolean isHuanBiaoZT) {
        this.isQianFei = isQianFei;
        this.isHuanBiaoZT = isHuanBiaoZT;
    }

    public boolean isQianFei() {
        return isQianFei;
    }

    public void setIsQianFei(boolean isQianFei) {
        this.isQianFei = isQianFei;
    }

    public boolean isHuanBiaoZT() {
        return isHuanBiaoZT;
    }

    public void setIsHuanBiaoZT(boolean isHuanBiaoZT) {
        this.isHuanBiaoZT = isHuanBiaoZT;
    }
}
