package com.sh3h.serverprovider.entity;


import org.json.JSONException;
import org.json.JSONObject;

public class YangHuGDEntity {
    /// 工单记录主键
    private int ID;

    /// 养护工单类型
    private int GongDanLX;

    /// 反映类别
    private String FanYingLB;

    /// 反映内容
    private String FanYingNR;

    /// 施工约时
    private String SHIGONGYS;

    /// 受理备注
    private String SHOULIBZ;

    public YangHuGDEntity() {

    }

    public void setID(int id) {
        ID = id;
    }

    public int getID() {
        return ID;
    }

    public void setGongDanLX(int gongDanLX) {
        GongDanLX = gongDanLX;
    }

    public int getGongDanLX() {
        return GongDanLX;
    }

    public void setFanYingLB(String fanYingLB) {
        FanYingLB = fanYingLB;
    }

    public String getFanYingLB() {
        return FanYingLB;
    }

    public void setFanYingNR(String fanYingLB) {
        FanYingNR = fanYingLB;
    }

    public String getFanYingNR() {
        return FanYingNR;
    }

    public void setSHIGONGYS(String shigongys) {
        SHIGONGYS = shigongys;
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

    public static JSONObject toJSON(YangHuGDEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ID", entity.getID());
            obj.put("GongDanLX", entity.getGongDanLX());
            obj.put("FanYingLB", entity.getFanYingLB());
            obj.put("FanYingNR", entity.getFanYingNR());
            obj.put("SHIGONGYS", entity.getSHIGONGYS());
            obj.put("SHOULIBZ", entity.getSHOULIBZ());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ID", getID());
            obj.put("GongDanLX", getGongDanLX());
            obj.put("FanYingLB", getFanYingLB());
            obj.put("FanYingNR", getFanYingNR());
            obj.put("SHIGONGYS", getSHIGONGYS());
            obj.put("SHOULIBZ", getSHOULIBZ());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
