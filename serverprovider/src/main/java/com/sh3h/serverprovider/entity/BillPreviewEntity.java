package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjing on 2017/9/7.
 */

public class BillPreviewEntity {

    /// <summary>
    /// 主键
    /// </summary>
    private int tempFeeId;

    /// <summary>
    /// 表卡编号
    /// </summary>
    private String meterCardId;

    /// <summary>
    /// 水量
    /// </summary>
    private int accWater;

    /// <summary>
    /// 金额
    /// </summary>
    private double accMoney;

    private String message;

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

    public static BillPreviewEntity fromJSON(JSONObject object) {
        BillPreviewEntity billPreviewEntity = new BillPreviewEntity();
        billPreviewEntity.setMeterCardId(object.optString("meterCardId"));
        billPreviewEntity.setTempFeeId(object.optInt("tempFeeId"));
        billPreviewEntity.setAccWater(object.optInt("accWater"));
        billPreviewEntity.setAccMoney(object.optDouble("accMoney"));
        billPreviewEntity.setMessage(object.optString("message"));
        return billPreviewEntity;
    }

    public static List<BillPreviewEntity> fromJSONArray(JSONArray array)
            throws JSONException {

        List<BillPreviewEntity> list = new ArrayList<BillPreviewEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            BillPreviewEntity entity = BillPreviewEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

}
