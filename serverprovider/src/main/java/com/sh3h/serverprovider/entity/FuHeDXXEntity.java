package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FuHeDXXEntity {

    public FuHeDXXEntity() {

    }

    public String S_GONGDANBH;
    public String S_YUANYIN;
    public String S_CHULIJG;

    public String getS_GONGDANBH() {
        return S_GONGDANBH;
    }

    public void setS_GONGDANBH(String s_GONGDANBH) {
        S_GONGDANBH = s_GONGDANBH;
    }

    public String getS_YUANYIN() {
        return S_YUANYIN;
    }

    public void setS_YUANYIN(String s_YUANYIN) {
        S_YUANYIN = s_YUANYIN;
    }

    public String getS_CHULIJG() {
        return S_CHULIJG;
    }

    public void setS_CHULIJG(String s_CHULIJG) {
        S_CHULIJG = s_CHULIJG;
    }

    public static FuHeDXXEntity fromJSON(JSONObject object) {
        FuHeDXXEntity fuHeDXXEntity = new FuHeDXXEntity();
        fuHeDXXEntity.setS_GONGDANBH(object.optString("s_GONGDANBH"));
        fuHeDXXEntity.setS_YUANYIN(object.optString("s_YUANYIN"));
        fuHeDXXEntity.setS_CHULIJG(object.optString("s_CHULIJG"));
        return fuHeDXXEntity;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<FuHeDXXEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<FuHeDXXEntity> list = new ArrayList<FuHeDXXEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            FuHeDXXEntity entity = FuHeDXXEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

    public static JSONArray toJSONArray(List<FuHeDXXEntity> dataInfo)
            throws JSONException {
        JSONArray array = new JSONArray();
        for (FuHeDXXEntity entity : dataInfo) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }
        return array;
    }

    public static JSONObject toJSON(FuHeDXXEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_GONGDANBH", entity.getS_GONGDANBH());
            obj.put("S_YUANYIN", entity.getS_YUANYIN());
            obj.put("S_CHULIJG", entity.getS_CHULIJG());
        } catch (JSONException e) {
            return null;
        }

        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("S_GONGDANBH", getS_GONGDANBH());
            obj.put("S_YUANYIN", getS_YUANYIN());
            obj.put("S_CHULIJG", getS_CHULIJG());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
