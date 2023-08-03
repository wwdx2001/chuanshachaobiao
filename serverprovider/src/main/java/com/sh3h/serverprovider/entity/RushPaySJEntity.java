/**
 *
 */
package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xulongjun
 */
public class RushPaySJEntity {

    private int id;
    private int taskId;
    private String cardId;
    private String cardName;
    private String cardAddress;
    private String subcomCode;
    private int qfMonths;
    private double qfMoney;
    private int isFinish;
    private String meterReader;
    private String receiptRemark;
    private long receiptTime;
    private String reviewPerson;
    private long uploadTime;
    private int isComplete;

    public RushPaySJEntity() {

    }

    public RushPaySJEntity(
                           int id,
                           int taskId,
                           String cardId,
                           String cardName,
                           String cardAddress,
                           String subcomCode,
                           int qfMonths,
                           double qfMoney,
                           int isFinish,
                           String meterReader,
                           String receiptRemark,
                           long receiptTime,
                           String reviewPerson,
                           long uploadTime,
                           int isComplete
    ) {
        this.id = id;
        this.taskId = taskId;
        this.cardId = cardId;
        this.cardName = cardName;
        this.cardAddress = cardAddress;
        this.subcomCode = subcomCode;
        this.qfMonths = qfMonths;
        this.qfMoney = qfMoney;
        this.isFinish = isFinish;
        this.meterReader = meterReader;
        this.receiptRemark = receiptRemark;
        this.receiptTime = receiptTime;
        this.reviewPerson = reviewPerson;
        this.uploadTime = uploadTime;
        this.isComplete = isComplete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(long receiptTime) {
        this.receiptTime = receiptTime;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardAddress() {
        return cardAddress;
    }

    public void setCardAddress(String cardAddress) {
        this.cardAddress = cardAddress;
    }

    public String getSubcomCode() {
        return subcomCode;
    }

    public void setSubcomCode(String subcomCode) {
        this.subcomCode = subcomCode;
    }

    public int getQfMonths() {
        return qfMonths;
    }

    public void setQfMonths(int qfMonths) {
        this.qfMonths = qfMonths;
    }

    public double getQfMoney() {
        return qfMoney;
    }

    public void setQfMoney(double qfMoney) {
        this.qfMoney = qfMoney;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public String getMeterReader() {
        return meterReader;
    }

    public void setMeterReader(String meterReader) {
        this.meterReader = meterReader;
    }

    public String getReceiptRemark() {
        return receiptRemark;
    }

    public void setReceiptRemark(String receiptRemark) {
        this.receiptRemark = receiptRemark;
    }

    public String getReviewPerson() {
        return reviewPerson;
    }

    public void setReviewPerson(String reviewPerson) {
        this.reviewPerson = reviewPerson;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }


    /**
     * 转换JSONObject对象为RushPaySJEntity
     *
     * @param object
     * @return
     */
    public static RushPaySJEntity fromJSON(JSONObject object) {
        RushPaySJEntity cbsj = new RushPaySJEntity();
        cbsj.setTaskId(object.optInt("taskId"));
        cbsj.setCardId(object.optString("cardId"));
        cbsj.setCardName(object.optString("cardName"));
        cbsj.setCardAddress(object.optString("cardAddress"));
        cbsj.setSubcomCode(object.optString("subcomCode"));
        cbsj.setQfMonths(object.optInt("qfMonths"));
        cbsj.setQfMoney(object.optDouble("qfMoney"));
        cbsj.setIsFinish(object.optInt("isFinish"));
        cbsj.setMeterReader(object.optString("meterReader"));
        cbsj.setReceiptRemark(object.optString("receiptRemark"));
        cbsj.setReceiptTime(object.optLong("receiptTime"));
        cbsj.setReviewPerson(object.optString("reviewPerson"));
        cbsj.setUploadTime(object.optLong("uploadTime"));
        cbsj.setIsComplete(object.optInt("isComplete"));
        return cbsj;
    }

    /**
     * 转换JSONArray对象为List<RushPaySJEntity>
     *
     * @param array
     * @return
     * @throws JSONException
     */
    public static List<RushPaySJEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<RushPaySJEntity> list = new ArrayList<RushPaySJEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            RushPaySJEntity cbsj = RushPaySJEntity.fromJSON(object);
            list.add(cbsj);
        }
        return list;
    }

}
