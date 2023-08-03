package com.sh3h.serverprovider.entity;


import org.json.JSONException;
import org.json.JSONObject;

public class DingQiHBDJEntity {
    /// 工单编号
    private String GONGDANBH;

    /// 施工约时
    private String SHIGONGYS;

    /// 受理备注
    private String SHOULIBZ;

    /// 预约扩展
    private String YuYueKZ;

    public DingQiHBDJEntity() {

    }

    public void setGONGDANBH(String gongdanbh) {
        GONGDANBH = gongdanbh;
    }

    public String getGONGDANBH() {
        return GONGDANBH;
    }

    public void setSHIGONGYS(String shigongys) {
        SHIGONGYS= shigongys;
    }

    public String getSHIGONGYS() {
        return SHIGONGYS;
    }

    public void setSHOULIBZ(String shoulibz) {
        SHOULIBZ = shoulibz;
    }

    public String getSHOULIBZ() {
        return SHOULIBZ;
    }

    public void setYuYueKZ(String yuYueKZ) {
        YuYueKZ = yuYueKZ;
    }

    public String getYuYueKZ() {
        return YuYueKZ;
    }

    public static JSONObject toJSON(DingQiHBDJEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("GONGDANBH", entity.getGONGDANBH());
            obj.put("SHIGONGYS", entity.getSHIGONGYS());
            obj.put("SHOULIBZ", entity.getSHOULIBZ());
            obj.put("YuYueKZ", entity.getYuYueKZ());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("GONGDANBH", getGONGDANBH());
            obj.put("SHIGONGYS", getSHIGONGYS());
            obj.put("SHOULIBZ", getSHOULIBZ());
            obj.put("YuYueKZ", getYuYueKZ());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
