/**
 *
 */
package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author xulongjun
 */
public class RushPaySJInfoEntity {

    private int taskId;
    private String receiptRemark;
    private long receiptTime;
    private String reviewPerson;
    private long uploadTime;

    public RushPaySJInfoEntity() {

    }

    public RushPaySJInfoEntity(int taskId,
                               String receiptRemark,
                               long receiptTime,
                               String reviewPerson,
                               long uploadTime
    ) {
        this.taskId = taskId;
        this.receiptRemark = receiptRemark;
        this.receiptTime = receiptTime;
        this.reviewPerson = reviewPerson;
        this.uploadTime = uploadTime;
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



        /**
     * 转换JiChaSJEntity对象为JSONObject
     *
     * @return
     */
    public JSONObject toJSON(RushPaySJInfoEntity rushPaySJInfoEntity) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("taskId", rushPaySJInfoEntity.getTaskId());
        object.put("receiptRemark", rushPaySJInfoEntity.getReceiptRemark());
        object.put("receiptTime", rushPaySJInfoEntity.getReceiptTime());
        object.put("reviewPerson", rushPaySJInfoEntity.getReviewPerson());
        object.put("uploadTime", rushPaySJInfoEntity.getUploadTime());
        return object;
    }

    /**
     * 转换List<JiChaSJEntity>对象为JSONArray
     *
     * @param list
     * @return
     */
    public JSONArray toJSONArray(List<RushPaySJInfoEntity> list)
            throws JSONException {
        JSONArray array = new JSONArray();

        for (RushPaySJInfoEntity rushPaySJInfoEntity : list) {
            JSONObject object = toJSON(rushPaySJInfoEntity);
            array.put(object);
        }

        return array;
    }


//
//    /**
//     * 转换JSONObject对象为RushPaySJEntity
//     *
//     * @param object
//     * @return
//     */
//    public static RushPaySJInfoEntity fromJSON(JSONObject object) {
//        RushPaySJInfoEntity cbsj = new RushPaySJInfoEntity();
//        cbsj.setTaskId(object.optInt("taskId"));
//        cbsj.setReceiptRemark(object.optString("receiptRemark"));
//        cbsj.setReceiptTime(object.optLong("receiptTime"));
//        cbsj.setReviewPerson(object.optString("reviewPerson"));
//        cbsj.setUploadTime(object.optLong("uploadTime"));
//        return cbsj;
//    }
//
//    /**
//     * 转换JSONArray对象为List<RushPaySJEntity>
//     *
//     * @param array
//     * @return
//     * @throws JSONException
//     */
//    public static List<RushPaySJInfoEntity> fromJSONArray(JSONArray array)
//            throws JSONException {
//        List<RushPaySJInfoEntity> list = new ArrayList<RushPaySJInfoEntity>();
//        for (int i = 0; i < array.length(); i++) {
//            JSONObject object = array.getJSONObject(i);
//            RushPaySJInfoEntity cbsj = RushPaySJInfoEntity.fromJSON(object);
//            list.add(cbsj);
//        }
//        return list;
//    }

}
