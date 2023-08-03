package com.sh3h.serverprovider.entity;


import org.json.JSONException;
import org.json.JSONObject;

public class DingQiHBSearchEntity {
    /// 工单编号
    private String S_GONGDANBH;

    /// 养护工单类型
    private int I_YANGHUGDLX;

    public DingQiHBSearchEntity() {

    }

    public void setS_GONGDANBH(String gongdanbh) {
        S_GONGDANBH = gongdanbh;
    }

    public String getS_GONGDANBH() {
        return S_GONGDANBH;
    }

    public void setI_YANGHUGDLX(int yanghugdlx) {
        I_YANGHUGDLX = yanghugdlx;
    }

    public int getI_YANGHUGDLX() {
        return I_YANGHUGDLX;
    }

    public static JSONObject toJSON(DingQiHBSearchEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_GONGDANBH", entity.getS_GONGDANBH());
            obj.put("I_YANGHUGDLX", entity.getI_YANGHUGDLX());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_GONGDANBH", getS_GONGDANBH());
            obj.put("I_YANGHUGDLX", getI_YANGHUGDLX());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
