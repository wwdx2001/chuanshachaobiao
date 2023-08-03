package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WorkStaffEntity {
    /// <summary>
    /// 账号
    /// </summary>
    private String _ACCOUNT;

    /// <summary>
    /// 名称
    /// </summary>
    private String _USERNAME;

    /// <summary>
    /// 站点
    /// </summary>
    private String _ST;

    public WorkStaffEntity() {

    }

    public String getACCOUNT() {
        return _ACCOUNT;
    }

    public void setACCOUNT(String ACCOUNT) {
        _ACCOUNT = ACCOUNT;
    }

    public String getUSERNAME() {
        return _USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        _USERNAME = USERNAME;
    }

    public String getST() {
        return _ST;
    }

    public void setST(String ST) {
        _ST = ST;
    }

    public static WorkStaffEntity fromJSON(JSONObject object)
            throws JSONException {
        WorkStaffEntity entity = new WorkStaffEntity();

        entity.setACCOUNT(object.optString("aCCOUNT"));
        entity.setUSERNAME(object.optString("uSERNAME"));
        entity.setST(object.optString("s_ST"));

        return entity;
    }

    public static List<WorkStaffEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<WorkStaffEntity> list = new ArrayList<WorkStaffEntity>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            WorkStaffEntity entity = WorkStaffEntity.fromJSON(object);
            list.add(entity);
        }

        return list;
    }
}
