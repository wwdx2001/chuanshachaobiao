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
public class RushPaySJResultEntity {

    private int taskId;
    private boolean isSuccess;
    private String message;

    public RushPaySJResultEntity() {

    }

    public RushPaySJResultEntity(int taskId,
                                 boolean isSuccess,
                                 String message

    ) {
        this.taskId = taskId;
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 转换JSONObject对象为RushPaySJEntity
     *
     * @param object
     * @return
     */
    public static RushPaySJResultEntity fromJSON(JSONObject object) {
        RushPaySJResultEntity cbsj = new RushPaySJResultEntity();
        cbsj.setTaskId(object.optInt("taskId"));
        cbsj.setSuccess(object.optBoolean("isSuccess"));
        cbsj.setMessage(object.optString("message"));
        return cbsj;
    }

    /**
     * 转换JSONArray对象为List<RushPaySJEntity>
     *
     * @param array
     * @return
     * @throws JSONException
     */
    public static List<RushPaySJResultEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<RushPaySJResultEntity> list = new ArrayList<RushPaySJResultEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            RushPaySJResultEntity cbsj = RushPaySJResultEntity.fromJSON(object);
            list.add(cbsj);
        }
        return list;
    }

}
