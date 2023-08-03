package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ExternalWorkPartialDetailInfoEntity {
    /// <summary>
    /// 任务编号
    /// </summary>
    private int _RENWUBH;
    /// <summary>
    /// 序号
    /// </summary>
    private int _XH;
    /// <summary>
    /// 用户号
    /// </summary>
    private String _CID;
    /// <summary>
    ///条件水量，不条件的为0【远传表水量-外复核查】
    /// </summary>
    private int _TIAOJIANSL;
    /// <summary>
    ///复查结果【远传表水量-外复核查】
    /// </summary>
    private String _FUCHAJG;

    public ExternalWorkPartialDetailInfoEntity() {

    }

    public int getRENWUBH() {
        return _RENWUBH;
    }

    public void setRENWUBH(int RENWUBH) {
        _RENWUBH = RENWUBH;
    }

    public int getXH() {
        return _XH;
    }

    public void setXH(int XH) {
        _XH = XH;
    }

    public String getCID() {
        return _CID;
    }

    public void setCID(String CID) {
        _CID = CID;
    }

    public int getTIAOJIANSL() {
        return _TIAOJIANSL;
    }

    public void setTIAOJIANSL(int TIAOJIANSL) {
        _TIAOJIANSL = TIAOJIANSL;
    }

    public String getFUCHAJG() {
        return _FUCHAJG;
    }

    public void setFUCHAJG(String FUCHAJG) {
        _FUCHAJG = FUCHAJG;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();

        try {
            obj.put("I_RENWUBH", getRENWUBH());
            obj.put("I_XH", getXH());
            obj.put("S_CID", getCID());
            obj.put("I_TIAOJIANSL", getTIAOJIANSL());
            obj.put("S_FUCHAJG", getFUCHAJG());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static JSONObject toJSON(ExternalWorkPartialDetailInfoEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();

        try {
            obj.put("I_RENWUBH", entity.getRENWUBH());
            obj.put("I_XH", entity.getXH());
            obj.put("S_CID", entity.getCID());
            obj.put("I_TIAOJIANSL", entity.getTIAOJIANSL());
            obj.put("S_FUCHAJG", entity.getFUCHAJG());
        }
        catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public static JSONArray toJSONArray(List<ExternalWorkPartialDetailInfoEntity> list)
            throws JSONException {
        if (list == null) {
            return null;
        }

        JSONArray array = new JSONArray();

        for (ExternalWorkPartialDetailInfoEntity entity : list) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }

        return array;
    }
}
