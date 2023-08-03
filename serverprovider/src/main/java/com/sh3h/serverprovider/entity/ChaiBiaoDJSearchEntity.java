package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChaiBiaoDJSearchEntity {

    public ChaiBiaoDJSearchEntity() {

    }

    /// 工单编号
    public String S_GONGDANBH;

    /// 养护工单类型
    public int I_YANGHUGDLX;


    public String getS_GONGDANBH() {
        return S_GONGDANBH;
    }

    public void setS_GONGDANBH(String s_GONGDANBH) {
        S_GONGDANBH = s_GONGDANBH;
    }

    public int getI_YANGHUGDLX() {
        return I_YANGHUGDLX;
    }

    public void setI_YANGHUGDLX(int i_YANGHUGDLX) {
        I_YANGHUGDLX = i_YANGHUGDLX;
    }

    public static ChaiBiaoDJSearchEntity fromJSON(JSONObject object) {
        ChaiBiaoDJSearchEntity chaoBiaoDJ = new ChaiBiaoDJSearchEntity();
        chaoBiaoDJ.setI_YANGHUGDLX(object.optInt("i_YANGHUGDLX"));
        chaoBiaoDJ.setS_GONGDANBH(object.optString("s_GONGDANBH"));
        return chaoBiaoDJ;
    }

    /**
     * 转换JsonArray对象为BiaoKaXXEntity实体集合
     *
     * @param array
     * @return
     */
    public static List<ChaiBiaoDJSearchEntity> fromJSONArray(JSONArray array)
            throws JSONException {
        List<ChaiBiaoDJSearchEntity> list = new ArrayList<ChaiBiaoDJSearchEntity>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            ChaiBiaoDJSearchEntity entity = ChaiBiaoDJSearchEntity.fromJSON(object);
            list.add(entity);
        }
        return list;
    }

    public static JSONArray toJSONArray(List<ChaiBiaoDJSearchEntity> dataInfo)
            throws JSONException {
        JSONArray array = new JSONArray();
        for (ChaiBiaoDJSearchEntity entity : dataInfo) {
            JSONObject object = toJSON(entity);
            array.put(object);
        }
        return array;
    }

    public static JSONObject toJSON(ChaiBiaoDJSearchEntity entity)
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
            obj.put("S_GONGDANBH",getS_GONGDANBH());
            obj.put("I_YANGHUGDLX", getI_YANGHUGDLX());
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
