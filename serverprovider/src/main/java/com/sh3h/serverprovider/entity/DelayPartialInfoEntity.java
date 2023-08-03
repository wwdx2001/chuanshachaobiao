package com.sh3h.serverprovider.entity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DelayPartialInfoEntity {
    // 任务编号
    private int _RENWUBH;
    // 抄表ID
    private int _CHAOBIAOID;
    // 客户号
    private String _CID;
    // 延迟类型(0:延迟,1:延迟外复)
    private int _YANCHILX;
    // 抄表年月
    private int _CHAOBIAONY;
    // 延迟外复备注
    private String _BEIZHU;

    public DelayPartialInfoEntity() { }

    public int getRENWUBH() {
        return _RENWUBH;
    }

    public void setRENWUBH(int RENWUBH) {
        _RENWUBH = RENWUBH;
    }

    public int getCHAOBIAOID() {
        return _CHAOBIAOID;
    }

    public void setCHAOBIAOID(int CHAOBIAOID) {
        _CHAOBIAOID = CHAOBIAOID;
    }

    public String getCID() {
        return _CID;
    }

    public void setCID(String CID) {
        _CID = CID;
    }

    public int getYANCHILX() {
        return _YANCHILX;
    }

    public void setYANCHILX(int YANCHILX) {
        _YANCHILX = YANCHILX;
    }

    public int getCHAOBIAONY() {
        return _CHAOBIAONY;
    }

    public void setCHAOBIAONY(int CHAOBIAONY) {
        _CHAOBIAONY = CHAOBIAONY;
    }

    public String getBEIZHU() {
        return _BEIZHU;
    }

    public void setBEIZHU(String BEIZHU) {
        _BEIZHU = BEIZHU;
    }


    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();

        try {
            obj.put("I_RENWUBH", getRENWUBH());
            obj.put("I_CHAOBIAOID", getCHAOBIAOID());
            obj.put("S_CID", getCID());
            obj.put("I_YANCHILX", getYANCHILX());
            obj.put("I_CHAOBIAONY", getCHAOBIAONY());
            obj.put("S_BEIZHU", getBEIZHU());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static JSONObject toJSON(DelayPartialInfoEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();

        try {
            obj.put("I_RENWUBH", entity.getRENWUBH());
            obj.put("I_CHAOBIAOID", entity.getCHAOBIAOID());
            obj.put("S_CID", entity.getCID());
            obj.put("I_YANCHILX", entity.getYANCHILX());
            obj.put("I_CHAOBIAONY", entity.getCHAOBIAONY());
            obj.put("S_BEIZHU", entity.getBEIZHU());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static JSONArray toJSONArray(List<DelayPartialInfoEntity> list)
            throws JSONException {
        JSONArray array = new JSONArray();

        for (DelayPartialInfoEntity entity : list) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }

        return array;
    }

    public static DelayPartialInfoEntity fromJSON(JSONObject object)
            throws JSONException {
        DelayPartialInfoEntity entity = new DelayPartialInfoEntity();

        entity.setRENWUBH(object.optInt("i_RENWUBH"));
        entity.setCHAOBIAOID(object.optInt("i_CHAOBIAOID"));
        entity.setCID(object.optString("s_CID"));
        entity.setYANCHILX(object.optInt("i_YANCHILX"));
        entity.setCHAOBIAONY(object.optInt("i_CHAOBIAONY"));
        entity.setBEIZHU(object.optString("s_BEIZHU"));

        return entity;
    }

    public static List<DelayPartialInfoEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<DelayPartialInfoEntity> list = new ArrayList<DelayPartialInfoEntity>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            DelayPartialInfoEntity entity = DelayPartialInfoEntity.fromJSON(object);
            list.add(entity);
        }

        return list;
    }
}
