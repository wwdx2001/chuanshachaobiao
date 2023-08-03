package com.sh3h.datautil.data.entity;

/**
 * Created by zhangjing on 2017/9/7.
 */

public class DUBillPreview {

    private  int tempFeeId;

    /// <summary>
    /// 表卡编号
    /// </summary>
    private String  meterCardId;

    /// <summary>
    /// 水量
    /// </summary>
    private int accWater;

    /// <summary>
    /// 金额
    /// </summary>
    private double accMoney;

    private String message;

    public DUBillPreview() {
    }

    public DUBillPreview(int tempFeeId, String meterCardId, int accWater, double accMoney,String message) {
        this.tempFeeId = tempFeeId;
        this.meterCardId = meterCardId;
        this.accWater = accWater;
        this.accMoney = accMoney;
        this.message=message;
    }

    public int getTempFeeId() {
        return tempFeeId;
    }

    public void setTempFeeId(int tempFeeId) {
        this.tempFeeId = tempFeeId;
    }

    public String getMeterCardId() {
        return meterCardId;
    }

    public void setMeterCardId(String meterCardId) {
        this.meterCardId = meterCardId;
    }

    public int getAccWater() {
        return accWater;
    }

    public void setAccWater(int accWater) {
        this.accWater = accWater;
    }

    public double getAccMoney() {
        return accMoney;
    }

    public void setAccMoney(double accMoney) {
        this.accMoney = accMoney;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
