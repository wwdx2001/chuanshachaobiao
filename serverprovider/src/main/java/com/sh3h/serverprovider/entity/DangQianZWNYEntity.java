package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DangQianZWNYEntity {

    public DangQianZWNYEntity() {

    }

    public int MONTH;
    public int YEAR;
    public int ZHANGWUNY;


    public int getMONTH() {
        return MONTH;
    }

    public void setMONTH(int MONTH) {
        this.MONTH = MONTH;
    }

    public int getYEAR() {
        return YEAR;
    }

    public void setYEAR(int YEAR) {
        this.YEAR = YEAR;
    }

    public int getZHANGWUNY() {
        return ZHANGWUNY;
    }

    public void setZHANGWUNY(int ZHANGWUNY) {
        this.ZHANGWUNY = ZHANGWUNY;
    }

    public static DangQianZWNYEntity fromJSON(JSONObject object) {
        DangQianZWNYEntity dangQianZWNYEntity = new DangQianZWNYEntity();
        dangQianZWNYEntity.setMONTH(object.optInt("mONTH"));
        dangQianZWNYEntity.setYEAR(object.optInt("yEAR"));
        dangQianZWNYEntity.setZHANGWUNY(object.optInt("zHANGWUNY"));
        return dangQianZWNYEntity;
    }

    /**
     * 转换JsonArray对象为DangQianZWNYEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<DangQianZWNYEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<DangQianZWNYEntity> list = new ArrayList<DangQianZWNYEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            DangQianZWNYEntity entity = DangQianZWNYEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

}
