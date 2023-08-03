package com.sh3h.serverprovider.entity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ZhanDianXXEntity {
    private int ID; // 自增
    private int STATIONID; // 站点Id
    private String STATIONNAME; // 名字
    private String STATIONADDRESS; // 地址
    private String STATUS; // 状态
    private String BELONGSTATION; //
    private int DISPLAYINDEX;
    private String CHARGEMAN;
    private String PHONE; // 手机
    private String REMARK; //  备注

    public ZhanDianXXEntity() {

    }

    public void setID(int id) {
        ID = id;
    }

    public int getID() {
        return ID;
    }

    public void setSTATIONID(int stationid) {
        STATIONID = stationid;
    }

    public int getSTATIONID() {
        return STATIONID;
    }

    public void setSTATIONNAME(String stationname) {
        STATIONNAME = stationname;
    }

    public String getSTATIONNAME() {
        return STATIONNAME;
    }

    public void setSTATIONADDRESS(String stationaddress) {
        STATIONADDRESS = stationaddress;
    }

    public String getSTATIONADDRESS() {
        return STATIONADDRESS;
    }

    public void setSTATUS(String status) {
        STATUS = status;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setBELONGSTATION(String belongstation) {
        BELONGSTATION = belongstation;
    }

    public String getBELONGSTATION() {
        return BELONGSTATION;
    }

    public void setDISPLAYINDEX(int displayindex) {
        DISPLAYINDEX = displayindex;
    }

    public int getDISPLAYINDEX() {
        return DISPLAYINDEX;
    }

    public void setCHARGEMAN(String chargeman) {
        CHARGEMAN = chargeman;
    }

    public String getCHARGEMAN() {
        return CHARGEMAN;
    }

    public void setPHONE(String phone) {
        PHONE = phone;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setREMARK(String remark) {
        REMARK = remark;
    }

    public String getREMARK() {
        return REMARK;
    }

    public static ZhanDianXXEntity fromJSON(JSONObject object)
            throws JSONException {
        ZhanDianXXEntity entity = new ZhanDianXXEntity();
        entity.setID(object.optInt("iD"));
        entity.setSTATIONID(object.optInt("sTATIONID"));
        entity.setSTATIONNAME(object.optString("sTATIONNAME"));
        entity.setSTATIONADDRESS(object.optString("sTATIONADDRESS"));
        entity.setSTATUS(object.optString("sTATUS"));
        entity.setBELONGSTATION(object.optString("bELONGSTATION"));
        entity.setDISPLAYINDEX(object.optInt("dISPLAYINDEX"));
        entity.setCHARGEMAN(object.optString("cHARGEMAN"));
        entity.setPHONE(object.optString("pHONE"));
        entity.setREMARK(object.optString("rEMARK"));
        return entity;
    }

    public static List<ZhanDianXXEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<ZhanDianXXEntity> list = new ArrayList<ZhanDianXXEntity>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            ZhanDianXXEntity entity = ZhanDianXXEntity.fromJSON(object);
            list.add(entity);
        }

        return list;
    }
}
