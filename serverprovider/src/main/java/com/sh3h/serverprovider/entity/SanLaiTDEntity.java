package com.sh3h.serverprovider.entity;


import org.json.JSONException;
import org.json.JSONObject;

public class SanLaiTDEntity {
    private String _gongDanBH;
    private String _tuiDanLY;

    public SanLaiTDEntity() {

    }

    public void set_gongDanBH(String gongDanBH) {
        _gongDanBH = gongDanBH;
    }

    public String get_gongDanBH() {
        return _gongDanBH;
    }

    public void set_tuiDanLY(String tuiDanLY) {
        _tuiDanLY = tuiDanLY;
    }

    public String get_tuiDanLY() {
        return _tuiDanLY;
    }

    public static JSONObject toJSON(SanLaiTDEntity entity)
            throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            obj.put("GONGDANBH", entity.get_gongDanBH());
            obj.put("TUIDANLY", entity.get_tuiDanLY());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("GONGDANBH", get_gongDanBH());
            obj.put("TUIDANLY", get_tuiDanLY());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
